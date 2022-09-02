package me.sores.soup.handler;

import com.google.common.collect.Lists;
import me.sores.Orion.util.handler.Handler;
import me.sores.soup.kit.Kit;
import me.sores.soup.kit.kits.*;
import me.sores.soup.kit.other.KitCommand;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sores on 8/11/2022.
 */
public class KitsHandler extends Handler {

    private static KitsHandler instance;
    private List<Kit> kits;

    public KitsHandler() {
        instance = this;
        kits = Lists.newArrayList();
    }

    @Override
    public void init() {
        kits.addAll(Arrays.asList(
                new PvP(),
                new Pro(),
                new Fisherman(),
                new Switcher(),
                new Barbarian()
        ));

        kits.forEach(KitCommand::new);
    }

    @Override
    public void unload() {
        kits.clear();
    }

    public Kit valueOf(String name){
        for(Kit kit : kits){
            if(kit.getName().equalsIgnoreCase(name)) return kit;
        }

        return null;
    }

    public List<Kit> getKits() {
        return kits;
    }

    public static KitsHandler getInstance() {
        return instance;
    }

}
