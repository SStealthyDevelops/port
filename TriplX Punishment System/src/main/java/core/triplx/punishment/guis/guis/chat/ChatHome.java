package core.triplx.punishment.guis.guis.chat;

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
public class ChatHome implements PunishMenu {

    private final Player player;
    private final UUID target;
    private final String targetName;

    private final Inventory inventory;

    public ChatHome(Player player, UUID target, String targetName) {
        inventory = Bukkit.createInventory(this, 54, ChatColor.RED.toString() + ChatColor.BOLD + "Punish >> " + ChatColor.RED + targetName);

        this.player = player;
        this.target = target;
        this.targetName = targetName;

        Item advertisement = new Item(Material.SIGN, "&cAdvertisement");
        advertisement.addLore("");
        advertisement.addLore("&7Advertising their own");
        advertisement.addLore("&7server, website, community, etc."); // permanent mute

        Item spam = new Item(Material.DIODE, "&cSpam");
        spam.addLore("");
        spam.addLore("&7Repeating messages or");
        spam.addLore("&7pasting copypasta, etc."); // 1 hour mute

        Item toxic = new Item(Material.REDSTONE, "&cExtreme Toxic Behavior");
        toxic.addLore("");
        toxic.addLore("&7Harassment, threats, etc.");
        toxic.addLore("&7Extreme negative behavior."); // permanent ban

        Item curse = new Item(Material.PAPER, "&cBypassing curse filters"); // 1 hour mute
        curse.addLore("");
        curse.addLore("&7Bypassing the curse/word filter");

        inventory.setItem(GUIUtils.getSlot(3, 2), advertisement.getStack());
        inventory.setItem(GUIUtils.getSlot(3, 4), curse.getStack());
        inventory.setItem(GUIUtils.getSlot(3, 6), spam.getStack());
        inventory.setItem(GUIUtils.getSlot(3, 8), toxic.getStack());

        player.openInventory(inventory);
    }

    @Override
    public boolean onClick(Player player, int slot, ClickType type) {


        if (slot == GUIUtils.getSlot(3, 2)) {
            // advertisement
            player.performCommand("mute " + this.targetName + " 0d Advertising --permanent --punish");
            player.closeInventory();
            return true;
        } else
        if (slot == GUIUtils.getSlot(3, 4)) {
            player.performCommand("mute " + this.targetName + " 1h Bypassing Word Filter --punish");
            player.closeInventory();
            return true;
        } else
        if (slot == GUIUtils.getSlot(3, 6)) {
            player.performCommand("mute " + this.targetName + " 1h Spamming --punish");
            player.closeInventory();
            return true;
        } else
        if (slot == GUIUtils.getSlot(3, 8)) {
            player.performCommand("tempban " + this.targetName + " 0d Extreme Toxic Behaviour --permanent --punish --chat");
            player.closeInventory();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onOpen(Player player) {

    }

    @Override
    public void onClose(Player player) {

    }
}
