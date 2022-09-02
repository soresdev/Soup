package me.sores.soup.kit.menu;

import com.google.common.collect.Lists;
import me.sores.Orion.util.ItemBuilder;
import me.sores.Orion.util.StringUtil;
import me.sores.Orion.util.menu.Button;
import me.sores.Orion.util.menu.Menu;
import me.sores.Orion.util.menu.buttons.BackButton;
import me.sores.Orion.util.menu.buttons.DisplayButton;
import me.sores.soup.kit.Kit;
import me.sores.soup.kit.Loadout;
import me.sores.soup.profile.SoupProfile;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.*;

/**
 * Created by sores on 8/31/2022.
 */
public class KitPreview extends Menu {

    private final Kit kit;
    private final SoupProfile profile;

    public KitPreview(Kit kit, SoupProfile profile) {
        this.kit = kit;
        this.profile = profile;

        setAutoUpdate(true);
        setUpdateAfterClick(true);
    }

    @Override
    public String getTitle(Player player) {
        return ChatColor.GRAY + "Previewing " + kit.getName() + " kit";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        Loadout loadout = kit.getLoadout();

        int index = 0;
        for(ItemStack item : loadout.getContents()){
            buttons.put(index, createPreviewItem(item));
            index++;
        }

        buttons.put(41, createPreviewItem(loadout.getArmor()[3]));
        buttons.put(42, createPreviewItem(loadout.getArmor()[2]));
        buttons.put(43, createPreviewItem(loadout.getArmor()[1]));
        buttons.put(44, createPreviewItem(loadout.getArmor()[0]));

        buttons.put(45, new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.PAPER).setName(ChatColor.GRAY + "Kit Information").setLore(Arrays.asList(
                        StringUtil.color("&7Ability: " + ChatColor.YELLOW + (kit.getAbility() != null ? kit.getAbility().getType().getDisplay() : "None")),
                        StringUtil.color("&7Ability Cooldown: " + ChatColor.YELLOW + (kit.getAbility() != null ? kit.getAbility().getCooldown() / 1000 + "s" : "N/A"))
                )).build();
            }
        });

        List<String> lore = Lists.newArrayList();
        if(kit.getPotionEffects().length > 0){
            for(PotionEffect effect : kit.getPotionEffects()){
                lore.add(StringUtil.color("&7- &r" + effect.getType().getName() + " " + (effect.getAmplifier() + 1)));
            }
        }else{
            lore.add(ChatColor.GRAY + "No effects");
        }

        buttons.put(46, new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.GLASS_BOTTLE).setName("&7Effects").setLore(lore).build();
            }
        });

        buttons.put(53, new BackButton(new KitSelectorMenu(profile)));

        return buttons;
    }

    private DisplayButton createPreviewItem(ItemStack item){
        return new DisplayButton(item, true);
    }

}
