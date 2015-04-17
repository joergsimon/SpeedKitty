package android.upipc.knowcenter.at.speedkitty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by j_simon on 17/04/15.
 *
 * BroadcaseReceiver receiving mood updates from the MotivationLogic. Register a
 * KittyMoodUpdater to do something with it.
 *
 */
public class KittyMoodReceiver extends BroadcastReceiver {

    private KittyMoodUpdater updater;

    public KittyMoodReceiver(KittyMoodUpdater updater) {
        this.updater = updater;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(MotivationLogic.BROADCAST_ACTION)) {
            int mood = intent.getExtras().getInt(MotivationLogic.MOOD_FIELD);
            updater.updateKittyMood(KittyMood.fromOrdinal(mood));
        }
    }
}
