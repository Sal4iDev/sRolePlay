package ru.sal4i.sroleplay.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.sal4i.sroleplay.SRolePlay;

public class Config {
    private final SRolePlay plugin;
    private final FileConfiguration config;

    private String cooldownMessage, reloadMessage, playersOnly, trySuccess, tryFail;

    public Config(SRolePlay plugin, FileConfiguration config) {
        this.plugin = plugin;
        this.config = config;

        reload();
    }

    /**
     * Перезагружает конфиг плагина
     * <br>Также, регистрирует новые команды, если они есть
     */
    public void reload() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();

        ConfigurationSection messages = config.getConfigurationSection("messages");
        ConfigurationSection commands = config.getConfigurationSection("commands");
        assert messages != null;
        assert commands != null;

        cooldownMessage = color(messages.getString("cooldown"));
        reloadMessage = color(messages.getString("reload"));
        playersOnly = color(messages.getString("playersOnly"));
        trySuccess = color(messages.getString("trySuccess"));
        tryFail = color(messages.getString("tryFail"));

        for (String commandName : commands.getKeys(false)) {
            plugin.getServer().getCommandMap()
                    .getKnownCommands().remove(commandName);
            ConfigurationSection command = commands.getConfigurationSection(commandName);
            assert command != null;

            boolean isSelf;
            String permission = command.getString("permission");
            String noPermission = command.getString("noPermission");
            try {
                isSelf = command.getBoolean("self");
            } catch (NullPointerException exception) {
                isSelf = false;
            }
            if (noPermission == null) {
                noPermission = Bukkit.getPermissionMessage();
            }

            String display = command.getString("display");
            String description = command.getString("description");
            String usage = command.getString("usage");
            CustomCommand customCommand = new CustomCommand(commandName, display, description, usage,
                    permission, noPermission, isSelf);

            double radius = command.getDouble("radius");
            customCommand.setRadius(radius);

            double cooldown = command.getDouble("cooldown");
            customCommand.setCooldown(cooldown);

            customCommand.register(this);
        }
    }


    /**
     * @return Сообщение, которое просит подождать
     */
    public String getCooldownMessage() {
        return cooldownMessage;
    }

    /**
     * @return Сообщение о перезагрузке конфига
     */
    public String getReloadMessage() {
        return reloadMessage;
    }

    /**
     * @return Сообщение для консоли
     */
    public String getPlayersOnly() {
        return playersOnly;
    }

    /**
     * @return Успешное <code>/try сообщение
     */
    public String getTrySuccess() {
        return trySuccess;
    }

    /**
     * @return Неуспешное <code>/try сообщение
     */
    public String getTryFail() {
        return tryFail;
    }

    /**
     * @param message Сообщение для замены цветов
     * @return Цветное сообщение
     */
    @Contract("_ -> new")
    private @NotNull String color(String message) {
        return Other.color(message);
    }
}
