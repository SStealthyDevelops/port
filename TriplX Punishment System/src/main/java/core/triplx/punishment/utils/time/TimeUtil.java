package core.triplx.punishment.utils.time;

public class TimeUtil {

    public static Time toTime(long time) {
        int days = 0,hours = 0,minutes = 0,seconds = 0;

        seconds += time / 1000;

        while (seconds >= 60) {
            minutes += 1;
            seconds -= 60;
        }

        while (minutes >= 60) {
            hours += 1;
            minutes -=60;
        }

        while (hours >= 24) {
            days += 1;
            hours -= 24;
        }


        return new Time(days, hours, minutes, seconds);
    }

    public static long toLong(int days, int hours, int minutes, int seconds) {
        long l = 0;

        long s = 1000;
        long m = s * 60;
        long h = m * 60;
        long d = h * 24;
//
//        System.out.println("d" + d);
//        System.out.println("m" + m);
//        System.out.println("s" + s);
//        System.out.println("h" + h);


//        System.out.println(l + " l1");

        l += (days * d) + (hours * h) + (minutes * m) + (seconds * s);
//        System.out.println(l + " l2");
        return l;
    }

    public static Time toTime(String s) {
        TimeUnit unit = TimeUnit.MINUTES;
        int amount = 0;
        try {
            if (s.toLowerCase().contains("d")) {
                s = s.toLowerCase().replace("d", "");
                unit = TimeUnit.DAYS;
            } else
            if (s.toLowerCase().contains("h")) {
                s = s.toLowerCase().replace("h", "");
                unit = TimeUnit.HOURS;
            } else
            if (s.toLowerCase().contains("m")) {
                s = s.toLowerCase().replace("m", "");
                unit = TimeUnit.MINUTES;
            }

            amount = Integer.parseInt(s);
        } catch (Exception e) { // ignore
        }



        return new Time(amount, unit);
    }

}
