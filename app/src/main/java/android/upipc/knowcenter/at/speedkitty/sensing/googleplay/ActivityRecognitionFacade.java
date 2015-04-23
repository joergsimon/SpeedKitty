package android.upipc.knowcenter.at.speedkitty.sensing.googleplay;

import android.content.Context;
import android.content.IntentFilter;

import static android.upipc.knowcenter.at.speedkitty.sensing.googleplay.ActivityRecognitionConstants.*;

/**
 * Created by j_simon on 22/04/15.
 */
public class ActivityRecognitionFacade implements ActivityUpdater {

    private ActivityRecognitionStarter starter;
    private ActivityRecognitionReceiver receiver;
    private Context ctx;
    private ActivityUpdater updater;

    public ActivityRecognitionFacade(Context ctx, ActivityUpdater updater) {
        starter = new ActivityRecognitionStarter(ctx);
        this.ctx = ctx;
        this.updater = updater;
    }

    public void startDetectingActivities() {
        IntentFilter filter = new IntentFilter(BROADCAST_ACTION);

        receiver = new ActivityRecognitionReceiver(ctx, this);
        ctx.registerReceiver(receiver, filter);
        starter.startDetectingActivities();
    }

    public void stopDetectingActivities() {
        starter.stopDetectingActivities();
        ctx.unregisterReceiver(receiver);
    }

    @Override
    public void activityUpdates(boolean running, int confidence, long time) {
        updater.activityUpdates(running, confidence, time);
    }
}
