package me.sores.soup.ability.abilities;

import com.google.common.collect.Lists;
import me.sores.Orion.util.TaskUtil;
import me.sores.soup.Soup;
import me.sores.soup.ability.Ability;
import me.sores.soup.ability.AbilityType;
import me.sores.soup.ability.wrapper.IInteract;
import me.sores.soup.handler.AbilityHandler;
import me.sores.soup.kit.Kit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Created by sores on 9/2/2022.
 */
public class Swarm extends Ability implements IInteract, Listener {

    private final String SWARM_METADATA = "swarm", USED_METADATA = "used";

    public Swarm(){
        super(AbilityType.SWARM);
    }

    @Override
    public void cleanup(Player player) {
        super.cleanup(player);

        if(player.hasMetadata(USED_METADATA)){
            List<Entity> entities = (List<Entity>) player.getMetadata(USED_METADATA).get(0).value();

            for(Entity entity : entities){
                if(entity != null && !entity.isDead() && entity.isValid()){
                    entity.remove();
                }
            }

            player.removeMetadata(USED_METADATA, Soup.getInstance());
        }
    }

    @Override
    public void onPlayerInteract(Kit kit, PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(item == null || item.getType() != Material.INK_SACK) return;

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(isOnCooldown(player)){
                event.setCancelled(true);
                sendCooldownMessage(player, AbilityHandler.getInstance().getCooldownTime(player));
                return;
            }

            Location location = player.getLocation().clone();
            List<Entity> entities = Lists.newArrayList();

            for(int i = 0; i < 360; i += 60){
                double rad = Math.toRadians(i);
                double x = Math.cos(rad) * 3;
                double z = Math.sin(rad) * 3;

                location.add(x, 0, z);

                Silverfish fish = (Silverfish) player.getWorld().spawnEntity(location, EntityType.SILVERFISH);
                fish.setMetadata(SWARM_METADATA, new FixedMetadataValue(Soup.getInstance(), player.getUniqueId().toString()));
                fish.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
                fish.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
                fish.setMaxHealth(30.0D);
                fish.setHealth(fish.getMaxHealth());
                fish.setVelocity(new Vector());

                entities.add(fish);
                location.subtract(x, 0, z);
            }

            player.setMetadata(USED_METADATA, new FixedMetadataValue(Soup.getInstance(), entities));

            TaskUtil.runTaskLater(Soup.getInstance(), () -> {
                for(Entity entity : entities){
                    if(entity != null && !entity.isDead() && entity.isValid()){
                        entity.remove();
                    }
                }

                if(player.hasMetadata(USED_METADATA)) player.removeMetadata(USED_METADATA, Soup.getInstance());
            }, (8 * 20L), false);

            sound(player, Sound.ENDERDRAGON_HIT, 1f, 1f, true);
            perform(player, this);
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event){
        if(event.getEntity() instanceof Silverfish && event.getEntity().hasMetadata(SWARM_METADATA) && event.getTarget() instanceof Player){
            Player player = (Player) event.getTarget();
            List<MetadataValue> metadataValues = event.getEntity().getMetadata(SWARM_METADATA);

            if(player.getUniqueId().toString().equals(metadataValues.get(0).asString())){
                event.setCancelled(true);
                event.setTarget(null);
            }
        }
    }

    @Override
    public Long getCooldown() {
        return 30000L;
    }

}
