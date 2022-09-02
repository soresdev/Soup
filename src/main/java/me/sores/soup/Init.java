package me.sores.soup;

import me.sores.Orion.util.StringUtil;
import me.sores.Orion.util.abstr.AbstractInit;
import me.sores.Orion.util.cmdfrmwrk.BaseCommand;
import me.sores.Orion.util.database.MongoBase;
import me.sores.soup.commands.BuildCommand;
import me.sores.soup.commands.KitsCommand;
import me.sores.soup.commands.SettingsCommand;
import me.sores.soup.config.SoupConfig;
import me.sores.soup.handler.AbilityHandler;
import me.sores.soup.handler.BoardHandler;
import me.sores.soup.handler.KitsHandler;
import me.sores.soup.handler.ProfileHandler;
import me.sores.soup.listener.AbilityListener;
import me.sores.soup.listener.PlayerListener;
import me.sores.soup.listener.WorldListener;
import me.sores.soup.util.OrionHook;
import org.bukkit.plugin.Plugin;

/**
 * Created by sores on 8/11/2022.
 */
public class Init extends AbstractInit {

    private static Init instance;
    private MongoBase mongoBase;

    public Init(Plugin plugin) {
        super(plugin);
        instance = this;

        OrionHook.check(Soup.getInstance());

        initInstances();
        registerCommands();
        registerEvents();
    }

    @Override
    public void initInstances() {
        new SoupConfig();

        try{
            this.mongoBase = new MongoBase(SoupConfig.DATABASE_HOST, SoupConfig.DATABASE_USERNAME, SoupConfig.DATABASE_PASSWORD, SoupConfig.DATABASE_NAME, SoupConfig.DATABASE_COLLECTION);
        }catch (Exception ex){
            StringUtil.log("&c[Soup] Could not connect to MongoDB Database, make sure it is setup.");
            ex.printStackTrace();
        }

        initHandler(new ProfileHandler(), true);
        initHandler(new BoardHandler(), false);
        initHandler(new AbilityHandler(), false);
        initHandler(new KitsHandler(), false);
    }

    @Override
    public void registerEvents() {
        registerListener(new PlayerListener(), new WorldListener(), new AbilityListener());
    }

    @Override
    public void registerCommands() {
        registerCommand("build", new BuildCommand());
        registerCommand("settings", new SettingsCommand());
        registerCommand("kits", new KitsCommand());
    }

    @Override
    public void unload() {
        getHandlerList().forEach(handler -> {
            try{
                handler.unload();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }

    public void registerCommand(String title, BaseCommand command) {
        registerCommand(getCommandRegistrar(), title, command);
    }

    public MongoBase getMongoBase() {
        return mongoBase;
    }

    public static Init getInstance() {
        return instance;
    }
}
