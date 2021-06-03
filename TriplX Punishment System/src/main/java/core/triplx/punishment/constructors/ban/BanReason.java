package core.triplx.punishment.constructors.ban;

import lombok.Getter;

public enum BanReason {

    ANTICHEAT("Spartan Detection"), STORE_OFFENSE("Store Offense"), CHAT("Chat Offense"), GAME_OFFENSE("Game Offense"), OTHER("Other Offense");


    @Getter
    private String name;
    BanReason(String name) {
        this.name = name;
    }

    public static BanReason fromID(String string) {
        return ANTICHEAT;//TODO
    }


}
