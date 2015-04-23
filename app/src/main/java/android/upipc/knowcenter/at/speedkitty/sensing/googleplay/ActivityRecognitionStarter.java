package android.upipc.knowcenter.at.speedkitty.sensing.googleplay;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;

/**
 * Created by j_simon on 22/04/15.
 */
public class ActivityRecognitionStarter  {


    public ActivityRecognitionStarter(Context ctx) {

    }

    public void startDetectingActivities() {
        Log.d("SENSING", "attempt to connect to activity recognition");

    }

    public void stopDetectingActivities() {
        Log.d("SENSING", "attempt to DISCONNECT activity recognition");

    }

}
