package me.sores.soup.tasks;

import me.sores.Orion.util.PlayerUtil;
import me.sores.soup.handler.ProfileHandler;
import me.sores.soup.profile.SoupProfile;
import me.sores.soup.profile.settings.PlayerSettings;
import me.sores.soup.util.SoupUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by sores on 8/11/2022.
 */
public class PlayerCleanTask extends BukkitRunnable {

    private final Player player;
    private final SoupProfile profile;

    public PlayerCleanTask(Player player) {
        this.player = player;
        this.profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());
    }

    @Override
    public void run() {
        PlayerSettings settings = profile.getPlayerSettings();

        PlayerUtil.gotoSpawn(player);
        SoupUtil.clean(player);
    }

}
