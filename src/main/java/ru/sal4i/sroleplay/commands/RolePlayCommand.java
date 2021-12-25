package ru.sal4i.sroleplay.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import ru.sal4i.sroleplay.utils.Config;
import ru.sal4i.sroleplay.utils.Other;

import java.util.List;

public record RolePlayCommand(@NotNull Config config) implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        config.reload();

        sender.sendMessage(Other.color("&8&l_________________________________\n"));
        sender.sendMessage(Other.color("\n#486769Plugin: #93DDF6sRolePlay\n"));
        sender.sendMessage(Other.color("#486769Author: #93DDF6Sal4iDev#4767\n"));
        sender.sendMessage(Other.color("&8&l_________________________________"));
        sender.sendMessage("\n" + config.getReloadMessage());
        return true;
    }

    @Override
    public @NotNull
    List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return List.of("");
    }
}
