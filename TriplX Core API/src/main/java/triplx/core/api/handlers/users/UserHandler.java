package triplx.core.api.handlers.users;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserHandler {

    @Getter
    @Setter
    private static UserHandler instance;

    @Getter
    private List<User> users;

    public UserHandler() {
        users = new ArrayList<>();
    }

    public void addUser(User e) {
        users.add(e);
    }

    public void removeUser(User e) {
        users.remove(e);
    }

    public User getUser(Player player) {
        for (User user : users) {
            if (user.getPlayer() == player) {
                return user;
            }
        }
        return null;
    }

    public User getUser(UUID player) {
        for (User user : users) {
            if (user.getUuid() == player) {
                return user;
            }
        }
        return null;
    }

    public boolean userStored(UUID uuid) {
        for (User user : users) {
            if (user.getUuid() == uuid) {
                return true;
            }
        }
        return false;
    }

    public void flush() {
        users = new ArrayList<>();
    }

}
