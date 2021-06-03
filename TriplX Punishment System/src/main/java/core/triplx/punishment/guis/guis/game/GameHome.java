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
public class GameHome implements PunishMenu {

    private final Player player;
    private final UUID target;
    private final String targetName;

    @Getter
    private final Inventory inventory;

    public GameHome(Player staff, UUID target, String targetName) {
        inventory = Bukkit.createInventory(this, 54, ChatColor.RED.toString() + ChatColor.BOLD + "Punish >> " + ChatColor.RED + targetName);

        System.out.println(inventory.getHolder()); // the inventory holder indeed exists

        this.player = staff;
        this.target = target;
        this.targetName = targetName;

        Item cheating = new Item(Material.DIAMOND_SWORD, "&c&lCheating / Hacks");
        cheating.addLore("");
        cheating.addLore("&7Using unfair modifications");
        cheating.addLore("&7to their game. (Kill Aura, Speed, etc)");

        inventory.setItem(GUIUtils.getSlot(3, 4), cheating.getStack());

        Item glitching = new Item(Material.GRASS, "&c&lGlitching / Exploits");
        glitching.addLore("");
        glitching.addLore("&7Glitching or using exploits");
        glitching.addLore("&7for an unfair advantage.");

        inventory.setItem(GUIUtils.getSlot(3, 6), glitching.getStack());

        System.out.println("Got here"); // we did get here

        staff.openInventory(inventory);
    }

    @Override
    public boolean onClick(Player player, final int slot, ClickType type) { // not here-- event doesnt work or something

        if (slot == GUIUtils.getSlot(3, 4)) {
            new HackPage(this.player, this.target, this.targetName);
        }
        if (slot == GUIUtils.getSlot(3, 6)) {
            new GlitchingPage(this.player, this.target, this.targetName);
        }

        return false;
    }

    @Override
    public void onOpen(Player player) {

    }

    @Override
    public void onClose(Player player) {

    }
}
