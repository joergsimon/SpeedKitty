package android.upipc.knowcenter.at.speedkitty.sensing.accelerometer;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by j_simon on 23/04/15.
 */
public class AvarageSpeedDetector implements LinearAccelerationHandler {
    LinearAccelerationListener listener;
    List<HistoryRecord> history;
    HistoryComparator comparator;

    public AvarageSpeedDetector(Context ctx) {
        listener = new LinearAccelerationListener(ctx, this);
        history = new ArrayList<>();
        comparator = new HistoryComparator();
    }

    public void startComputingSpeed() {
        listener.startSensing();
    }

    public void stopComputingSpeed() {
        listener.startSensing();
    }

    public float getAvarageSpeeed(long start, long end) {
        float total_m_s = 0;
        Collections.sort(history, comparator);
        for (int i = 1; i < history.size(); i++) {
            HistoryRecord lastRec = history.get(i-1);
            HistoryRecord rec = history.get(i);
            if (rec.timestamp > start && rec.timestamp < end) {
                total_m_s = total_m_s + rec.acceleration*(float)((double)(rec.timestamp - lastRec.timestamp)/1000000000.0);
            }
        }
        float kmh = total_m_s*3.6f;
        return kmh;
    }

    public void purgeHistory() {
        history.clear();
    }

    @Override
    public void accelertionChanges(long timestamp, float x, float y, float z) {
        float acceleration = (float)Math.sqrt(x*x+y*y+z*z);
        HistoryRecord record = new HistoryRecord(timestamp, acceleration);
        history.add(record);
        Log.d("SENSING", "chaned acceleration to "+acceleration+" at time: " + timestamp);
    }

    private class HistoryRecord {
        long timestamp;
        float acceleration;

        public HistoryRecord(long timestamp, float acceleration) {
            this.timestamp = timestamp;
            this.acceleration = acceleration;
        }
    }

    private class HistoryComparator implements Comparator<HistoryRecord> {
        @Override
        public int compare(HistoryRecord lhs, HistoryRecord rhs) {
            Long time1 = lhs.timestamp;
            Long time2 = rhs.timestamp;
            return time1.compareTo(time2);
        }
    }
}
