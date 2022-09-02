package me.sores.soup.scoreboard;

import com.google.common.collect.Lists;
import me.sores.Orion.util.StringUtil;
import me.sores.Orion.util.scoreboard.AssembleAdapter;
import me.sores.soup.handler.AbilityHandler;
import me.sores.soup.handler.ProfileHandler;
import me.sores.soup.kit.Kit;
import me.sores.soup.profile.SoupProfile;
import me.sores.soup.profile.stats.PlayerStats;
import me.sores.soup.util.SoupUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by sores on 8/11/2022.
 */
public class SoupAdapter implements AssembleAdapter {

    @Override
    public String getTitle(Player player) {
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());
        return profile.getPlayerSettings().isScoreboard() ? ChatColor.GOLD.toString() + ChatColor.BOLD + "SoupPvP" : "";
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = Lists.newArrayList();
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());
        PlayerStats stats = profile.getPlayerStats();
        ChatColor base = ChatColor.YELLOW, sec = ChatColor.WHITE;

        lines.add("&7&m" + StringUtils.repeat("-", 20));

        lines.add(base + "Kills: " + sec + stats.getKills());
        lines.add(base + "Killstreak: " + sec + stats.getStreak());
        lines.add(base + "Deaths: " + sec + stats.getDeaths());
        lines.add(base + "Credits: " + sec + stats.getCredits());

        lines.add("&7&m" + StringUtils.repeat("-", 20));

        if(AbilityHandler.getInstance().isOnCooldown(player)){
            Kit kit = profile.getSelectedKit();
            long time = AbilityHandler.getInstance().getCooldownTime(player);

            lines.add(base + kit.getAbility().getType().getDisplay() + ": " + sec +
                    (time > System.currentTimeMillis() ? SoupUtil.combatFormat.format((time - System.currentTimeMillis()) / 1000.0) + "s" : ""));
        }

        return profile.getPlayerSettings().isScoreboard() ? StringUtil.color(lines) : null;
    }

}
