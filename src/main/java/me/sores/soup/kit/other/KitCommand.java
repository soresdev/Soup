package me.sores.soup.kit.other;

import me.sores.Orion.util.cmdfrmwrk.BaseCommand;
import me.sores.Orion.util.cmdfrmwrk.CommandRegistrar;
import me.sores.Orion.util.cmdfrmwrk.CommandUsageBy;
import me.sores.soup.Init;
import me.sores.soup.kit.Kit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by sores on 8/11/2022.
 */
public class KitCommand {

    private final Kit kit;

    public KitCommand(Kit kit) {
        this.kit = kit;

        setup();
    }

    private void setup(){
        try{
            CommandRegistrar registrar = Init.getInstance().getCommandRegistrar();
            registrar.registerCommand(kit.getName(), new BaseCommand(kit.getName(), "soup.kits." + kit.getName().toLowerCase(), CommandUsageBy.PLAYER) {
                @Override
                public void execute(CommandSender sender, String[] args) {
                    Player player = (Player) sender;
                    kit.apply(player);
                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
