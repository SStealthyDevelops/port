package core.triplx.punishment.constructors;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Warning {

    private final UUID uuid;
    private final String category;
    private final String reason;

    public Warning(UUID uuid, String category, String reason) {
        this.uuid = uuid;
        this.category = category;
        this.reason = reason;
    }
}
