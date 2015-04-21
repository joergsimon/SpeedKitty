package android.upipc.knowcenter.at.speedkitty.utils;

import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 * Created by j_simon on 21/04/15.
 */
public class TimeUtils {

    public static int weekPassedSinceTime(long timestamp) {
        long realTimestamp = timestamp / 1000000L; // the sensor TS are in nanoseconds
        DateTime now = DateTime.now();
        DateTime past = new DateTime(realTimestamp);
        Period interval = new Period(past, now);
        return interval.getWeeks();
    }
}
