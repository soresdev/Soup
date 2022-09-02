package me.sores.soup.ability.wrapper;

import me.sores.soup.kit.Kit;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by sores on 4/20/2021.
 */
public interface IInteract extends WrapperItem {

    void onPlayerInteract(Kit kit, PlayerInteractEvent event);

}
