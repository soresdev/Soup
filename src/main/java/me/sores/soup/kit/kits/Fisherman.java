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
 * Created by sores on 8/28/2022.
 */
public class Fisherman extends Kit {

    public Fisherman() {
        super("Fisherman", KitType.PAID);
    }

    @Override
    public Ability getAbility() {
        return AbilityHandler.getInstance().valueOf(getName());
    }

    @Override
    public Material getIcon() {
        return Material.FISHING_ROD;
    }

    @Override
    public int getPrice() {
        return 2500;
    }

    @Override
    public Loadout getLoadout() {
        Loadout loadout = new Loadout();

        loadout.setArmor(new ItemStack[]{
                new ItemBuilder(Material.GOLD_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unsafeEnchant(Enchantment.DURABILITY, 10).setLore(SoupUtil.BOUND_LORE).build(),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unsafeEnchant(Enchantment.DURABILITY, 10).setLore(SoupUtil.BOUND_LORE).build(),
                new ItemBuilder(Material.IRON_CHESTPLATE).setLore(SoupUtil.BOUND_LORE).build(),
                new ItemBuilder(Material.GOLD_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unsafeEnchant(Enchantment.DURABILITY, 10).setLore(SoupUtil.BOUND_LORE).build()
        });

        loadout.setContents(new ItemStack[]{
                new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).setLore(SoupUtil.BOUND_LORE).build(),
                new ItemBuilder(Material.FISHING_ROD).setName("&cFisherman's Rod").unsafeEnchant(Enchantment.DURABILITY, 10).setLore(SoupUtil.BOUND_LORE).build()
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
                "&7Catch a player with your rod",
                "&7and reel them in!"
        };
    }

}
