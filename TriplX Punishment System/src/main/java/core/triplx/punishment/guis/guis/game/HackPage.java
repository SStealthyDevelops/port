package core.triplx.punishment.guis.guis.game;

import core.triplx.punishment.guis.GUIUtils;
import core.triplx.punishment.guis.Item;
import core.triplx.punishment.guis.PunishMenu;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

@Getter
public class HackPage implements PunishMenu {

    private final Inventory inventory;


    private final Player player;
    private final UUID target;
    private final String targetName;

    public HackPage(Player staff, UUID target, String targetName) {
        inventory = Bukkit.createInventory(this, 54, ChatColor.RED.toString() + ChatColor.BOLD + "Punish >>" + ChatColor.RED + targetName);
        System.out.println("Inventory created"); // text size too big if we use Color.cc

        this.player = staff;
        this.target = target;
        this.targetName = targetName;

        Item sev1 = new Item(Material.STAINED_CLAY, 4, "&67 Day Ban");
        sev1.addLore("");
        sev1.addLore("&7Not cheating very much,");
        sev1.addLore("&7first offense.");

        Item sev2 = new Item(Material.STAINED_CLAY, 6, "&c14 Day Ban");
        sev2.addLore("");
        sev2.addLore("&7Not cheating very much,");
        sev2.addLore("&7second offense.");

        Item sev3 = new Item(Material.STAINED_CLAY, 14, "&c&l30 Day Ban");
        sev3.addLore("");
        sev3.addLore("&7Cheating a lot,");
        sev3.addLore("&7first offense.");

        Item sev4 = new Item(Material.STAINED_CLAY, 15, "&4&l90 Day Ban");
        sev4.addLore("");
        sev4.addLore("&cCheating a lot:");
        sev4.addLore("&c(Multiple offenses)");


        inventory.setItem(GUIUtils.getSlot(3, 2), sev1.getStack());
        inventory.setItem(GUIUtils.getSlot(3, 4), sev2.getStack());
        inventory.setItem(GUIUtils.getSlot(3, 6), sev3.getStack());
        inventory.setItem(GUIUtils.getSlot(3, 8), sev4.getStack());


        staff.openInventory(inventory);
    }

    @Override
    public boolean onClick(Player player, final int slot, ClickType type) {


        String time;
        if (slot == GUIUtils.getSlot(3, 2)) {
            time = "7d";
        } else
        if (slot == GUIUtils.getSlot(3, 4)) {
            time = "14d";
        } else
        if (slot == GUIUtils.getSlot(3, 6)) {
            time = "30d";
        } else
        if (slot == GUIUtils.getSlot(3, 8)) {
            time = "90d";
        } else {
            return false;
        }

        String command = "tempban " + targetName + " " + time + " " + "Hack Client --game --punish";
        player.performCommand(command);
        player.closeInventory();
        return false;
    }

    @Override
    public void onOpen(Player player) {

    }

    @Override
    public void onClose(Player player) {

    }
}
