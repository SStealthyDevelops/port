package core.triplx.punishment.guis.guis.store;

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
public class StoreHome implements PunishMenu {

    private final Inventory inventory;

    private final Player player;
    private final UUID target;
    private final String targetName;

    public StoreHome(Player player, UUID target, String targetName) {
        inventory = Bukkit.createInventory(this, 54, ChatColor.RED.toString() + ChatColor.BOLD.toString() + "PUNISH >> " + ChatColor.RED  + targetName);

        this.player = player;
        this.target = target;
        this.targetName = targetName;

        Item fraud = new Item(Material.BANNER, 14, "&c&lFraud");
        fraud.addLore("");
        fraud.addLore("&7Buying as another player,");
        fraud.addLore("&7etc.");


        Item chargeback = new Item(Material.BANNER, 6, "&c&lForced Chargeback");
        chargeback.addLore("");
        chargeback.addLore("&7Buying a product,");
        chargeback.addLore("&7then forcing a chargeback.");


        inventory.setItem(GUIUtils.getSlot(3, 4), fraud.getStack());
        inventory.setItem(GUIUtils.getSlot(3, 6), chargeback.getStack());

        player.openInventory(inventory);
    }

    @Override
    public boolean onClick(Player player, int slot, ClickType type) {

        if (slot == GUIUtils.getSlot(3, 4)) {
            player.performCommand("tempban " + this.targetName + " 0d Fraud --store --punish --permanent");
        }
        if (slot == GUIUtils.getSlot(3, 6)) {
            player.performCommand("tempban " + this.targetName + " 0d Forced Chargeback --store --punish --permanent");
        }

        player.closeInventory();
        return true;
    }

    @Override
    public void onOpen(Player player) {

    }

    @Override
    public void onClose(Player player) {

    }
}
