package ru.sal4i.sroleplay.utils;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.sal4i.sroleplay.commands.RolePlayCommand;

public class CustomCommand {
    private final String name, display, description, usage, permission, noPermission;
    private final boolean isSelf;
    private double radius, cooldown;

    public CustomCommand(String name, String display, String description, String usage,
                         String permission, String noPermission, boolean isSelf) {
        this.name = name;
        this.display = Other.color(display);
        this.description = Other.color(description);
        this.usage = (usage == null) ? null : Other.color(usage);
        this.permission = permission;
        this.noPermission = (noPermission == null)
                ? Bukkit.getPermissionMessage() : noPermission;
        this.isSelf = isSelf;
    }

    /**
     * @return Имя команды
     */
    public @NotNull String getName() {
        return name;
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
     * @return Право на выполнение команды
     */
    public @Nullable String getPermission() {
        return permission;
    }

    /**
     * @return Сообщение о не хватке прав на выполнение команды
     */
    public @NotNull String getNoPermission() {
        return noPermission;
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
                new RolePlayCommand(this, config));
    }
}

