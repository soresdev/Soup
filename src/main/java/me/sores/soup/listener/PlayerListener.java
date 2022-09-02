package me.sores.soup.listener;

import me.sores.Orion.util.LocationUtil;
import me.sores.Orion.util.StringUtil;
import me.sores.soup.util.enumeration.SpongeFaces;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Iterator;

/**
 * Created by sores on 8/11/2022.
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void onJoinMessage(PlayerJoinEvent event){
        event.setJoinMessage(null);
    }

    @EventHandler
    public void onLeaveMessage(PlayerQuitEvent event){
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onBed(PlayerBedEnterEvent event){
        event.setCancelled(true);
    }

    /**
     * Str Fix
     */
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            if (event.getDamager() != null) {
                if ((event.getDamager() instanceof Player)) {
                    Player player = (Player) event.getDamager();

                    Iterator<PotionEffect> iterator = player.getActivePotionEffects().iterator();
                    while (iterator.hasNext()) {
                        PotionEffect eff = iterator.next();
                        if (eff.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
                            int level = eff.getAmplifier() + 1;
                            event.setDamage(10.0D * event.getDamage() / (10.0D + 13.0D * level) + 13.0D * event.getDamage() * level * 30 / 200.0D / (10.0D + 13.0D * level));
                        }
                    }
                }
            }
        }
    }

    /**
     * Achievement cancel
     */
    @EventHandler
    public void onAchievementCancel(PlayerAchievementAwardedEvent event){
        event.setCancelled(true);
    }

    /**
     * Disable hunger
     */
    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event){
        event.setCancelled(true);
    }

    /**
     * Soup logic
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();
        if(player.isDead()) return;

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(item != null && item.getType() != Material.AIR){
                if(item.getType() == Material.MUSHROOM_SOUP){
                    if(player.getHealth() == player.getMaxHealth()) return;

                    player.getItemInHand().setType(Material.BOWL);
                    player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + 6.5F));
                }
            }
        }
    }

    /**
     * Refill signs
     */
    @EventHandler
    public void onRefillSignPlace(SignChangeEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if(block.getState() instanceof Sign){
            Sign sign = (Sign) block.getState();

            if(player.hasPermission("soup.signs")){
                if(event.getLine(0).equalsIgnoreCase("<refill>")){
                    event.setLine(0, "");
                    event.setLine(1, StringUtil.color("&9&l- Free -"));
                    event.setLine(2, StringUtil.color("Soup"));
                    event.setLine(3, "");
                    sign.update();
                }
            }
        }
    }

    @EventHandler
    public void onOpenRefillInventory(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && block.getState() instanceof Sign){
            Sign sign = (Sign) block.getState();

            if(sign.getLine(1).equalsIgnoreCase(StringUtil.color("&9&l- Free -")) && sign.getLine(2).equalsIgnoreCase(StringUtil.color("Soup"))){
                Inventory inventory = Bukkit.createInventory(player, 54, StringUtil.color("&6Refill"));

                for(int i = 0; i < inventory.getSize(); i++){
                    inventory.addItem(new ItemStack(Material.MUSHROOM_SOUP));
                }

                player.openInventory(inventory);
            }
        }
    }

    /**
     * Sponge and other movement features
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMoveEvent(PlayerMoveEvent event) {
        if(!LocationUtil.isDifferentBlock(event.getFrom(), event.getTo())) return;
        Player player = event.getPlayer();
        Location baseLocation = event.getTo().clone().subtract(0, .95D, 0);

        if (baseLocation.getBlock().getType() == Material.SPONGE) {
            Vector send = new Vector(0, 0, 0);

            if (baseLocation.subtract(0, 1, 0).getBlock().getType() == Material.SPONGE) {
                for (SpongeFaces face : SpongeFaces.values()) {
                    Location curLocation = baseLocation.clone().add(face.getxOffset(), 0, face.getzOffset());
                    int tri = 0;

                    while (tri < 10 && curLocation.getBlock().getType() == Material.SPONGE) {
                        curLocation.add(face.getxOffset(), face.getyOffset(), face.getzOffset());
                        face.addToVector(send, .7);
                        tri++;
                    }
                }
                try {
                    player.setVelocity(send);
                    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1f, 1f);
                }
                catch (Exception ex) {
                }
            }
        }
    }

    @EventHandler
    public void onLandHaybale(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (event.getEntity().getLocation().subtract(0, 1, 0).getBlock().getType() == Material.HAY_BLOCK ||
                    event.getEntity().getLocation().subtract(0, 1, 0).getBlock().getType() == Material.SPONGE) {
                event.setCancelled(true);
            }
        }
    }

}
