package android.upipc.knowcenter.at.speedkitty.logic;

import android.content.Context;
import android.upipc.knowcenter.at.speedkitty.sensing.googleplay.ActivityRecognitionConstants;
import android.upipc.knowcenter.at.speedkitty.sensing.googleplay.ActivityUpdater;

/**
 * Created by j_simon on 16/04/15.
 *
 * This class detects if the user is currently running or not. You need to register a RunningNotifier
 * and call startDetectingChanges. The RunningNotifier will then detect if the running mode changed
 *
 */
public class RunningDetector implements ActivityUpdater {

    RunningNotifier notifier;
    boolean currentMode;
    long startTime;

    public RunningDetector(Context ctx, RunningNotifier notifier) {
        this.notifier = notifier;
        currentMode = false;
    }


    public void startDetectingChanges() {

    }

    public void stopDetectingChanges() {

    }


    @Override
    public void activityUpdates(int running, int confidence, long time) {
        boolean isRunning = (running == ActivityRecognitionConstants.RUNNING);
        if (currentMode != isRunning) {
            if (!currentMode) {
                notifier.runDidStart(time);
            }
            else {
                notifier.runDidEnd(time, 0);

            }
            startTime = time;
            currentMode = isRunning;
        }
    }
}
