package me.sores.soup.ability.wrapper;

import me.sores.Orion.util.moreprojectiles.event.BlockProjectileHitEvent;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.entity.Player;

/**
 * Created by sores on 8/4/2022.
 */
public interface IBlockProjetileHitBlock extends WrapperItem {

    void onBlockPrjectileHitBlock(Player shooter, BlockProjectileHitEvent event);

}
