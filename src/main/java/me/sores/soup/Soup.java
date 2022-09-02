package me.sores.soup;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by sores on 8/11/2022.
 */
public class Soup extends JavaPlugin {

    private static Soup instance;

    @Override
    public void onEnable() {
        instance = this;
        new Init(this);
    }

    @Override
    public void onDisable() {
        Init.getInstance().unload();
        instance = null;
    }

    public static Soup getInstance() {
        return instance;
    }
}
