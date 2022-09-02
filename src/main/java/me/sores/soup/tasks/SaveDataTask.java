package me.sores.soup.tasks;

import me.sores.Orion.util.StringUtil;
import me.sores.soup.handler.ProfileHandler;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by sores on 8/11/2022.
 */
public class SaveDataTask extends BukkitRunnable {

    @Override
    public void run() {
        if(Bukkit.getOnlinePlayers().size() == 0) return;

        try{
            ProfileHandler.getInstance().getProfiles().forEach(((uuid, profile) -> profile.saveData()));
            StringUtil.log("&e[Soup] Complete SaveDataTask, saved " + ProfileHandler.getInstance().getProfiles().size() + " profiles.");
        }catch (Exception ex){
            StringUtil.log("&c[Soup] Failed to complete SaveDataTask, see log.");
            ex.printStackTrace();
        }
    }

}
