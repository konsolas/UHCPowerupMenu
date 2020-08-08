package me.konsolas.uhcpowerupmenu.playerdeatheffects;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

public class ReduceRedHearts implements Listener {

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent playerDeathEvent) {

        if (playerDeathEvent.getEntity() instanceof Player) {
            Player target = playerDeathEvent.getEntity();
            AttributeInstance maxHealthAttr = Objects.requireNonNull(target.getAttribute(Attribute.GENERIC_MAX_HEALTH));
            if (maxHealthAttr.getValue() > 2) {
                maxHealthAttr.setBaseValue(maxHealthAttr.getBaseValue() - 2);
                target.setAbsorptionAmount(target.getAbsorptionAmount() + 2);
            }
        }
    }
}
