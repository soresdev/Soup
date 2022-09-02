package me.sores.soup.commands;

import me.sores.Orion.util.cmdfrmwrk.BaseCommand;
import me.sores.Orion.util.cmdfrmwrk.CommandUsageBy;
import me.sores.soup.handler.ProfileHandler;
import me.sores.soup.menu.SettingsMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by sores on 8/11/2022.
 */
public class SettingsCommand extends BaseCommand {

    public SettingsCommand(){
        super("settings", "soup.command.settings", CommandUsageBy.PLAYER, "options");
        setUsage("/<command>");
        setMinArgs(0);
        setMaxArgs(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        new SettingsMenu(ProfileHandler.getInstance().getProfile(player)).openMenu(player);
    }

}
