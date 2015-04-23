package android.upipc.knowcenter.at.speedkitty.logic;

import android.content.Context;
import android.upipc.knowcenter.at.speedkitty.sensing.accelerometer.AvarageSpeedDetector;
import android.upipc.knowcenter.at.speedkitty.sensing.googleplay.ActivityRecognitionConstants;
import android.upipc.knowcenter.at.speedkitty.sensing.googleplay.ActivityRecognitionFacade;
import android.upipc.knowcenter.at.speedkitty.sensing.googleplay.ActivityUpdater;

/**
 * Created by j_simon on 16/04/15.
 *
 * This class detects if the user is currently running or not. You need to register a RunningNotifier
 * and call startDetectingChanges. The RunningNotifier will then detect if the running mode changed
 *
 */
public class RunningDetector implements ActivityUpdater {

    ActivityRecognitionFacade recognition;
    AvarageSpeedDetector speedDetector;
    RunningNotifier notifier;
    boolean currentMode;
    long startTime;

    public RunningDetector(Context ctx, RunningNotifier notifier) {
        this.notifier = notifier;
        currentMode = false;
        recognition = new ActivityRecognitionFacade(ctx, this);
        speedDetector = new AvarageSpeedDetector(ctx);
    }


    public void startDetectingChanges() {
        recognition.startDetectingActivities();
        speedDetector.startComputingSpeed();
    }

    public void stopDetectingChanges() {
        recognition.stopDetectingActivities();
        speedDetector.startComputingSpeed();
    }


    @Override
    public void activityUpdates(boolean isRunning, int confidence, long time) {
        if (currentMode != isRunning) {
            if (!currentMode) {
                notifier.runDidStart(time);
            }
            else {
                notifier.runDidEnd(time, speedDetector.getAvarageSpeeed(startTime, time));

            }
            speedDetector.purgeHistory();
            startTime = time;
            currentMode = isRunning;
        }
    }
}
