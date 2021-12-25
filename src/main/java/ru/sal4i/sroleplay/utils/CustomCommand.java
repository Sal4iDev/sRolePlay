package ru.sal4i.sroleplay.utils;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.sal4i.sroleplay.commands.Command;

public class CustomCommand {
    private final String id, display, description, usage;
    private final boolean isSelf;
    private double radius, cooldown;

    public CustomCommand(String id, String display, String description, String usage, boolean isSelf) {
        this.id = id;
        this.display = Other.color(display);
        this.description = Other.color(description);
        this.usage = usage == null ? null : Other.color(usage);
        this.isSelf = isSelf;
    }

    /**
     * @return Айди команды
     */
    public @NotNull String getId() {
        return id;
    }

    /**
     * @return Описание команды
     */
    public @NotNull String getDescription() {
        return description;
    }

    /**
     * @return Итоговое сообщение
     */
    public @NotNull String getDisplay() {
        return display;
    }

    /**
     * @return Сообщение о использовании команды
     */
    public @Nullable String getUsage() {
        return usage;
    }

    /**
     * @return Радиус отправки сообщения
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @param radius Установка радиуса отправки сообщения
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * @return Время которое нужно подождать перед следующим использованием команды
     */
    public double getCooldown() {
        return cooldown;
    }

    /**
     * @param cooldown Установка времени которое нужно подождать перед следующим использованием команды
     */
    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    /**
     * @return Имеет ли команда радиус
     */
    public boolean hasRadius() {
        return radius > 0;
    }

    /**
     * @return Имеет ли команда время ожидания
     */
    public boolean hasCooldown() {
        return cooldown > 0;
    }

    /**
     * @return Имеет ли команда сообщение об использовании
     */
    public boolean hasUsage() {
        return getUsage() != null;
    }

    /**
     * @return Является ли команда приватной
     */
    public boolean isSelf() {
        return isSelf;
    }

    /**
     * @param config Конфиг плагина.
     *               <br>Регистрирует новую команду
     */
    public void register(Config config) {
        Bukkit.getCommandMap().register("sal4idev",
                new Command(this, config));
    }
}

