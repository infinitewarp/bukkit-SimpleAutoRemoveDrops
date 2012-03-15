package com.infinitewarp.simpleautoremovedrops;

import java.util.Collection;

import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

public class ItemSpawnEventListener implements Listener {
    private SimpleAutoRemoveDrops plugin;

    public ItemSpawnEventListener(SimpleAutoRemoveDrops plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void handleDropsCheck(ItemSpawnEvent event) {
        World world = event.getLocation().getWorld();
        Collection<Item> items = world.getEntitiesByClass(Item.class);
        Collection<Projectile> projectiles = world.getEntitiesByClass(Projectile.class);
        int fullCount = items.size() + projectiles.size();
        if (fullCount > plugin.getClearCount()) {
            plugin.getServer().broadcastMessage("Removing all item drops and projectiles!");
            for (Item item : items) {
                item.remove();
            }
            for (Projectile projectile : projectiles) {
                projectile.remove();
            }
        } else if (fullCount > plugin.getWarningCount()) {
            plugin.getServer().broadcastMessage("Warning: approaching critical item drop and projectile count");
        }
    }
}
