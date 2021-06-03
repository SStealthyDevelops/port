package triplx.core.api.chat;

import org.bukkit.ChatColor;

public class Color {

    public static String cc(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
