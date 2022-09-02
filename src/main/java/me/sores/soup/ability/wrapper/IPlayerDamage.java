package me.sores.soup.ability.wrapper;

import me.sores.soup.kit.Kit;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by sores on 4/20/2021.
 */
public interface IPlayerDamage extends WrapperItem {

    void onPlayerDamage(Kit kit, EntityDamageEvent event, Player damaged);

}
