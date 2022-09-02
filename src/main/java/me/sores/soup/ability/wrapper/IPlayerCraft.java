package me.sores.soup.ability.wrapper;

import me.sores.soup.kit.Kit;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.event.inventory.CraftItemEvent;

/**
 * Created by sores on 4/20/2021.
 */
public interface IPlayerCraft extends WrapperItem {

    void onPlayerCraft(Kit kit, CraftItemEvent event);


}
