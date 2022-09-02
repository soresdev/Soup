package me.sores.soup.commands;

import me.sores.Orion.util.cmdfrmwrk.BaseCommand;
import me.sores.Orion.util.cmdfrmwrk.CommandUsageBy;
import me.sores.soup.handler.ProfileHandler;
import me.sores.soup.kit.menu.KitSelectorMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by sores on 8/11/2022.
 */
public class KitsCommand extends BaseCommand {

    public KitsCommand(){
        super("kits", "", CommandUsageBy.PLAYER, "kit");
        setUsage("/<command>");
        setMinArgs(0);
        setMaxArgs(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        new KitSelectorMenu(ProfileHandler.getInstance().getProfile(player)).openMenu(player);
    }

}
