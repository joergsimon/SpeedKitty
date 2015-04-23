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
 * Created by j_simon on 23/04/15.
 */
public class ActivityRecognitionIntentService extends IntentService {

    Handler handler;

    public ActivityRecognitionIntentService() {
        super("activity recognition");
        handler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("SENSING", "in on handle intent");
        if (ActivityRecognitionResult.hasResult(intent)) {
            Log.d("SENSING", "got activity recognition result");
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            DetectedActivity activity = getActivity(result);
            Log.d("SENSING", "got activity " + activity.getType());
            //toast("got activity: " + activity.getType(), Toast.LENGTH_SHORT);

            boolean isRunning = activity.getType() == DetectedActivity.RUNNING;
            Intent i = new Intent(BROADCAST_ACTION);
            i.putExtra(RUNNING_FIELD, isRunning);
            i.putExtra(CONFIDENCE_FIELD, activity.getConfidence());
            i.putExtra(TIME_FIELD, result.getTime());
            sendBroadcast(i);
        }
    }

    private DetectedActivity getActivity(ActivityRecognitionResult result) {
        DetectedActivity activity = result.getMostProbableActivity();
        int type = activity.getType();
        if (type == DetectedActivity.ON_FOOT) {
            DetectedActivity better = walkingOrRunning(result.getProbableActivities());
            if (better != null) {
                activity = better;
            }
        }
        return activity;
    }

    private DetectedActivity walkingOrRunning(List<DetectedActivity> activities) {
        DetectedActivity activity = null;
        int confidence = 0;
        for (DetectedActivity a : activities) {
            if (a.getType() != DetectedActivity.RUNNING && a.getType() != DetectedActivity.WALKING) {
                continue;
            }
            if (confidence < a.getConfidence()) {
                activity = a;
                confidence = a.getConfidence();
            }
        }
        return activity;
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
