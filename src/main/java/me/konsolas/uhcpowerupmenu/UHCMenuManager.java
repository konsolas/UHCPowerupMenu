package me.konsolas.uhcpowerupmenu;

import me.konsolas.uhcpowerupmenu.powerups.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.NumberConversions;

import java.util.ArrayList;
import java.util.List;

public class UHCMenuManager implements Listener {
    private final UHCPowerupMenu plugin;
    private final Inventory inventory;
    private final List<Powerup> powerups = new ArrayList<>();

    private void registerPowerups() {
        powerups.add(new SpeedyPickPowerup(plugin));
        powerups.add(new BuffsPowerup(plugin));
        powerups.add(new LifestealPowerup(plugin));
        powerups.add(new MaxHealthPowerup(plugin));
        powerups.add(new HastePowerup(plugin));
        powerups.add(new InvisPowerup(plugin));
        powerups.add(new BowPowerup(plugin));
    }

    public UHCMenuManager(UHCPowerupMenu plugin) {
        this.plugin = plugin;

        registerPowerups();

        // Round our inventory size up to the next multiple of 9
        int inventorySize = powerups.size();
        int rows = NumberConversions.ceil(inventorySize / 9.0);
        this.inventory = Bukkit.createInventory(null, rows * 9, "Menu");

        for (int i = 0; i < inventorySize; i++) {
            inventory.setItem(i, powerups.get(i).getIcon());
        }
    }

    public void show(Player player) {
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == inventory) {
            event.setCancelled(true);
            int slot = event.getSlot();
            if (slot < powerups.size()) powerups.get(slot).apply((Player) event.getWhoClicked());
        }
    }
}
