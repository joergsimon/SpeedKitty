package android.upipc.knowcenter.at.speedkitty.sensing.googleplay;

import android.content.Context;
import android.content.IntentFilter;

import static android.upipc.knowcenter.at.speedkitty.sensing.googleplay.ActivityRecognitionConstants.*;

/**
 * Created by j_simon on 22/04/15.
 */
public class ActivityRecognitionFacade implements ActivityUpdater {

    private ActivityRecognitionStarter starter;
    private Context ctx;
    private ActivityUpdater updater;

    public ActivityRecognitionFacade(Context ctx, ActivityUpdater updater) {
        starter = new ActivityRecognitionStarter(ctx);
        this.ctx = ctx;
        this.updater = updater;
    }

    public void startDetectingActivities() {

    }

    public void stopDetectingActivities() {
        starter.stopDetectingActivities();
    }

    @Override
    public void activityUpdates(int running, int confidence, long time) {
        updater.activityUpdates(running, confidence, time);
    }
}
