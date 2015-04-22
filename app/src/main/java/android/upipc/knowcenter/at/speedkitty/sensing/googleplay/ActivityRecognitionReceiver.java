package android.upipc.knowcenter.at.speedkitty.sensing.googleplay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.upipc.knowcenter.at.speedkitty.sensing.googleplay.ActivityRecognitionConstants.*;

/**
 * Created by j_simon on 22/04/15.
 */
public class ActivityRecognitionReceiver extends BroadcastReceiver {

        private ActivityUpdater updater;

        public ActivityRecognitionReceiver(ActivityUpdater updater) {
            this.updater = updater;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BROADCAST_ACTION)) {
                int running = intent.getExtras().getInt(RUNNING_FIELD);
                int confidence = intent.getExtras().getInt(CONFIDENCE_FIELD);
                long time = intent.getExtras().getLong(TIME_FIELD);
                updater.activityUpdates(running, confidence, time);
            }
        }
}
