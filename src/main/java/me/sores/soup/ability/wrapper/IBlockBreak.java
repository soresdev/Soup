package me.sores.soup.ability.wrapper;

import me.sores.soup.kit.Kit;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by sores on 4/20/2021.
 */
public interface IBlockBreak extends WrapperItem {

    void onPlayerBlockBreakEvent(Kit kit, BlockBreakEvent event);

}
