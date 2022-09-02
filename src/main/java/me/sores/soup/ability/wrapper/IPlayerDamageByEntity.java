package me.sores.soup.ability.wrapper;

import me.sores.soup.kit.Kit;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by sores on 4/20/2021.
 */
public interface IPlayerDamageByEntity extends WrapperItem {

    void onPlayerDamageByEntity(Kit kit, EntityDamageByEntityEvent event, Player damaaged, Entity damager);

}
