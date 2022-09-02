package me.sores.soup.ability.wrapper;

import me.sores.soup.kit.Kit;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Created by sores on 4/20/2021.
 */
public interface IPlayerRespawn extends WrapperItem {

    void onPlayerRespawn(Kit kit, PlayerRespawnEvent event);

}
