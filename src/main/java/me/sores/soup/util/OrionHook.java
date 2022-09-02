package me.sores.soup.util;

import me.sores.Orion.util.StringUtil;
import me.sores.soup.Soup;

/**
 * Created by sores on 8/11/2022.
 */
public class OrionHook {

    public static void check(Soup soup){
        boolean hooked = soup.getServer().getPluginManager().getPlugin("Orion") != null;

        if(hooked){
            soup.getServer().getConsoleSender().sendMessage(StringUtil.color("&a[Soup] Orion depend found."));
        }else{
            soup.getServer().getConsoleSender().sendMessage(StringUtil.color("&c[Soup] Disabling..."));
            soup.getServer().getConsoleSender().sendMessage(StringUtil.color("&c[Soup] Soup cannot be enabled while missing Orion depend."));
        }
    }

}
