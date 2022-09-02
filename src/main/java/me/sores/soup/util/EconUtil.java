package me.sores.soup.util;

import me.sores.Orion.util.MessageUtil;
import me.sores.soup.handler.ProfileHandler;
import me.sores.soup.profile.SoupProfile;
import me.sores.soup.profile.stats.PlayerStats;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by sores on 8/11/2022.
 */
public class EconUtil {

    public static boolean canAfford(SoupProfile profile, int cost){
        PlayerStats stats = profile.getPlayerStats();
        return stats.getCredits() >= cost;
    }

    public static void addCredits(SoupProfile profile, int amount){
        PlayerStats stats = profile.getPlayerStats();
        stats.setCredits(stats.getCredits() + amount);
    }

    public static void takeCredits(SoupProfile profile, int amount){
        PlayerStats stats = profile.getPlayerStats();
        stats.setCredits(stats.getCredits() - amount);
    }

    public static void pay(Player player, Player target, int amount){
        SoupProfile playerProfile = ProfileHandler.getInstance().getProfile(player.getUniqueId());
        SoupProfile targetProfile = ProfileHandler.getInstance().getProfile(target.getUniqueId());

        if(target == player){
            MessageUtil.message(player, ChatColor.RED + "You cannot pay yourself.");
            return;
        }

        if(amount < 1){
            MessageUtil.message(player, ChatColor.RED + "You cannot pay less than 1 credit.");
            return;
        }

        if(!canAfford(playerProfile, amount)){
            MessageUtil.message(player, ChatColor.RED + "You do not have enough credits.");
            return;
        }

        takeCredits(playerProfile, amount);
        addCredits(targetProfile, amount);

        String creds = amount > 1 ? ChatColor.YELLOW + " credits " : ChatColor.YELLOW + " credit ";
        MessageUtil.message(player, "&eYou sent &d" + amount + creds + "to &d" + target.getName() + "&e.");
        MessageUtil.message(target, ChatColor.LIGHT_PURPLE + player.getName() + " &esent you &d" + amount + creds + ".");
    }

}
