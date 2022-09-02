package me.sores.soup.ability.abilities;

import me.sores.Orion.util.ItemBuilder;
import me.sores.Orion.util.MessageUtil;
import me.sores.soup.ability.Ability;
import me.sores.soup.ability.AbilityType;
import me.sores.soup.ability.wrapper.IInteract;
import me.sores.soup.ability.wrapper.IKillGained;
import me.sores.soup.ability.wrapper.IPlayerHitEntityWithProjectile;
import me.sores.soup.handler.AbilityHandler;
import me.sores.soup.kit.Kit;
import me.sores.soup.util.SoupUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sores on 9/2/2022.
 */
public class Switcher extends Ability implements IInteract, IPlayerHitEntityWithProjectile, IKillGained {

    public Switcher() {
        super(AbilityType.SWITCHER);
    }

    @Override
    public void onPlayerInteract(Kit kit, PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(item == null || item.getType() != Material.SNOW_BALL) return;

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(isOnCooldown(player)){
                event.setCancelled(true);
                player.updateInventory();
                sendCooldownMessage(player, AbilityHandler.getInstance().getCooldownTime(player));
            }
        }
    }

    @Override
    public void onPlayerHitEntityWithProjectile(Kit kit, EntityDamageByEntityEvent event, Projectile projectile, Player shooter, Entity hit) {
        if(projectile instanceof Snowball && hit instanceof Player){
            Location location = hit.getLocation();

            if(hit == shooter){
                MessageUtil.message(shooter, ChatColor.RED + "You cannot switch yourself.");
                event.setCancelled(true);
                return;
            }

            event.setDamage(0);
            event.getDamager().remove();

            hit.teleport(shooter.getLocation(), PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
            shooter.teleport(location, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);

            MessageUtil.message(shooter, ChatColor.YELLOW + "You switched " + ChatColor.LIGHT_PURPLE + hit.getName() + ChatColor.YELLOW + "!");
            MessageUtil.message(hit, ChatColor.YELLOW + "You were switched by " + ChatColor.LIGHT_PURPLE + shooter.getName() + ChatColor.YELLOW + "!");

            perform(shooter, this);
        }
    }

    @Override
    public void onKillGained(Kit kit, PlayerDeathEvent event, Player killed, Player killer) {
        SoupUtil.addItem(killer, new ItemBuilder(Material.SNOW_BALL).setLore(SoupUtil.BOUND_LORE).build(), 3);
    }

    @Override
    public Long getCooldown() {
        return 5000L;
    }

}
