package host.plas.worldlock.events;

import host.plas.worldlock.WorldLock;
import host.plas.worldlock.utils.MessageUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MainListener implements Listener {
    @EventHandler
    public void onWorldEnter(PlayerMoveEvent event) {
        onOtherWorld(event);
    }

    @EventHandler
    public void onWorldEnter(PlayerPortalEvent event) {
        onOtherWorld(event);
    }

    @EventHandler
    public void onWorldEnter(PlayerTeleportEvent event) {
        onOtherWorld(event);
    }

    public void onOtherWorld(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Location from = event.getFrom();
        Location to = event.getTo();

        if (to == null) return;

        World fromWorld = from.getWorld();
        World toWorld = to.getWorld();

        if (fromWorld == null || toWorld == null) return;

        if (fromWorld != toWorld) {
            if (WorldLock.getMyConfig().isLockedWorld(toWorld.getName())) {
                if (player.hasPermission("worldlock.bypass." + toWorld.getName())) return;

                event.setCancelled(true);
                if (! NotificationTimer.hasNotification(toWorld.getName(), player)) {
                    event.getPlayer().sendMessage(MessageUtils.colorize("&cThis world is locked!"));
                    NotificationTimer.addNotification(toWorld.getName(), player).ifPresentOrElse(p -> {}, () -> {
                        MessageUtils.logDebug("Failed to add notification timer for player " + player.getName() + " in world " + toWorld.getName() + "!");
                    });
                }
            }
        }
    }
}
