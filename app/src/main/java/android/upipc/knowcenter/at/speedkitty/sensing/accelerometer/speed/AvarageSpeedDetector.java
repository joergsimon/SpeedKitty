package android.upipc.knowcenter.at.speedkitty.sensing.accelerometer.speed;

import android.content.Context;
import android.upipc.knowcenter.at.speedkitty.sensing.accelerometer.HighFrequencyBatchedLinearAccelerationListener;
import android.upipc.knowcenter.at.speedkitty.sensing.accelerometer.LinearAccelerationHandler;
import android.upipc.knowcenter.at.speedkitty.sensing.accelerometer.LinearAccelerationListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by j_simon on 22/04/15.
 */
public class AvarageSpeedDetector implements LinearAccelerationHandler {

    List<HistoryRecord> localHistory;
    LinearAccelerationListener listener;
    HistoryTimeComparator comparator;

    public AvarageSpeedDetector(Context ctx) {
        HighFrequencyBatchedLinearAccelerationListener listener = new HighFrequencyBatchedLinearAccelerationListener(ctx);
        this.listener = listener;
        listener.setHandler(this);
        localHistory = new ArrayList<>();
        comparator = new HistoryTimeComparator();
    }

    public void startComputingSpeed() {
        listener.startSensing();
    }

    public void stopComputingSpeed() {
        listener.stopSensing();
    }

    public float getAvarageSpeed(long start, long end) {
        float total_m_s = 0;
        Collections.sort(localHistory, comparator);
        for (int i = 1; i < localHistory.size(); i++) {
            HistoryRecord lastRec = localHistory.get(i-1);
            HistoryRecord rec = localHistory.get(i);
            if (rec.timestampInMicrosecend > start && rec.timestampInMicrosecend < end) {
                total_m_s = total_m_s + rec.acceleration*((rec.timestampInMicrosecend - lastRec.timestampInMicrosecend)/ 1000000L);
            }
        }
        float kmh = (float)total_m_s*3.6f;
        return kmh;
    }

    @Override
    public void accelerationChanged(long timestampInMicrosecend, float x, float y, float z) {
        HistoryRecord record = new HistoryRecord(timestampInMicrosecend,computeAcceleration(x,y,z));
        localHistory.add(record);
        purgeHistory();
    }

    private float computeAcceleration(float x, float y, float z) {
        return (float)Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2));
    }

    public void purgeHistory() {
        localHistory.clear();
    }

    private class HistoryRecord {
        public HistoryRecord(long timestampInMicrosecend, float acceleration) {
            this.timestampInMicrosecend = timestampInMicrosecend;
            this.acceleration = acceleration;
        }
        long timestampInMicrosecend;
        float acceleration;
    }

    private class HistoryTimeComparator implements Comparator<HistoryRecord> {
        @Override
        public int compare(HistoryRecord lhs, HistoryRecord rhs) {
            Long time1 = lhs.timestampInMicrosecend;
            Long time2 = rhs.timestampInMicrosecend;
            return time1.compareTo(time2);
        }
    }
}
