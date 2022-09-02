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
public class Barbarian extends Kit {

    public Barbarian() {
        super("Barbarian", KitType.PAID);
    }

    @Override
    public Ability getAbility() {
        return AbilityHandler.getInstance().valueOf("Swarm");
    }

    @Override
    public Material getIcon() {
        return Material.STONE_SWORD;
    }

    @Override
    public int getPrice() {
        return 3500;
    }

    @Override
    public Loadout getLoadout() {
        Loadout loadout = new Loadout();

        loadout.setArmor(new ItemStack[]{
                new ItemStack(Material.AIR),
                new ItemStack(Material.AIR),
                new ItemStack(Material.AIR),
                new ItemStack(Material.AIR)
        });

        loadout.setContents(new ItemStack[]{
                new ItemBuilder(Material.STONE_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).unsafeEnchant(Enchantment.DURABILITY, 10).setLore(SoupUtil.BOUND_LORE).build(),
                new ItemBuilder(Material.INK_SACK).setData((short) 6).setName("&bSilverfish Swarm").setLore(SoupUtil.BOUND_LORE).build()
        });


        return loadout;
    }

    @Override
    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0),
                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 2)
        };
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "&7Summon a swarm of Silverfish to",
                "&7battle your enemies."
        };
    }

}
