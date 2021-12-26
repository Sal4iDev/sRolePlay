package ru.sal4i.sroleplay.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import ru.sal4i.sroleplay.utils.Config;

import java.util.List;

public record ReloadCommand(@NotNull Config config) implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        config.reload();
        sender.sendMessage(config.getReloadMessage());
        return true;
    }

    @Override
    public @NotNull
    List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) return List.of("Sal4iDev#4767");
        else return List.of("");
    }
}
