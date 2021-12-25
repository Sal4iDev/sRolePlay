package ru.sal4i.sroleplay;

import org.bukkit.plugin.java.JavaPlugin;
import ru.sal4i.sroleplay.commands.RolePlayCommand;
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

        getCommand("sroleplay").setExecutor(new RolePlayCommand(config));
    }
}
