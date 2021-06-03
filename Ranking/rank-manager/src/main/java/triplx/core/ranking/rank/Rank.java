package triplx.core.ranking.rank;

import lombok.Getter;

public enum Rank {

    DEFAULT("&7", "default", false, 1),
    VIP("&7[&dVIP&7] &d", "vip", false, 2),
    HERO("&7[&bHERO&7] &b", "hero", false, 3),
    LEGEND("&7[&aLEGEND&7] &a", "legend", false, 4),
    CHAMPION("&7[&6CHAMPION&7] &6", "champion", false, 5),
    YOUTUBE("&7[&cYOU&fTUBE&7] &f", "youtube", false, 6),
    PARTNER("&7[&9PARTNER&7] &9", "partner", false, 7),
    RETIRED("&7[&fRETIRED&7] &f", "retired", false, 8),
    HELPER("&7[&3HELPER&7] &3", "helper", true, 9),
    MODERATOR("&7[&5MOD&7] &5", "moderator", true, 10),
    BUILDER("&7[&3BUILDER&7] &3", "builder", true, 11),
    ADMIN("&7[&cADMIN&7] &c", "admin", true, 12),
    MANAGER("&7[&cMANAGER&7] &c", "manager", true, 13),
    DEVELOPER("&7[&eDEVELOPER&7] &e", "developer", true, 14),
    OWNER("&7[&cOWNER&7] &c", "owner", true, 15);

    @Getter
    private final String name;
    @Getter
    private final String prefix;
    @Getter
    private final Integer rankID;
    @Getter
    private final boolean staff;

    private Rank(String prefix, String name, Boolean isStaff, Integer rankID) {
        this.prefix = prefix;
        this.rankID = rankID;
        this.name = name;
        this.staff = isStaff;
    }

    public static Rank getRank(int id) {
        for (Rank rank : Rank.values()) {
            if (rank.getRankID() == id) {
                return rank;
            }
        }
        return null;
    }

    public static Rank getRank(String str) {
        for (Rank rank : Rank.values()) {
            if (rank.getName().equalsIgnoreCase(str)){
                return rank;
            }
        }
        return null;
    }

    public boolean hasPermission(int minimumID) {
        return this.getRankID() >= minimumID;
    }

    public boolean hasPermission(Rank rankMinimum) {
        return this.getRankID() >= rankMinimum.getRankID();
    }



}
