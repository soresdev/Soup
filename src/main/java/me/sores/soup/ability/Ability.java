package me.sores.soup.ability;

import com.google.common.collect.Lists;
import me.sores.Orion.util.MessageUtil;
import me.sores.soup.Soup;
import me.sores.soup.ability.cooldown.AbilityCooldown;
import me.sores.soup.handler.AbilityHandler;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by sores on 8/11/2022.
 */
public abstract class Ability {

    private final AbilityType type;
    public abstract Long getCooldown();

    public Ability(AbilityType type) {
        this.type = type;
        AbilityHandler.getInstance().registerAbility(this);
    }

    public void perform(Player player, Ability ability){
        long time = System.currentTimeMillis() + ability.getCooldown();
        if(time <= 0) return;

        AbilityCooldown cooldown = new AbilityCooldown(player, ability, time);
        AbilityHandler.getInstance().addCooldown(player, cooldown);

        cooldown.runTaskTimerAsynchronously(Soup.getInstance(), 0L, 20L);
    }

    public void sendCooldownMessage(Player player, long expire){
        long exp = TimeUnit.MILLISECONDS.toSeconds(expire - System.currentTimeMillis()) + 1;
        if(exp < 0) return;

        MessageUtil.message(player, ChatColor.RED + "You can't use this for another " + ChatColor.YELLOW + exp + "s" + ChatColor.RED + ".");
    }

    public boolean isOnCooldown(Player player){
        return AbilityHandler.getInstance().isOnCooldown(player);
    }

    public void sound(Player player, Sound sound, float volume, float pitch, boolean global){
        if(global){
            player.getWorld().playSound(player.getLocation(), sound, volume, pitch);
        }else{
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
    }

    /**
     * Cleans up all ability related things once the ability is removed
     * @param player
     */
    public void destroy(Player player){
        if(!AbilityHandler.getInstance().getCooldowns().isEmpty() && AbilityHandler.getInstance().getCooldowns().containsKey(player.getUniqueId())){
            AbilityCooldown cooldown = AbilityHandler.getInstance().getCooldowns().get(player.getUniqueId());

            if(cooldown != null){
                if(!cooldown.isCancelled()) cooldown.cancel();
            }

            AbilityHandler.getInstance().getCooldowns().remove(player.getUniqueId());
        }

        cleanup(player);
    }

    /**
     * Used to clean up any data given from the ability
     * @param player
     */
    public void cleanup(Player player){}

    /**
     * This fetches all of our wrapper items in the ability class
     * @return
     */
    public List<Class<? extends WrapperItem>> fetchWrapperItems(){
        if(getClass().getInterfaces().length > 0){
            List<Class<? extends WrapperItem>> items = Lists.newArrayList();

            for(Class<?> wrapper : getClass().getInterfaces()){
                for(Class<?> a : wrapper.getInterfaces()){
                    if(a == WrapperItem.class) items.add((Class<? extends WrapperItem>) wrapper);
                }
            }

            return items;
        }

        return null;
    }

    public AbilityType getType() {
        return type;
    }

}
