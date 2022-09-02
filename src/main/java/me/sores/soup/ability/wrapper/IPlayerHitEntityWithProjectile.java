package me.sores.soup.ability.wrapper;

import me.sores.soup.kit.Kit;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by sores on 4/20/2021.
 */
public interface IPlayerHitEntityWithProjectile extends WrapperItem {

    void onPlayerHitEntityWithProjectile(Kit kit, EntityDamageByEntityEvent event, Projectile projectile, Player shooter, Entity hit);

}
