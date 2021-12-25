package ru.sal4i.sroleplay.utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class CustomText {
    private final TextComponent component;

    public CustomText(String text) {
        this.component = new TextComponent(text);
    }

    /**
     * @param text Описание для команды
     *             <br>Устанавливает описание для команды</br>
     */
    public void setDescription(String text) {
        String colorText = Other.color(text);
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new TextComponent[]{
                        new TextComponent(colorText)
                });

        component.setHoverEvent(hoverEvent);
    }

    /**
     * @param command Команда
     *                <br>Устанавливает текст при нажатии на сообщение</br>
     */
    public void setSuggestCommand(String command) {
        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
                "/" + command + " ");
        component.setClickEvent(clickEvent);
    }

    /**
     * Отправляет сообщение всем игрокам
     */
    public void sendAllPlayers() {
        Bukkit.getOnlinePlayers().forEach(this::sendMessage);
    }

    /**
     * @param location Локация игрока
     * @param radius   Радиус для нахождения игроков
     *                 <br>Отправляет сообщение игрокам в радиусе </br>
     */
    public void sendNearbyPlayers(Location location, double radius) {
        location.getNearbyPlayers(radius).forEach(this::sendMessage);
    }

    /**
     * @param sender Получатель сообщения
     *               <br>Отправляет сообщение </br>
     */
    public void sendMessage(CommandSender sender) {
        sender.sendMessage(component);
    }
}
