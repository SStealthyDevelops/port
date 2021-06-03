package core.triplx.punishment.constructors.ban;

import core.triplx.punishment.utils.IDUtil;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Ban {

    private final UUID uuid;
    private final String reason;
    private final BanReason type;
    private final long endTime;
    private final int id;
    private final boolean permanent;

    public Ban(UUID uuid, String reason, BanReason type, long endTime) {
        this.uuid = uuid;
        this.reason = reason;
        this.type = type;
        this.endTime = endTime;
        permanent = (endTime == -1);
        id = IDUtil.randomID();
    }

    public Ban(UUID uuid, String reason, BanReason type, long endTime, int id) {
        this.uuid = uuid;
        this.reason = reason;
        this.type = type;
        this.endTime = endTime;
        permanent = (endTime == -1);
        this.id = id;
    }



}
