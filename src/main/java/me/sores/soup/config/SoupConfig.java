package me.sores.soup.config;

import me.sores.Orion.util.configuration.ConfigFile;
import me.sores.soup.Soup;

/**
 * Created by sores on 8/11/2022.
 */
public class SoupConfig {

    private static ConfigFile configFile;

    public static String DATABASE_HOST, DATABASE_USERNAME, DATABASE_PASSWORD, DATABASE_NAME, DATABASE_COLLECTION;

    public SoupConfig() {
        configFile = new ConfigFile("config.yml", Soup.getInstance());

        DATABASE_HOST = configFile.getString("database.host");
        DATABASE_USERNAME = configFile.getString("database.username");
        DATABASE_PASSWORD = configFile.getString("database.password");
        DATABASE_NAME = configFile.getString("database.name");
        DATABASE_COLLECTION = configFile.getString("database.collection");

    }

    public static void reload(){
        destroy();
        new SoupConfig();
    }

    private static void destroy(){

    }

    public static ConfigFile getConfigFile() {
        return configFile;
    }

}
