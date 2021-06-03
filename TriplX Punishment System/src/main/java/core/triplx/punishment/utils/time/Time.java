package core.triplx.punishment.utils.time;

import lombok.Getter;
import triplx.core.api.chat.Color;

public class Time {

    @Getter
    private final int days, hours, minutes, seconds;

    public Time(int days, int hours, int minutes, int seconds) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public Time(int amount, TimeUnit unit) {
        if (unit == TimeUnit.DAYS) {
            days = amount;
            minutes = 0;
            hours = 0;
            seconds = 0;
        } else if (unit == TimeUnit.MINUTES) {
            minutes = amount;
            hours = 0;
            days = 0;
            seconds = 0;
        } else if (unit == TimeUnit.HOURS) {
            hours = amount;
            minutes = 0;
            seconds = 0;
            days = 0;
        } else {
            days = 0;
            hours = 0;
            minutes = 0;
            seconds = 0;
        }

    }



    public String makeString() {

        StringBuilder b = new StringBuilder("&f");
        if (days >= 1) {
            b.append(days).append("d").append(" ");
        }
        if (hours >= 1) {
            b.append(hours).append("h").append(" ");
        }
        if (minutes >= 1) {
            b.append(minutes).append("m").append(" ");
        }
        if (seconds >= 1) {
            b.append(seconds).append("s");
        }

        return Color.cc(b.toString());
    }

    public long toLong() {
        return TimeUtil.toLong(days, hours, minutes, seconds);
    }

    public long endLong() {
        return Math.addExact(toLong(), System.currentTimeMillis());
    }


}
