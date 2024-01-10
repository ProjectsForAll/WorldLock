package host.plas.worldlock;

import host.plas.worldlock.commands.WorldsCMD;
import host.plas.worldlock.configs.MyConfig;
import host.plas.worldlock.events.MainListener;
import io.streamlined.bukkit.PluginBase;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentSkipListMap;

@Getter @Setter
public final class WorldLock extends PluginBase {
    @Getter @Setter
    private static WorldLock instance;

    @Getter @Setter
    private static MyConfig myConfig;

    @Getter @Setter
    private static MainListener mainListener;

    @Getter @Setter
    private static WorldsCMD worldsCMD;

    public WorldLock() {
        super();
    }

    @Override
    public void onBaseEnabled() {
        // Plugin startup logic
        setInstance(this);

        setMyConfig(new MyConfig());

        setMainListener(new MainListener());
        Bukkit.getPluginManager().registerEvents(getMainListener(), this);

        setWorldsCMD(new WorldsCMD());
        getWorldsCMD().register();
    }

    @Override
    public void onBaseDisable() {
        // Plugin shutdown logic
    }

    /**
     * Get a map of online players.
     * Sorted by player name.
     * @return A map of online players sorted by player name.
     */
    public ConcurrentSkipListMap<String, Player> getOnlinePlayers() {
        ConcurrentSkipListMap<String, Player> onlinePlayers = new ConcurrentSkipListMap<>();

        for (Player player : getServer().getOnlinePlayers()) {
            onlinePlayers.put(player.getName(), player);
        }

        return onlinePlayers;
    }
}
