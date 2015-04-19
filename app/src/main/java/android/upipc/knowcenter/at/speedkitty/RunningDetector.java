package android.upipc.knowcenter.at.speedkitty;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j_simon on 16/04/15.
 *
 * This class detects if the user is currently running or not. You need to register a RunningNotifier
 * and call startDetectingChanges. The RunningNotifier will then detect if the running mode changed
 *
 */
public class RunningDetector implements LinearAccelerationHandler {

    public final float THRESHOLD = 9;

    List<HistoryRecord> localHistory;
    boolean currentMode;
    LinearAccelerationListener listener;
    RunningNotifier notifier;

    public RunningDetector(Context ctx, RunningNotifier notifier) {
        HighFrequencyBatchedLinearAccelerationListener listener = new HighFrequencyBatchedLinearAccelerationListener(ctx);
        init(listener, notifier);
    }

    public RunningDetector(LinearAccelerationListener listener, RunningNotifier notifier) {
        init(listener, notifier);
    }

    private void init(LinearAccelerationListener listener, RunningNotifier notifier) {
        this.listener = listener;
        this.notifier = notifier;
        listener.setHandler(this);
        currentMode = false;
        localHistory = new ArrayList<>();
    }


    @Override
    public void accelerationChanged(long timestampInMicrosecend, float x, float y, float z) {
        HistoryRecord record = new HistoryRecord(timestampInMicrosecend,computeAcceleration(x,y,z));
        localHistory.add(record);
        detectModeChange();
        purgeHistory();
    }

    private float computeAcceleration(float x, float y, float z) {
        return (float)Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2));
    }

    public void startDetectingChanges() {
        listener.startSensing();
    }

    public void stopDetectingChanges() {
        listener.stopSensing();
    }

    private void detectModeChange() {
        boolean isRunning = detectRunning();
        if (currentMode != isRunning) {
            notifier.notifyChange(currentMode, getLastTimestamp());
            currentMode = isRunning;
        }
    }

    int counter = 0;
    boolean running =false;
    private boolean detectRunning() {
        counter++;
        if (counter % 3000 == 0) {
            running = !running;
        }
        return running;
    }

    private long getLastTimestamp() {
        if (localHistory.size() == 0) {
            return 0;
        }
        return localHistory.get(localHistory.size()-1).timestampInMicrosecend;
    }

    private void purgeHistory() {
        // take joda time?
    }

    private class HistoryRecord {
        public HistoryRecord(long timestampInMicrosecend, float acceleration) {
            this.timestampInMicrosecend = timestampInMicrosecend;
            this.acceleration = acceleration;
        }
        long timestampInMicrosecend;
        float acceleration;
    }
}
