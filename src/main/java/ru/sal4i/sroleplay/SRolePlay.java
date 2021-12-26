package ru.sal4i.sroleplay;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import ru.sal4i.sroleplay.commands.ReloadCommand;
import ru.sal4i.sroleplay.utils.Config;

public class SRolePlay extends JavaPlugin {
    private static SRolePlay instance;

    public static SRolePlay getInstance() {
        return instance;
    }

    /**
     * Метод вызываемый при включении плагина
     */
    @Override
    public void onEnable() {
        instance = this;
        Config config = new Config(instance, getConfig());

        PluginCommand command = getCommand("sroleplay");
        if (command == null) {
            getLogger().warning("Команда /sroleplay не зарегистрирована в plugin.yml!");
            getLogger().warning("Команда отключена...");
        } else {
            command.setExecutor(new ReloadCommand(config));
        }
    }
}
