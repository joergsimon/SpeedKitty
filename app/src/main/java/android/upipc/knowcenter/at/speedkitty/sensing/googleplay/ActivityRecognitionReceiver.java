package android.upipc.knowcenter.at.speedkitty.sensing.googleplay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static android.upipc.knowcenter.at.speedkitty.sensing.googleplay.ActivityRecognitionConstants.*;

/**
 * Created by j_simon on 23/04/15.
 */
public class ActivityRecognitionReceiver extends BroadcastReceiver {
    ActivityUpdater updater;
    Context ctx;

    public ActivityRecognitionReceiver(Context ctx, ActivityUpdater updater) {
        this.updater = updater;
        this.ctx = ctx;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(BROADCAST_ACTION)) {
            boolean running = intent.getExtras().getBoolean(RUNNING_FIELD);
            int confidence = intent.getExtras().getInt(CONFIDENCE_FIELD);
            long time = intent.getExtras().getLong(TIME_FIELD);
            updater.activityUpdates(running,confidence,time);
            Toast.makeText(ctx, "running? " + running, Toast.LENGTH_LONG).show();
        }
    }
}
