package triplx.core.api.utils.inventory;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import triplx.core.api.chat.Color;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Item {

    private Material material;
    private int shortData;

    private ItemMeta meta;
    private ItemStack stack;

    public Item(Material material, int shortData, String displayName) {
        ItemStack stack = new ItemStack(material, 1, (short) shortData);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(Color.cc(displayName));
        stack.setItemMeta(meta);
        this.meta = meta;
        this.stack = stack;
    }

    public Item(Material material, String displayName) {
        ItemStack stack = new ItemStack(material, 1);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(Color.cc(displayName));
        stack.setItemMeta(meta);
        this.meta = meta;
        this.stack = stack;
    }

    public Item setName(String newName) {
        meta.setDisplayName(Color.cc(newName));
        stack.setItemMeta(meta);
        return this;
    }

    public Item addLore(String lore) {
        List<String> loreList = meta.getLore();
        if (loreList == null) loreList = new ArrayList<>();
        loreList.add(Color.cc(lore));
        meta.setLore(loreList);
        stack.setItemMeta(meta);
        return this;
    }


}
