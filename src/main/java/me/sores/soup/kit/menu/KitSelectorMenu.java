package me.sores.soup.kit.menu;

import com.google.common.collect.Maps;
import me.sores.Orion.util.menu.Button;
import me.sores.Orion.util.menu.Menu;
import me.sores.soup.handler.KitsHandler;
import me.sores.soup.kit.Kit;
import me.sores.soup.kit.menu.button.KitButton;
import me.sores.soup.profile.SoupProfile;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Created by sores on 8/11/2022.
 */
public class KitSelectorMenu extends Menu {

    private final SoupProfile profile;

    public KitSelectorMenu(SoupProfile profile) {
        this.profile = profile;

        setAutoUpdate(true);
        setUpdateAfterClick(true);
    }

    @Override
    public String getTitle(Player player) {
        return ChatColor.DARK_GRAY + "Kit Selection";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        int index = 0;
        for(Kit kit : KitsHandler.getInstance().getKits()){
            buttons.put(index, new KitButton(kit, profile));
            index++;
        }

        return buttons;
    }

    @Override
    public int getSize() {
        return 27;
    }

}
