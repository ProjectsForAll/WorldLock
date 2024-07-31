package host.plas.worldlock;

import host.plas.bou.BetterPlugin;
import host.plas.worldlock.commands.WorldsCMD;
import host.plas.worldlock.configs.MyConfig;
import host.plas.worldlock.events.MainListener;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentSkipListMap;

@Getter
@Setter
public final class WorldLock extends BetterPlugin {
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
    }

    @Override
    public void onBaseDisable() {
        // Plugin shutdown logic
    }
}
