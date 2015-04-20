package android.upipc.knowcenter.at.speedkitty.logic;

import android.content.Context;
import android.content.Intent;
import android.upipc.knowcenter.at.speedkitty.db.DatabaseHandler;

/**
 * Created by j_simon on 17/04/15.
 *
 * Main Logic class. Connects the Activity Detection to the DB, sends broadcasts, and
 * contains the main rules.
 *
 */
public class MotivationLogic implements RunningNotifier {

    public static final String BROADCAST_ACTION = "KittyMoodUpdated";
    public static final String MOOD_FIELD = "KittyMood";

    private boolean isRunning;
    private boolean isInBackground;
    private Context ctx;
    private DatabaseHandler databaseHandler;
    private RunningDetector runningDetector;

    public MotivationLogic(Context ctx, boolean isInBackground) {
        this.ctx = ctx;
        this.isInBackground = isInBackground;
        databaseHandler = new DatabaseHandler(ctx);
        runningDetector = new RunningDetector(ctx, this);
    }

    public void startMonitorMoticationUpdates() {
        runningDetector.startDetectingChanges();
    }

    public void stopMonitorMoticationUpdates() {
        runningDetector.stopDetectingChanges();
    }

    @Override
    public void notifyChange(boolean isRunning, long timestamp) {
        this.isRunning = isRunning;
        incrementSpeedInDB();
        broadcastKittiesMood();
    }

    private void broadcastKittiesMood() {
        KittyMood mood = getKittiesMood();
        if (isInBackground) {
            sendNotification(mood);
        }
        else {
            sendIntent(mood);
        }
    }

    private void sendNotification(KittyMood mood) {
        // TODO: add that when you did the Service
    }

    private void sendIntent(KittyMood mood) {
        Intent intent = new Intent(BROADCAST_ACTION);
        intent.putExtra(MOOD_FIELD, mood.getValue());
        ctx.sendBroadcast(intent);
    }

    private void incrementSpeedInDB() {
        int currentSpeed = databaseHandler.getSpeed();
        if (currentSpeed > 20) {
            currentSpeed = 0;
        }
        databaseHandler.updateSpeed(currentSpeed + 1);
    }

    public KittyMood getKittiesMood() {
        if (isRunning) {
            return KittyMood.RUNNING;
        }
        int speed = getSpeed();
        if (speed == 1) {
            return KittyMood.SCARED;
        }
        if (speed < 10) {
            return KittyMood.NEUTRAL;
        }
        return KittyMood.MOTIVATED;
    }

    public int adoptSpeedForWeek() {
        return 0;
    }

    public int getSpeed() {
        return databaseHandler.getSpeed();
    }
}
