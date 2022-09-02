package me.sores.soup.listener;

import me.sores.Orion.util.TaskUtil;
import me.sores.soup.Soup;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Created by sores on 8/11/2022.
 */
public class WorldListener implements Listener {

    @EventHandler
    public void onLeafDecay(LeavesDecayEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onArrowStrike(ProjectileHitEvent event) {
        Projectile entity = event.getEntity();
        if (entity instanceof Arrow) {
            TaskUtil.runTaskLater(Soup.getInstance(), entity::remove, 30L, false);
        }
    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void preventMobSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG && event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSnowMelt(BlockFadeEvent event){
        Block block = event.getBlock();

        switch (block.getType()){
            case ICE:
            case SNOW:
            case PACKED_ICE:
            case SNOW_BLOCK:
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().clear();
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event){
        event.setCancelled(true);
    }

}
