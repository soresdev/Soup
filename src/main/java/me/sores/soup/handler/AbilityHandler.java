package me.sores.soup.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.sores.Orion.util.handler.Handler;
import me.sores.soup.Soup;
import me.sores.soup.ability.Ability;
import me.sores.soup.ability.AbilityType;
import me.sores.soup.ability.cooldown.AbilityCooldown;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by sores on 8/11/2022.
 */
public class AbilityHandler extends Handler {

    private static AbilityHandler instance;
    private List<Ability> abilities;

    private Map<UUID, AbilityCooldown> cooldowns;

    public AbilityHandler() {
        instance = this;
        abilities = Lists.newArrayList();
        cooldowns = Maps.newHashMap();
    }

    @Override
    public void init() {
        initAbilities();
    }

    @Override
    public void unload() {
        cooldowns.clear();
        abilities.clear();
    }

    public void initAbilities(){
        try{
            for(AbilityType type : AbilityType.values()){
                type.init();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void registerAbility(Ability ability){
        if(ability instanceof Listener){
            Soup.getInstance().getServer().getPluginManager().registerEvents((Listener) ability, Soup.getInstance());
        }

        abilities.add(ability);
    }

    public void addCooldown(Player player, AbilityCooldown cooldown){
        cooldowns.put(player.getUniqueId(), cooldown);
    }

    public long getCooldownTime(Player player){
        return cooldowns.get(player.getUniqueId()).getTime();
    }

    public boolean isOnCooldown(Player player){
        long expire = cooldowns.containsKey(player.getUniqueId()) ? cooldowns.get(player.getUniqueId()).getTime() : -1;
        return expire > 0 && !hasExpired(expire);
    }

    public boolean hasExpired(long time){
        return System.currentTimeMillis() >= time;
    }


    public Ability valueOf(String name){
        for(Ability ability : abilities){
            if(ability.getType().toString().equalsIgnoreCase(name)) return ability;
        }

        return null;
    }

    public Map<UUID, AbilityCooldown> getCooldowns() {
        return cooldowns;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public static AbilityHandler getInstance() {
        return instance;
    }
}
