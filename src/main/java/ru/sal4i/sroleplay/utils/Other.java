package ru.sal4i.sroleplay.utils;

import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class Other {

    /**
     * @param message Сообщение для изменения цвета
     * @return Цветное сообщение
     */
    @Contract("_ -> new")
    public static @NotNull String color(String message) {
        return ChatColor.translateAlternateColorCodes('&',
                Pattern.compile("#([A-Fa-f0-9]){6}")
                        .matcher(message)
                        .replaceAll(m -> ChatColor.of(m.group()).toString())
        );
    }

    /**
     * @param message Сообщение для отключения цвета
     * @return Сообщение полностью лишённое цвета
     */
    @Contract("_ -> new")
    public static @NotNull String strip(String message) {
        return ChatColor.stripColor(message);
    }
}
