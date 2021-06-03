package triplx.core.api.data.server;

import lombok.Getter;

@Getter
public enum ServerType {

    MAIN_HUB("main-hub", false, new String[]{"main", "main_hub", "hub", "mainhub"}),
    SKYDOMINATION_HUB("skydomination-hubs", false, new String[]{"skydom", "skydomination", "sky_domination", "sd"}),
    SKYDOMINATION_SERVER("skydomination-servers", true, new String[]{"skydom", "skydomination", "sky_domination", "sd"});



    private final String collection;
    private final boolean gameServer;
    private final String[] aliases;
    ServerType(String collection, boolean gameServer, String[] aliases) {
        this.collection = collection;
        this.gameServer = gameServer;
        this.aliases = aliases;
    }

    public static ServerType getServerType(String alias, boolean gameServer) {
        for (ServerType type : values()) {
            if (type.gameServer != gameServer) continue;
            for (String s : type.aliases) {
                if (s.equalsIgnoreCase(alias)) return type;
            }
        }
        return null;
    }


}
