package me.sores.soup.kit;

import me.sores.Orion.util.MessageUtil;
import me.sores.soup.ability.Ability;
import me.sores.soup.handler.ProfileHandler;
import me.sores.soup.kit.type.KitType;
import me.sores.soup.profile.SoupProfile;
import me.sores.soup.util.SoupUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

/**
 * Created by sores on 8/11/2022.
 */
public abstract class Kit {

    private final String name;
    private final KitType type;

    public Kit(String name, KitType type) {
        this.name = name;
        this.type = type;
    }

    public abstract Material getIcon();

    public abstract int getPrice();

    public abstract Loadout getLoadout();

    public abstract PotionEffect[] getPotionEffects();

    public abstract String[] getDescription();

    public abstract Ability getAbility();

    public boolean isDefault(){
        return getPrice() <= 0 || getType() == KitType.DEFAULT;
    }

    public boolean hasAbility(){
        return getAbility() != null;
    }

    public void apply(Player player){
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player);

        if(!profile.hasKit(this)){
            MessageUtil.message(player, ChatColor.RED + "You do not have permission to use this kit.");
            return;
        }

        if(profile.hasSelectedKit()){
            MessageUtil.message(player, ChatColor.RED + "You already have a kit selected.");
            player.closeInventory();
            return;
        }

        player.closeInventory();
        SoupUtil.resetPlayer(player);

        player.getInventory().setArmorContents(getLoadout().getArmor());
        player.getInventory().setContents(getLoadout().getContents());

        for(PotionEffect potionEffect : getPotionEffects()){
            player.addPotionEffect(potionEffect);
        }

        for(int i = 0; i < player.getInventory().getSize(); i++){
            player.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
        }

        player.updateInventory();
        profile.setSelectedKit(this);

        MessageUtil.message(player, ChatColor.YELLOW + "You have chosen the " + ChatColor.LIGHT_PURPLE + getName() + ChatColor.YELLOW + " kit.");
    }

    public void destroy(Player player){
        SoupUtil.resetPlayer(player);

        if(hasAbility()){
            getAbility().destroy(player);
        }
    }

    public String getName() {
        return name;
    }

    public KitType getType() {
        return type;
    }
}
