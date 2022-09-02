package me.sores.soup.ability.wrapper;

import me.sores.soup.kit.Kit;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileLaunchEvent;

/**
 * Created by sores on 4/20/2021.
 */
public interface IProjectileLaunch extends WrapperItem {

    void onProjectileLaunch(Kit kit, ProjectileLaunchEvent event, Player shooter);

}
