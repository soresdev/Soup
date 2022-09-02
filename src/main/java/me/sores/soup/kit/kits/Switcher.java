package me.sores.soup.kit.kits;

import me.sores.Orion.util.ItemBuilder;
import me.sores.soup.ability.Ability;
import me.sores.soup.handler.AbilityHandler;
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
 * Created by sores on 9/2/2022.
 */
public class Switcher extends Kit {

    public Switcher() {
        super("Switcher", KitType.PAID);
    }

    @Override
    public Ability getAbility() {
        return AbilityHandler.getInstance().valueOf(getName());
    }

    @Override
    public Material getIcon() {
        return Material.SNOW_BALL;
    }

    @Override
    public int getPrice() {
        return 2750;
    }

    @Override
    public Loadout getLoadout() {
        Loadout loadout = new Loadout();

        loadout.setArmor(new ItemStack[]{
                new ItemBuilder(Material.LEATHER_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).unsafeEnchant(Enchantment.DURABILITY, 3).setLore(SoupUtil.BOUND_LORE).build(),
                new ItemBuilder(Material.IRON_LEGGINGS).setLore(SoupUtil.BOUND_LORE).build(),
                new ItemBuilder(Material.IRON_CHESTPLATE).setLore(SoupUtil.BOUND_LORE).build(),
                new ItemBuilder(Material.LEATHER_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).unsafeEnchant(Enchantment.DURABILITY, 3).setLore(SoupUtil.BOUND_LORE).build()
        });

        loadout.setContents(new ItemStack[]{
                new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).setLore(SoupUtil.BOUND_LORE).build(),
                new ItemBuilder(Material.SNOW_BALL).setAmount(3).setLore(SoupUtil.BOUND_LORE).build()
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
                "&7Hit a player with your snowball",
                "&7to switch positions with them!"
        };
    }

}
