package me.sores.soup.kit.kits;

import me.sores.Orion.util.ItemBuilder;
import me.sores.soup.ability.Ability;
import me.sores.soup.kit.Kit;
import me.sores.soup.kit.Loadout;
import me.sores.soup.kit.type.KitType;
import me.sores.soup.util.SoupUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by sores on 8/11/2022.
 */
public class PvP extends Kit {

    public PvP() {
        super("PvP", KitType.DEFAULT);
    }

    @Override
    public Ability getAbility() {
        return null;
    }

    @Override
    public Material getIcon() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public int getPrice() {
        return -1;
    }

    @Override
    public Loadout getLoadout() {
        Loadout loadout = new Loadout();

        loadout.setArmor(new ItemStack[]{
                new ItemBuilder(Material.IRON_BOOTS).setLore(SoupUtil.BOUND_LORE).build(),
                new ItemBuilder(Material.IRON_LEGGINGS).setLore(SoupUtil.BOUND_LORE).build(),
                new ItemBuilder(Material.IRON_CHESTPLATE).setLore(SoupUtil.BOUND_LORE).build(),
                new ItemBuilder(Material.IRON_HELMET).setLore(SoupUtil.BOUND_LORE).build()
        });

        loadout.setContents(new ItemStack[]{
                new ItemBuilder(Material.DIAMOND_SWORD).setLore(SoupUtil.BOUND_LORE).enchant(Enchantment.DAMAGE_ALL, 1).build()
        });


        return loadout;
    }

    @Override
    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)
        };
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "&7Basic PvP Class"
        };
    }

}
