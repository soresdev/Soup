package me.sores.soup.kit.menu.button;

import com.google.common.collect.Lists;
import me.sores.Orion.util.ItemBuilder;
import me.sores.Orion.util.MessageUtil;
import me.sores.Orion.util.StringUtil;
import me.sores.Orion.util.TypeCallback;
import me.sores.Orion.util.menu.Button;
import me.sores.Orion.util.menu.buttons.DisplayButton;
import me.sores.Orion.util.menu.menus.ConfirmMenu;
import me.sores.soup.kit.Kit;
import me.sores.soup.kit.menu.KitPreview;
import me.sores.soup.kit.menu.KitSelectorMenu;
import me.sores.soup.profile.SoupProfile;
import me.sores.soup.util.EconUtil;
import me.sores.soup.util.SoupUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sores on 8/11/2022.
 */
public class KitButton extends Button {

    private final Kit kit;
    private final SoupProfile profile;

    public KitButton(Kit kit, SoupProfile profile) {
        this.kit = kit;
        this.profile = profile;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = Lists.newArrayList();
        boolean has = profile.hasKit(kit);
        String name = has ? ChatColor.GREEN + kit.getName() : ChatColor.RED + kit.getName();

        lore.add(SoupUtil.MENU_BAR);
        lore.addAll(Arrays.asList(kit.getDescription()));
        lore.add(SoupUtil.MENU_BAR);

        if(!has){
            lore.add("");
            lore.add("&ePrice: " + ChatColor.RESET + kit.getPrice());
            lore.add("&aClick here to purchase.");
        }

        lore.add("");
        lore.add(ChatColor.GRAY + "(Right click to preview)");

        return new ItemBuilder(kit.getIcon()).setName(name).setLore(StringUtil.color(lore)).build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        boolean has = profile.hasKit(kit);

        if(clickType == ClickType.RIGHT){
            new KitPreview(kit, profile).openMenu(player);
            return;
        }

        if(!has){
            if(!EconUtil.canAfford(profile, kit.getPrice())){
                MessageUtil.message(player, ChatColor.RED + "Not enough credits!");
            }else{
                new ConfirmMenu("Purchase " + kit.getName(), new TypeCallback<Boolean>() {
                    @Override
                    public void callback(Boolean purchase) {
                        if(purchase){
                            profile.purchaseKit(kit);
                            player.closeInventory();
                        }else{
                            new KitSelectorMenu(profile).openMenu(player);
                        }
                    }
                }, true, new DisplayButton(new ItemBuilder(kit.getIcon()).setName(kit.getName()).build(), true)).openMenu(player);
            }

            return;
        }

        kit.apply(player);
        player.closeInventory();
    }

}
