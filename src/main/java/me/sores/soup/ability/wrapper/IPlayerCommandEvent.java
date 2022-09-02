package me.sores.soup.ability.wrapper;

import me.sores.soup.kit.Kit;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by sores on 4/20/2021.
 */
public interface IPlayerCommandEvent extends WrapperItem {

    void onPlayerCommand(Kit kit, PlayerCommandPreprocessEvent event);


}
