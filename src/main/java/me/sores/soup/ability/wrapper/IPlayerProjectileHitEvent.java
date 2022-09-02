package me.sores.soup.ability.wrapper;

import me.sores.soup.kit.Kit;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.event.entity.ProjectileHitEvent;

/**
 * Created by sores on 4/20/2021.
 */
public interface IPlayerProjectileHitEvent extends WrapperItem {

    void onPlayerProjectileHitEvent(Kit kit, ProjectileHitEvent event);

}
