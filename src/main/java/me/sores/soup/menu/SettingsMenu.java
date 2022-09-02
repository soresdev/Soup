package me.sores.soup.menu;

import com.google.common.collect.Maps;
import me.sores.Orion.util.ItemBuilder;
import me.sores.Orion.util.menu.Button;
import me.sores.Orion.util.menu.Menu;
import me.sores.soup.profile.SoupProfile;
import me.sores.soup.profile.settings.PlayerSettings;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Map;

/**
 * Created by sores on 8/11/2022.
 */
public class SettingsMenu extends Menu {

    private final SoupProfile profile;

    public SettingsMenu(SoupProfile profile) {
        this.profile = profile;
    }

    @Override
    public String getTitle(Player player) {
        return ChatColor.DARK_GRAY + "Settings";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        PlayerSettings settings = profile.getPlayerSettings();

        buttons.put(0, new Button() {
            boolean enabled = settings.isScoreboard();

            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.ITEM_FRAME)
                        .setName(enabled ? ChatColor.GREEN + "Scoreboard" : ChatColor.RED + "Scoreboard")
                        .setLore(Collections.singletonList(ChatColor.GRAY + "Click here to turn your " + ChatColor.YELLOW + "Scoreboard "
                        + ChatColor.GRAY + (enabled ? "off." : "on.")))
                        .build();
            }

            @Override
            public void clicked(Player player, ClickType clickType) {
                settings.setScoreboard(!settings.isScoreboard());
            }

            @Override
            public boolean shouldUpdate(Player player, ClickType clickType) {
                return true;
            }
        });

        buttons.put(1, new Button() {
            boolean enabled = settings.isBountyMessages();

            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.ANVIL)
                        .setName(enabled ? ChatColor.GREEN + "Bounty Messages" : ChatColor.RED + "Bounty Messages")
                        .setLore(Collections.singletonList(ChatColor.GRAY + "Click here to turn " + ChatColor.YELLOW + "Bounty Messages "
                        + ChatColor.GRAY + (enabled ? "off." : "on.")))
                        .build();
            }

            @Override
            public void clicked(Player player, ClickType clickType) {
                settings.setBountyMessages(!enabled);
            }

            @Override
            public boolean shouldUpdate(Player player, ClickType clickType) {
                return true;
            }
        });

        buttons.put(2, new Button() {
            boolean enabled = settings.isStreakMessages();

            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.DIAMOND_SWORD)
                        .setName(enabled ? ChatColor.GREEN + "Killstreak Messages" : ChatColor.RED + "Killstreak Messages")
                        .setLore(Collections.singletonList(ChatColor.GRAY + "Click here to turn " + ChatColor.YELLOW + "Killstreak Messages "
                                + ChatColor.GRAY + (enabled ? "off." : "on.")))
                        .build();
            }

            @Override
            public void clicked(Player player, ClickType clickType) {
                settings.setStreakMessages(!enabled);
            }

            @Override
            public boolean shouldUpdate(Player player, ClickType clickType) {
                return true;
            }
        });

        return buttons;
    }

    @Override
    public int getSize() {
        return 9;
    }

}
