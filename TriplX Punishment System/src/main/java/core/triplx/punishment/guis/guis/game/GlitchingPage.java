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
public class GlitchingPage implements PunishMenu {

    private final Player player;
    private final UUID target;
    private final String targetName;

    private final Inventory inventory;


    public GlitchingPage(Player staff, UUID target, String targetName) {
        inventory = Bukkit.createInventory(this, 54, ChatColor.RED.toString() + ChatColor.BOLD + "Punish >> " + ChatColor.RED + targetName);

        this.player = staff;
        this.target = target;
        this.targetName = targetName;

        Item sev1 = new Item(Material.STAINED_CLAY, 4, "&67 Day Ban");
        sev1.addLore("");
        sev1.addLore("&7Small glitch");
        sev1.addLore("&7first offense.");

        Item sev2 = new Item(Material.STAINED_CLAY, 6, "&c14 Day Ban");
        sev2.addLore("");
        sev2.addLore("&7Small glitch");
        sev2.addLore("&7multi-offense.");

        Item sev3 = new Item(Material.STAINED_CLAY, 14, "&c&l30 Day Ban");
        sev3.addLore("");
        sev3.addLore("&7Unfair advantage, big glitch.");

        inventory.setItem(GUIUtils.getSlot(3, 3), sev1.getStack());
        inventory.setItem(GUIUtils.getSlot(3, 5), sev2.getStack());
        inventory.setItem(GUIUtils.getSlot(3, 7), sev3.getStack());

        staff.openInventory(inventory);

    }



    @Override
    public boolean onClick(Player player, int slot, ClickType type) {

        String time;
        if (slot == GUIUtils.getSlot(3, 3)) {
            //sev1
            time = "7d";
        } else
        if (slot == GUIUtils.getSlot(3, 5)) {
            //sev2
            time = "14d";
        } else
        if (slot == GUIUtils.getSlot(3,7)) {
            time = "30d";
        } else {
            return false;
        }

        String command = "tempban " + targetName + " " + time + " " + "Glitch/Exploits --game --punish";
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
