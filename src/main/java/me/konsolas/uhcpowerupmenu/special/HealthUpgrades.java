package me.konsolas.uhcpowerupmenu.special;

import me.konsolas.uhcpowerupmenu.UHCPowerupMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Objects;

public class HealthUpgrades implements Listener {
    private static final double HEALTH_CHANGE = 4.0;

    private final NamespacedKey healthUpgradeKey;

    public HealthUpgrades(UHCPowerupMenu plugin) {
        healthUpgradeKey = new NamespacedKey(plugin, "health_upgrade");

        registerUpgrades(plugin);
    }

    private void registerUpgrades(UHCPowerupMenu plugin) {
        plugin.getServer().addRecipe(
                new ShapedRecipe(new NamespacedKey(plugin, "health_upgrade_1"), createUpgradeItem(20.0))
                        .shape(" G ", "GDG", " G ")
                        .setIngredient('D', Material.DIAMOND)
                        .setIngredient('G', Material.GOLDEN_APPLE));

        plugin.getServer().addRecipe(
                new ShapedRecipe(new NamespacedKey(plugin, "health_upgrade_2"), createUpgradeItem(24.0))
                        .shape("DGD", "GDG", "DGD")
                        .setIngredient('D', Material.DIAMOND)
                        .setIngredient('G', Material.GOLDEN_APPLE));

        plugin.getServer().addRecipe(
                new ShapedRecipe(new NamespacedKey(plugin, "health_upgrade_3"), createUpgradeItem(28.0))
                        .shape("EGB", "GEG", "BGE")
                        .setIngredient('B', Material.DIAMOND_BLOCK)
                        .setIngredient('G', Material.GOLDEN_APPLE)
                        .setIngredient('E', Material.ENDER_EYE));

        plugin.getServer().addRecipe(
                new ShapedRecipe(new NamespacedKey(plugin, "health_upgrade_4"), createUpgradeItem(32.0))
                        .shape("EGB", "GNG", "BGE")
                        .setIngredient('B', Material.DIAMOND_BLOCK)
                        .setIngredient('G', Material.GOLDEN_APPLE)
                        .setIngredient('E', Material.ENDER_EYE)
                        .setIngredient('N', Material.NETHER_STAR));

        plugin.getServer().addRecipe(
                new ShapedRecipe(new NamespacedKey(plugin, "health_upgrade_5"), createUpgradeItem(36.0))
                        .shape("NGE", "GRG", "EGN")
                        .setIngredient('R', Material.ENCHANTED_GOLDEN_APPLE)
                        .setIngredient('G', Material.GOLDEN_APPLE)
                        .setIngredient('E', Material.ENDER_EYE)
                        .setIngredient('N', Material.NETHER_STAR));
    }

    private ItemStack createUpgradeItem(double startHealth) {
        ItemStack item = new ItemStack(Material.GHAST_TEAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Health upgrade "
                + (int) (startHealth / 2) + " -> " + (int) ((startHealth + HEALTH_CHANGE) / 2));
        meta.addEnchant(Enchantment.DURABILITY, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setLore(Arrays.asList(
                "Right click air to increase max health by " + (int) (HEALTH_CHANGE / 2) + " hearts",
                "Can only be used if your maximum health is " + (int) (startHealth / 2) + " hearts"
        ));
        meta.getPersistentDataContainer().set(healthUpgradeKey, PersistentDataType.DOUBLE, startHealth);
        item.setItemMeta(meta);

        return item;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null || event.getItem().getItemMeta() == null) return;
        if (event.getAction() != Action.RIGHT_CLICK_AIR) return;

        PersistentDataContainer data = event.getItem().getItemMeta().getPersistentDataContainer();
        if (data.has(healthUpgradeKey, PersistentDataType.DOUBLE)) {
            double healthFrom = Objects.requireNonNull(data.get(healthUpgradeKey, PersistentDataType.DOUBLE));

            AttributeInstance maxHealth = event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
            assert maxHealth != null;
            if (maxHealth.getBaseValue() != healthFrom) {
                event.getPlayer().sendMessage("You must have a max health of "
                        + (int) (healthFrom / 2) + " hearts to use this upgrade");
            } else {
                maxHealth.setBaseValue(healthFrom + HEALTH_CHANGE);
                event.getPlayer().sendMessage("Your max health was upgraded to "
                        + (int) (maxHealth.getBaseValue() / 2) + " hearts");

                if (event.getHand() == EquipmentSlot.HAND) {
                    event.getPlayer().getInventory().getItemInMainHand().setAmount(
                            event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                } else if (event.getHand() == EquipmentSlot.OFF_HAND) {
                    event.getPlayer().getInventory().getItemInOffHand().setAmount(
                            event.getPlayer().getInventory().getItemInOffHand().getAmount() - 1);
                } else throw new AssertionError();
            }
        }
    }
}
