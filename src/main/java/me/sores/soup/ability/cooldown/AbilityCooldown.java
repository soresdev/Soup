package me.sores.soup.ability.cooldown;

import me.sores.Orion.util.MessageUtil;
import me.sores.soup.ability.Ability;
import me.sores.soup.handler.AbilityHandler;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by sores on 8/11/2022.
 */
public class AbilityCooldown extends BukkitRunnable {

    private Player player;
    private Ability ability;
    private Long time;
    private boolean cancelled = false;

    public AbilityCooldown(Player player, Ability ability, Long time) {
        this.player = player;
        this.ability = ability;
        this.time = time;
    }

    @Override
    public void run() {
        if(cancelled || ability == null || player == null || player.isDead() || System.currentTimeMillis() >= time){
            cancel();
            MessageUtil.message(player, ChatColor.YELLOW + "You may now use " + ChatColor.LIGHT_PURPLE + ability.getType().getDisplay() + ChatColor.YELLOW + ".");
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 2f, 2f);
        }
    }

    public void cancel(){
        super.cancel();

        if(AbilityHandler.getInstance().isOnCooldown(player)){
            AbilityHandler.getInstance().getCooldowns().remove(player.getUniqueId());
        }

        this.cancelled = true;
    }

    public Player getPlayer() {
        return player;
    }

    public Ability getAbility() {
        return ability;
    }

    public Long getTime() {
        return time;
    }

    public boolean isCancelled() {
        return cancelled;
    }

}
