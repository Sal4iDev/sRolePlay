package ru.sal4i.sroleplay.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.sal4i.sroleplay.SRolePlay;
import ru.sal4i.sroleplay.utils.Config;
import ru.sal4i.sroleplay.utils.CustomCommand;
import ru.sal4i.sroleplay.utils.CustomText;
import ru.sal4i.sroleplay.utils.Other;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Command extends BukkitCommand {

    private final List<UUID> cooldown = new ArrayList<>();
    private final CustomCommand customCommand;
    private final Config config;

    public Command(CustomCommand customCommand, Config config) {
        super(customCommand.getId());

        this.customCommand = customCommand;
        this.config = config;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(config.getPlayersOnly());
            return true;
        }

        if (args.length == 0 && customCommand.hasUsage()) {
            sendUsage(sender, label);
            return true;
        }
        UUID uuid = player.getUniqueId();

        if (cooldown.contains(uuid)) {
            player.sendMessage(config.getCooldownMessage());
            return true;
        }
        String message = "";
        if (args.length != 0)
            message = String.join(" ", args).trim();
        String finalMessage = applyPlaceholders(player, label, message, customCommand.getDisplay());
        if (finalMessage == null) return true;

        CustomText text = new CustomText(finalMessage);
        text.setDescription(customCommand.getDescription());
        text.setSuggestCommand(label);

        if (customCommand.isSelf()) {
            text.sendMessage(sender);
        } else {
            if (customCommand.hasRadius()) {
                double radius = customCommand.getRadius();
                text.sendNearbyPlayers(player.getLocation(), radius);
            } else {
                text.sendAllPlayers();
            }
        }

        if (customCommand.hasCooldown()) {
            cooldown.add(uuid);

            long ticks = (long) customCommand.getCooldown();
            Bukkit.getScheduler().runTaskLaterAsynchronously(SRolePlay.getInstance(),
                    () -> cooldown.remove(uuid), ticks * 20L);
        }
        return true;
    }

    /**
     * @param player  Отправитель команды
     * @param label   Команда которую использовал отправитель
     * @param message Сообщение которое отправил игрок
     * @param display Сообщение которое будет отправлено
     * @return Сообщение с изменнёнными плейсхолдеры
     */
    private @Nullable String applyPlaceholders(Player player, String label, String message, String display) {
        String withPlaceholders = display;

        if (withPlaceholders.contains("{message1}")
            || withPlaceholders.contains("{message2}")) {
            String[] split = message.split("\\*", 2);
            if (split.length != 2 || split[1].isEmpty()) {
                sendUsage(player, label);
                return null;
            }

            withPlaceholders = withPlaceholders
                    .replace("{message1}", split[0])
                    .replace("{message2}", split[1]);
        }

        return withPlaceholders
                .replace("{player}", player.getName())
                .replace("{message}", Other.strip(message))
                // .replace("{label}", "/" + label)
                .replace("{try}", getRandomResult())
                .replace("{number}", getRandomNumber());
    }


    /**
     * @return Случайное число от 1(включительно) до 101(не включительно)
     */
    private @NotNull String getRandomNumber() {
        return ThreadLocalRandom.current().nextInt(1, 101) + "";
    }


    /**
     * @param sender Отправитель команды
     * @param label  Неправильно выполненная команда
     */
    private void sendUsage(CommandSender sender, String label) {
        CustomText text = new CustomText(customCommand.getUsage());

        text.setDescription(customCommand.getDescription());
        text.setSuggestCommand(label);
        text.sendMessage(sender);
    }

    /**
     * @return Решение для <code>/try</code>
     */
    private @NotNull String getRandomResult() {
        return Math.random() < 0.5D ?
                config.getTrySuccess() : config.getTryFail();
    }
}
