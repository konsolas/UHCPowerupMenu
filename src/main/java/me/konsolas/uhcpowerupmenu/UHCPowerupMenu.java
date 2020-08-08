package me.konsolas.uhcpowerupmenu;

import me.konsolas.uhcpowerupmenu.playerdeatheffects.ReduceRedHearts;
import me.konsolas.uhcpowerupmenu.special.HealthBow;
import me.konsolas.uhcpowerupmenu.special.LifestealSword;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class UHCPowerupMenu extends JavaPlugin {
    private UHCMenuManager uhcMenuManager;
    private LifestealSword lifestealSword;
    private HealthBow healthBow;

    @Override
    public void onEnable() {
        this.uhcMenuManager = new UHCMenuManager(this);
        this.lifestealSword = new LifestealSword(this);
        this.healthBow = new HealthBow(this);

        getServer().getPluginManager().registerEvents(uhcMenuManager, this);
        getServer().getPluginManager().registerEvents(lifestealSword, this);
        getServer().getPluginManager().registerEvents(new ReduceRedHearts(), this);
        getServer().getPluginManager().registerEvents(healthBow, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            uhcMenuManager.show(((Player) sender));
            return true;
        } else {
            sender.sendMessage("Cannot show menu to console");
            return false;
        }
    }

    public UHCMenuManager getUhcMenuManager() {
        return uhcMenuManager;
    }

    public LifestealSword getLifestealSword() {
        return lifestealSword;
    }
    public HealthBow getHealthBow() { return healthBow; }
}
