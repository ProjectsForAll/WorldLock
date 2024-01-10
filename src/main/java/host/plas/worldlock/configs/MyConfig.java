package host.plas.worldlock.configs;

import host.plas.worldlock.WorldLock;
import lombok.Getter;
import lombok.Setter;
import tv.quaint.storage.resources.flat.simple.SimpleConfiguration;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListSet;

@Getter @Setter
public class MyConfig extends SimpleConfiguration {
    public MyConfig() {
        super("config.yml", WorldLock.getInstance(), false);
    }

    @Override
    public void init() {
        // Set defaults
        getLockedWorlds();
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
        ConcurrentSkipListSet<String> lockedWorlds = getLockedWorlds();
        lockedWorlds.add(world);
        write("locked-worlds", new ArrayList<>(lockedWorlds));
    }

    public void removeLockedWorld(String world) {
        ConcurrentSkipListSet<String> lockedWorlds = getLockedWorlds();
        lockedWorlds.remove(world);
        write("locked-worlds", new ArrayList<>(lockedWorlds));
    }

    public boolean isLockedWorld(String world) {
        return getLockedWorlds().contains(world);
    }

    public void setLockedWorlds(ConcurrentSkipListSet<String> lockedWorlds) {
        write("locked-worlds", new ArrayList<>(lockedWorlds));
    }

    public void clearLockedWorlds() {
        write("locked-worlds", new ArrayList<>());
    }
}
