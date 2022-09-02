package me.sores.soup.handler;

import me.sores.Orion.util.handler.Handler;
import me.sores.Orion.util.scoreboard.Assemble;
import me.sores.Orion.util.scoreboard.AssembleBoard;
import me.sores.Orion.util.scoreboard.AssembleStyle;
import me.sores.soup.Soup;
import me.sores.soup.scoreboard.SoupAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by sores on 8/11/2022.
 */
public class BoardHandler extends Handler {

    private static BoardHandler instance;

    public BoardHandler() {
        instance = this;
    }

    @Override
    public void init() {
        Assemble assemble = new Assemble(Soup.getInstance(), new SoupAdapter());
        assemble.setAssembleStyle(AssembleStyle.MODERN);
        assemble.setTicks(2);

        if(Bukkit.getOnlinePlayers().size() != 0) cleanBoards();
    }

    @Override
    public void unload() {

    }

    public void addBoard(Player player){
        Assemble.getInstance().getBoards().put(player.getUniqueId(), new AssembleBoard(player));
    }

    public void removeBoard(Player player){
        Assemble.getInstance().getBoards().remove(player.getUniqueId());
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public void cleanBoards(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            removeBoard(player);
            addBoard(player);
        });
    }

    public static BoardHandler getInstance() {
        return instance;
    }

}
