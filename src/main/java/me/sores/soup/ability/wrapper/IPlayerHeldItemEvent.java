package me.sores.soup.ability.wrapper;

import me.sores.soup.kit.Kit;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemHeldEvent;

/**
 * Created by sores on 4/20/2021.
 */
public interface IPlayerHeldItemEvent extends WrapperItem {

    void onPlayerHeldItem(Kit kit, PlayerItemHeldEvent event, Player player);

}
