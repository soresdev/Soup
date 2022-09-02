package me.sores.soup.ability.wrapper;

import me.sores.soup.kit.Kit;
import me.sores.soup.util.interf.WrapperItem;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * Created by sores on 4/20/2021.
 */
public interface IEntityExplode extends WrapperItem {

    void onEntityExplode(Kit kit, EntityExplodeEvent event, Entity entity);


}
