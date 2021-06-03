package core.triplx.punishment.constructors.mute;

import core.triplx.punishment.utils.IDUtil;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Mute {

    private final UUID uuid;
    private final String reason;
    private final long endTime;
    private final int id;
    private final boolean permanent;

    public Mute(UUID uuid, String reason, long endTime, int id) {
        this.uuid = uuid;
        this.reason = reason;
        this.endTime = endTime;
        this.id = id;
        this.permanent = (endTime == -1);
    }

    public Mute(UUID uuid, String reason, long endTime) {
        this.uuid = uuid;
        this.reason = reason;
        this.endTime = endTime;
        id = IDUtil.randomID();
        this.permanent = (endTime == -1);
    }
}
