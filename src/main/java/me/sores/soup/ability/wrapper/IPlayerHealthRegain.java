package me.sores.soup.ability.wrapper;

import me.sores.soup.kit.Kit;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.event.entity.EntityRegainHealthEvent;

/**
 * Created by sores on 4/20/2021.
 */
public interface IPlayerHealthRegain extends WrapperItem {

    void onPlayerHealthRegen(Kit kit, EntityRegainHealthEvent event);


}
