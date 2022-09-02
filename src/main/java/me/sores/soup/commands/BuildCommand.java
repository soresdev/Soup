package me.sores.soup.commands;

import me.sores.Orion.util.MessageUtil;
import me.sores.Orion.util.PlayerUtil;
import me.sores.Orion.util.cmdfrmwrk.BaseCommand;
import me.sores.Orion.util.cmdfrmwrk.CommandUsageBy;
import me.sores.soup.handler.ProfileHandler;
import me.sores.soup.profile.SoupProfile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by sores on 8/11/2022.
 */
public class BuildCommand extends BaseCommand {

    public BuildCommand(){
        super("build", "soup.command.build", CommandUsageBy.PLAYER);
        setUsage("/<command> [playerName]");
        setMinArgs(0);
        setMaxArgs(1);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0){
            SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());
            profile.setBuilding(!profile.isBuilding());
            MessageUtil.message(player, profile.isBuilding() ? "&eYou are &anow &ein build mode." : "&eYou are &cno longer &ein build mode.");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(!PlayerUtil.doesExist(target)){
            MessageUtil.message(player, ChatColor.RED + "No player with the name " + args[0] + " found.");
            return;
        }

        SoupProfile targetProfile = ProfileHandler.getInstance().getProfile(target.getUniqueId());
        targetProfile.setBuilding(!targetProfile.isBuilding());

        MessageUtil.message(player, ChatColor.GREEN + target.getName() + (targetProfile.isBuilding() ? " &eis &anow &ein build mode." : " &eis &cno longer &ein build mode."));
    }

}
