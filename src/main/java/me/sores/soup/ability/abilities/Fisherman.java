package me.sores.soup.ability.abilities;

import me.sores.Orion.util.LocationUtil;
import me.sores.Orion.util.MessageUtil;
import me.sores.soup.ability.Ability;
import me.sores.soup.ability.AbilityType;
import me.sores.soup.ability.wrapper.IFish;
import me.sores.soup.ability.wrapper.IProjectileLaunch;
import me.sores.soup.handler.AbilityHandler;
import me.sores.soup.kit.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * Created by sores on 8/28/2022.
 */
public class Fisherman extends Ability implements IFish, IProjectileLaunch {

    public Fisherman() {
        super(AbilityType.FISHERMAN);
    }

    @Override
    public void onPlayerFish(Kit kit, PlayerFishEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();
        if(item == null || item.getType() != Material.FISHING_ROD) return;

        if(event.getCaught() != null && event.getCaught() instanceof Player){
            if(isOnCooldown(player)){
                sendCooldownMessage(player, AbilityHandler.getInstance().getCooldownTime(player));
                return;
            }

            if(!LocationUtil.hasAnyBlockUnder(player.getLocation(), 120)){
                MessageUtil.message(player, ChatColor.RED + "You cannot use your fisherman ability right now.");
                return;
            }

            Player target = (Player) event.getCaught();

            if(player == target){
                MessageUtil.message(player, ChatColor.RED + "You cannot fish yourself.");
                return;
            }

            target.damage(0, player);
            target.teleport(player, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
            MessageUtil.message(target, ChatColor.RED + "You have been fished by " + player.getName() + ".");
            target.setVelocity(new Vector());

            perform(player, this);
        }
    }

    @Override
    public void onProjectileLaunch(Kit kit, ProjectileLaunchEvent event, Player shooter) {
        if(shooter.getItemInHand() == null || shooter.getItemInHand().getType() != Material.FISHING_ROD) return;

        if(event.getEntity() instanceof FishHook){
            if(isOnCooldown(shooter)){
                sendCooldownMessage(shooter, AbilityHandler.getInstance().getCooldownTime(shooter));
                event.setCancelled(true);
            }
        }
    }

    @Override
    public Long getCooldown() {
        return 8000L;
    }

}

