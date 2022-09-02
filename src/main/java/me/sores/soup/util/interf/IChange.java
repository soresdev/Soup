package me.sores.soup.util.interf;

import org.bukkit.command.CommandSender;

/**
 * Created by sores on 8/11/2022.
 */
public interface IChange {

    boolean handleChange(CommandSender sender, String[] args);

}
