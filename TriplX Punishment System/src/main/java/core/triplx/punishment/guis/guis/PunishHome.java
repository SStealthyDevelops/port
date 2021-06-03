package core.triplx.punishment.guis.guis;

import core.triplx.punishment.guis.GUIUtils;
import core.triplx.punishment.guis.Item;
import core.triplx.punishment.guis.PunishMenu;
import core.triplx.punishment.guis.guis.chat.ChatHome;
import core.triplx.punishment.guis.guis.game.GameHome;
import core.triplx.punishment.guis.guis.store.StoreHome;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import triplx.core.api.chat.Color;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;

import java.util.UUID;

@Getter
public class PunishHome implements PunishMenu {

    private final Player player;
    private final UUID target;
    private final String targetName;

    private final Inventory inventory;

    public PunishHome(Player staff, UUID target, String targetName) {
        inventory = Bukkit.createInventory(this, 54, Color.cc("&c&lPunish >> &c" + targetName));

        this.player = staff;
        this.target = target;
        this.targetName = targetName;

        Rank rank = RankingManager.getRank(staff);

        // store, game, chat, other
        // only admins can use ~store~

        Item store = new Item(Material.GOLD_INGOT, "&c&lStore Offense");
        if (!rank.hasPermission(Rank.ADMIN)) {
            store.addLore("");
            store.addLore(Color.cc("&dYou must be an"));
            store.addLore(Color.cc("&dadmin to punish for a "));
            store.addLore(Color.cc("&dStore offense. Please contact one!"));
        } else {
            store.addLore("");
            store.addLore(Color.cc("&7Chargeback, fraud, etc."));
            store.addLore(Color.cc("&7Ban will automatically be"));
            store.addLore(Color.cc("&7&npermanent."));
        }

        inventory.setItem(GUIUtils.getSlot(3, 7), store.getStack());

        Item game = new Item(Material.DIAMOND_SWORD, "&c&lGame Offense");
        if (!rank.hasPermission(Rank.MODERATOR)) {//helper cant ban
            game.addLore("");
            game.addLore("&dYou must be a");
            game.addLore("&dmoderator to ban");
            game.addLore("&da player.");
        } else {
            game.addLore("");
            game.addLore("&7Using hacks, exploits,");
            game.addLore("&7glitches, etc.");
        }

        inventory.setItem(GUIUtils.getSlot(3, 5), game.getStack());

        Item chat = new Item(Material.PAPER, "&c&lChat Offense");
        chat.addLore("");
        chat.addLore("&7Bypassing curse filters, spamming");
        chat.addLore("&7racism, harassment, etc.");

        inventory.setItem(GUIUtils.getSlot(3, 3), chat.getStack());



        staff.openInventory(inventory);
    }


    @Override
    public boolean onClick(Player player, final int slot, ClickType type) {

        if (slot == GUIUtils.getSlot(3, 5)) {
            new GameHome(player, target, targetName);
        }
        if (slot == GUIUtils.getSlot(3, 3)) {
            new ChatHome(player, target, targetName);
        }
        if (slot == GUIUtils.getSlot(3, 7)) {
            new StoreHome(player, target, targetName);
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
