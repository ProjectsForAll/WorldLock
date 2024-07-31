package host.plas.worldlock.configs;

import host.plas.worldlock.WorldLock;
import lombok.Getter;
import lombok.Setter;
import tv.quaint.storage.resources.flat.simple.SimpleConfiguration;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListSet;

@Getter @Setter
public class MyConfig extends SimpleConfiguration {
    @Getter @Setter
    private static ConcurrentSkipListSet<String> loadedLockedWorlds;

    public MyConfig() {
        super("config.yml", WorldLock.getInstance(), false);
    }

    @Override
    public void init() {
        // Set defaults
        loadLockedWorlds();
    }

    public void loadLockedWorlds() {
        loadedLockedWorlds = getLockedWorlds();
    }

    public void reloadFromConfig() {
        loadLockedWorlds();
    }

    public void saveToConfig() {
        setLockedWorlds(getLoadedLockedWorlds());
    }

    /**
     * A set of world names that are locked.
     * @return A set of world names that are locked.
     */
    public ConcurrentSkipListSet<String> getLockedWorlds() {
        reloadResource();

        return new ConcurrentSkipListSet<>(getOrSetDefault("locked-worlds", new ArrayList<>()));
    }

    public void addLockedWorld(String world) {
        loadedLockedWorlds.add(world);
        saveToConfig();
    }

    public void removeLockedWorld(String world) {
        loadedLockedWorlds.remove(world);
        saveToConfig();
    }

    public boolean isLockedWorld(String world) {
        return getLoadedLockedWorlds().contains(world);
    }

    public void clearLockedWorlds() {
        loadedLockedWorlds.clear();
        saveToConfig();
    }

    public void setLockedWorlds(ConcurrentSkipListSet<String> lockedWorlds) {
        write("locked-worlds", new ArrayList<>(lockedWorlds));
    }
}
