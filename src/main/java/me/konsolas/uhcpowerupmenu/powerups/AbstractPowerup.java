package me.konsolas.uhcpowerupmenu.powerups;

import me.konsolas.uhcpowerupmenu.UHCPowerupMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPowerup implements Powerup {
    protected final UHCPowerupMenu plugin;
    private final ItemStack icon;
    private final int cost;

    public AbstractPowerup(UHCPowerupMenu plugin, Material type, boolean glow, int cost, String title, String... lore) {
        this.plugin = plugin;
        this.icon = new ItemStack(type);
        this.cost = cost;

        ItemMeta meta = Objects.requireNonNull(icon.getItemMeta());

        if (glow) {
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        meta.setDisplayName(ChatColor.GOLD + title);

        List<String> loreLines = new ArrayList<>(Arrays.asList(lore));
        if (cost > 0) {
            loreLines.add(ChatColor.RED + "Costs " + cost + " heart" + (cost != 1 ? "s" : ""));
        } else if (cost == 0) {
            loreLines.add(ChatColor.YELLOW + "No health cost");
        }
        meta.setLore(loreLines);

        icon.setItemMeta(meta);
    }

    @Override
    public ItemStack getIcon() {
        return icon;
    }

    @Override
    public void apply(Player target) {
        // Cost is in full hearts, damage is in half hearts
        target.damage(cost * 2);
    }

    protected static void giveItems(Player player, ItemStack... items) {
        HashMap<Integer, ItemStack> unstored = player.getInventory().addItem(items);

        for (ItemStack stack : unstored.values())
            player.getWorld().dropItemNaturally(player.getLocation(), stack);
    }
}
