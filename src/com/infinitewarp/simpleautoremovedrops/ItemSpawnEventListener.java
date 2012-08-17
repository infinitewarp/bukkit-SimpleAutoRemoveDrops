package com.infinitewarp.simpleautoremovedrops;

import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

import java.util.Collection;
import java.util.Date;

public class ItemSpawnEventListener implements Listener {
    private SimpleAutoRemoveDrops plugin;
    private boolean isRemoving = false;
    private Date lastCheckTime = new Date();

    public ItemSpawnEventListener(SimpleAutoRemoveDrops plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void handleDropsCheck(ItemSpawnEvent event) {
        Date newCheckTime = new Date();
        if (newCheckTime.getTime() > (plugin.getMillisecondsBetweenCheck() + lastCheckTime.getTime())) {
            lastCheckTime = newCheckTime;
            if (isRemoving == false) {
                World world = event.getLocation().getWorld();
                Collection<Item> items = world.getEntitiesByClass(Item.class);
                Collection<Projectile> projectiles = world.getEntitiesByClass(Projectile.class);
                int fullCount = items.size() + projectiles.size();

                if (fullCount > plugin.getClearCount()) {
                    isRemoving = true;
                    plugin.getServer().broadcastMessage("Removing all item drops and projectiles!");
                    plugin.getLogger().info(String.format("Server has reached %d of %d allowed drops.", fullCount, plugin.getClearCount()));
                    plugin.getLogger().info(String.format("Removing %d items...", items.size()));
                    for (Item item : items) {
                        item.remove();
                    }
                    plugin.getLogger().info(String.format("Removing %d projectiles...", projectiles.size()));
                    for (Projectile projectile : projectiles) {
                        projectile.remove();
                    }
                    plugin.getLogger().info("Finished removing drops.");
                    isRemoving = false;
                } else if (fullCount > plugin.getWarningCount()) {
                    plugin.getServer().broadcastMessage("Warning: approaching critical item drop and projectile count");
                }
            }
        }
    }
}
