package me.sores.soup.ability.wrapper;

import me.sores.soup.kit.Kit;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.event.player.PlayerFishEvent;

/**
 * Created by sores on 4/20/2021.
 */
public interface IFish extends WrapperItem {

    void onPlayerFish(Kit kit, PlayerFishEvent event);

}
