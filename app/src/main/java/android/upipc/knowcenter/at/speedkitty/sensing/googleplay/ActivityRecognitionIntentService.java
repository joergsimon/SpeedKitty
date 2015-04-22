package android.upipc.knowcenter.at.speedkitty.sensing.googleplay;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

import static android.upipc.knowcenter.at.speedkitty.sensing.googleplay.ActivityRecognitionConstants.*;

/**
 * Created by j_simon on 22/04/15.
 */
public class ActivityRecognitionIntentService extends IntentService {

    private static final String NAME = ActivityRecognitionIntentService.class.getSimpleName();
    private Handler handler;

    public ActivityRecognitionIntentService() {
        super(NAME);
        Log.d("SENSING", "start activity recognition intent service with name: " + NAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("SENSING", "got an activity recognition update");
        toast("got intent: " + intent.getAction(), Toast.LENGTH_SHORT);
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            DetectedActivity activity = getActivity(result);
            int confidence = activity.getConfidence();
            long time = result.getTime();
            int running = NOT_RUNNING;
            if (activity.getType() == DetectedActivity.RUNNING) {
                running = RUNNING;
            }
            toast("detected activity: " + activity.getType(), Toast.LENGTH_LONG);
            sendBroadcast(running, confidence, time);
        }
    }

    // inspired by: http://stackoverflow.com/questions/24815302/how-to-use-activity-recognition-to-detect-walking-running-vs-on-foot
    private DetectedActivity getActivity(ActivityRecognitionResult result) {
        DetectedActivity activity = result.getMostProbableActivity();
        int activityType = activity.getType();
        if (activityType == DetectedActivity.ON_FOOT) {
            DetectedActivity betterActivity = walkingOrRunning(result.getProbableActivities());
            if (null != betterActivity)
                activity = betterActivity;
        }
        return activity;
    }

    // taken from http://stackoverflow.com/questions/24815302/how-to-use-activity-recognition-to-detect-walking-running-vs-on-foot
    private DetectedActivity walkingOrRunning(List<DetectedActivity> probableActivities) {
        DetectedActivity myActivity = null;
        int confidence = 0;
        for (DetectedActivity activity : probableActivities) {
            if (activity.getType() != DetectedActivity.RUNNING && activity.getType() != DetectedActivity.WALKING)
                continue;

            if (activity.getConfidence() > confidence)
                myActivity = activity;
        }

        return myActivity;
    }

    private void sendBroadcast(int running, int confidence, long time) {
        Intent intent = new Intent(BROADCAST_ACTION);
        intent.putExtra(RUNNING_FIELD, running);
        intent.putExtra(CONFIDENCE_FIELD, confidence);
        intent.putExtra(TIME_FIELD, time);
        sendBroadcast(intent);
    }

    private void toast(final String msg, final int duration) {
        handler.post(new Runnable() {
            final ActivityRecognitionIntentService srv = ActivityRecognitionIntentService.this;
            @Override
            public void run() {
                Toast.makeText(srv, msg, duration).show();
            }
        });
    }
}
