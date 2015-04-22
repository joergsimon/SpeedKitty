package android.upipc.knowcenter.at.speedkitty.logic;

import android.content.Context;
import android.upipc.knowcenter.at.speedkitty.sensing.accelerometer.HighFrequencyBatchedLinearAccelerationListener;
import android.upipc.knowcenter.at.speedkitty.sensing.accelerometer.LinearAccelerationHandler;
import android.upipc.knowcenter.at.speedkitty.sensing.accelerometer.LinearAccelerationListener;
import android.upipc.knowcenter.at.speedkitty.sensing.accelerometer.speed.AvarageSpeedDetector;
import android.upipc.knowcenter.at.speedkitty.sensing.googleplay.ActivityRecognitionConstants;
import android.upipc.knowcenter.at.speedkitty.sensing.googleplay.ActivityRecognitionFacade;
import android.upipc.knowcenter.at.speedkitty.sensing.googleplay.ActivityUpdater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j_simon on 16/04/15.
 *
 * This class detects if the user is currently running or not. You need to register a RunningNotifier
 * and call startDetectingChanges. The RunningNotifier will then detect if the running mode changed
 *
 */
public class RunningDetector implements ActivityUpdater {

    AvarageSpeedDetector speedDetector;
    ActivityRecognitionFacade activityRecognition;
    RunningNotifier notifier;
    boolean currentMode;
    long startTime;

    public RunningDetector(Context ctx, RunningNotifier notifier) {
        speedDetector = new AvarageSpeedDetector(ctx);
        activityRecognition = new ActivityRecognitionFacade(ctx, this);
        this.notifier = notifier;
        currentMode = false;
    }


    public void startDetectingChanges() {
        speedDetector.startComputingSpeed();
        activityRecognition.startDetectingActivities();
    }

    public void stopDetectingChanges() {
        speedDetector.stopComputingSpeed();
        activityRecognition.stopDetectingActivities();
    }


    @Override
    public void activityUpdates(int running, int confidence, long time) {
        boolean isRunning = (running == ActivityRecognitionConstants.RUNNING);
        if (currentMode != isRunning) {
            if (!currentMode) {
                speedDetector.purgeHistory(); // we loose some accuracy here, but ok...
                notifier.runDidStart(time);
            }
            else {
                notifier.runDidEnd(time, speedDetector.getAvarageSpeed(startTime, time));
                speedDetector.purgeHistory();
            }
            startTime = time;
            currentMode = isRunning;
        }
    }
}
