package core.triplx.punishment.guis;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class GUIUtils {

    public static int getSlot(int row, int column) {
        if (row > 6) {
            System.out.println("ROW CANNOT BE GREATER THAN 6");
            return 1;
        }
        if (column < 1 || column > 9) {
            System.out.println("COLUMN MUST BE BETWEEN 1 AND 9");
            return 1;
        }
        int i = 0;
        i += (row * 9) - 9;
        i += column - 1;
        return i;
    }

    public static ItemStack getHead(String name) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(name);
        skull.setItemMeta(skullMeta);
        return skull;
    }


}

