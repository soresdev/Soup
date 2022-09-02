package me.sores.soup.handler;

import com.google.common.collect.Maps;
import me.sores.Orion.util.TaskUtil;
import me.sores.Orion.util.handler.Handler;
import me.sores.soup.Soup;
import me.sores.soup.profile.SoupProfile;
import me.sores.soup.tasks.PlayerCleanTask;
import me.sores.soup.tasks.SaveDataTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;

/**
 * Created by sores on 8/11/2022.
 */
public class ProfileHandler extends Handler {

    private static ProfileHandler instance;
    private Map<UUID, SoupProfile> profiles;

    public ProfileHandler() {
        instance = this;
        profiles = Maps.newHashMap();
    }

    @Override
    public void init() {
        if(Bukkit.getOnlinePlayers().size() != 0){
            Bukkit.getOnlinePlayers().forEach(player -> {
                if(getProfile(player.getUniqueId()) == null){
                    profiles.put(player.getUniqueId(), new SoupProfile(player.getUniqueId()));
                }

                load(getProfile(player.getUniqueId()));
            });
        }

        TaskUtil.runTaskTimer(Soup.getInstance(), new SaveDataTask(), 20L * 60 * 10, 20L * 60 * 10, true);
    }

    @Override
    public void unload() {
        profiles.values().forEach(SoupProfile::saveData);
        instance = null;
    }

    public void load(SoupProfile profile){
        TaskUtil.runTask(Soup.getInstance(), profile::loadData, true);
    }

    public void save(SoupProfile profile){
        TaskUtil.runTask(Soup.getInstance(), profile::saveData, true);
    }

    @EventHandler
    public void onAsyncPlayerLogin(AsyncPlayerPreLoginEvent event){
        if(getProfile(event.getUniqueId()) == null){
            profiles.put(event.getUniqueId(), new SoupProfile(event.getUniqueId()));
        }

        SoupProfile profile = getProfile(event.getUniqueId());
        load(profile);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        SoupProfile profile = getProfile(player.getUniqueId());

        TaskUtil.runTaskLater(Soup.getInstance(), new PlayerCleanTask(player), 2L, false);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        SoupProfile profile = getProfile(player.getUniqueId());

        profile.setBuilding(false);

        save(profile);
        profiles.remove(player.getUniqueId());
    }

    /**
     * Handle Build mdoe
     */
    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        SoupProfile profile = getProfile(player.getUniqueId());

        if(!profile.isBuilding()) event.setCancelled(true);
    }

    @EventHandler
    public void onBuild(BlockPlaceEvent event){
        Player player = event.getPlayer();
        SoupProfile profile = getProfile(player.getUniqueId());

        if(!profile.isBuilding()) event.setCancelled(true);
    }

    public SoupProfile getProfile(Player player){
        return getProfile(player.getUniqueId());
    }

    public SoupProfile getProfile(UUID uuid){
        for(SoupProfile profile : profiles.values()){
            if(profile.getID().equals(uuid)) return profile;
        }

        return null;
    }

    public Map<UUID, SoupProfile> getProfiles() {
        return profiles;
    }

    public static ProfileHandler getInstance() {
        return instance;
    }

}
