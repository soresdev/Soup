package me.sores.soup.ability.wrapper;

import me.sores.Orion.util.moreprojectiles.event.ItemProjectileHitEvent;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.entity.Player;

/**
 * Created by sores on 8/5/2022.
 */
public interface IItemProjectileHit extends WrapperItem {

    void onItemProjectileHit(Player shooter, Player hit, ItemProjectileHitEvent event);

}
