package host.plas.worldlock.commands;

import host.plas.worldlock.WorldLock;
import io.streamlined.bukkit.commands.CommandContext;
import io.streamlined.bukkit.commands.SimplifiedCommand;
import org.bukkit.Bukkit;
import tv.quaint.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

public class WorldsCMD extends SimplifiedCommand {
    public WorldsCMD() {
        super("manageworlds", WorldLock.getInstance());
    }

    @Override
    public boolean command(CommandContext commandContext) {
        if (commandContext.getArgs().isEmpty()) {
            commandContext.sendMessage("&cUsage: /manageworlds <add/remove/list>");
            return true;
        }

        String action = commandContext.getStringArg(0);

        switch (action) {
            case "add":
                if (commandContext.getArgs().size() < 2) {
                    commandContext.sendMessage("&cUsage: /manageworlds add <world>");
                    return true;
                }

                ConcurrentSkipListSet<String> aSelection = new ConcurrentSkipListSet<>();

                commandContext.getArgs().forEach(arg -> {
                    if (arg.getIndex() < 1) return;

                    aSelection.add(arg.getContent());
                });

                aSelection.forEach(world -> {
                    if (WorldLock.getMyConfig().isLockedWorld(world)) {
                        commandContext.sendMessage("&cWorld &f" + world + " &cis already locked.");
                        return;
                    }

                    WorldLock.getMyConfig().addLockedWorld(world);
                    commandContext.sendMessage("&eAdded &f" + world + " &eto the locked worlds list.");
                });
                return true;
            case "remove":
                if (commandContext.getArgs().size() < 2) {
                    commandContext.sendMessage("&cUsage: /manageworlds remove <world>");
                    return true;
                }

                ConcurrentSkipListSet<String> rSelection = new ConcurrentSkipListSet<>();

                commandContext.getArgs().forEach(arg -> {
                    if (arg.getIndex() < 1) return;

                    rSelection.add(arg.getContent());
                });

                rSelection.forEach(world -> {
                    if (!WorldLock.getMyConfig().isLockedWorld(world)) {
                        commandContext.sendMessage("&cWorld &f" + world + " &cis not locked.");
                        return;
                    }

                    WorldLock.getMyConfig().removeLockedWorld(world);
                    commandContext.sendMessage("&eRemoved &f" + world + " &efrom the locked worlds list.");
                });
                return true;
            case "list":
                List<String> lockedWorlds = new ArrayList<>(WorldLock.getMyConfig().getLockedWorlds());
                if (lockedWorlds.isEmpty()) {
                    commandContext.sendMessage("&cThere are no locked worlds.");
                    return true;
                }

                commandContext.sendMessage("&eLocked worlds:");
                commandContext.sendMessage(String.join("&8, &f", lockedWorlds));
                return true;
            default:
                commandContext.sendMessage("&cUsage: /manageworlds <add/remove/list>");
                return true;
        }
    }

    @Override
    public ConcurrentSkipListSet<String> tabComplete(CommandContext commandContext) {
        ConcurrentSkipListSet<String> tab = new ConcurrentSkipListSet<>();

        if (commandContext.getArgs().size() == 1) {
            tab.add("add");
            tab.add("remove");
            tab.add("list");
        } else if (commandContext.getArgs().size() == 2 && (commandContext.getStringArg(0).equals("add"))) {
            tab.addAll(Bukkit.getServer().getWorlds().stream().map(world -> world.getName().toLowerCase()).collect(Collectors.toList()));
        } else if (commandContext.getArgs().size() == 2 && (commandContext.getStringArg(0).equals("remove"))) {
            tab.addAll(WorldLock.getMyConfig().getLockedWorlds().stream().map(String::toLowerCase).collect(Collectors.toList()));
        }

        return tab;
    }
}
