package core.triplx.punishment.events;

import core.triplx.punishment.Core;
import core.triplx.punishment.guis.PunishMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClick implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    private void onClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof PunishMenu)) {
            event.setCancelled(false);
            return;
        }
        // need to delay it or it wont work (stupid ffs)
        Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> {
            final InventoryHolder holder = event.getInventory().getHolder();
//            System.out.println(holder.toString()); // worked for first menu, not second

            event.setCancelled(((PunishMenu) holder).onClick((Player) event.getWhoClicked(), event.getSlot(), event.getClick()));
        }, 2L);
    }

    @EventHandler private void onOpen(InventoryOpenEvent event) {
        final InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof PunishMenu) ((PunishMenu) holder).onOpen((Player) event.getPlayer());
    }

    @EventHandler private void onClose(InventoryCloseEvent event) {
        final InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof PunishMenu) ((PunishMenu) holder).onClose((Player) event.getPlayer());
    }



}
