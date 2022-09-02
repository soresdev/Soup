package me.sores.soup.ability.wrapper;

import me.sores.soup.kit.Kit;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.event.vehicle.VehicleExitEvent;

/**
 * Created by sores on 4/20/2021.
 */
public interface IPlayerUnmount extends WrapperItem {

    void onPlayerUnmount(Kit kit, VehicleExitEvent event);

}
