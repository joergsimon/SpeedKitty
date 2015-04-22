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
public class ActivityRecognitionStarter implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient client;
    private PendingIntent intent;

    public ActivityRecognitionStarter(Context ctx) {
        this.client = new GoogleApiClient.Builder(ctx)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        Intent i = new Intent(ctx, ActivityRecognitionIntentService.class);
        intent = PendingIntent.getService(ctx, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void startDetectingActivities() {
        Log.d("SENSING", "attempt to connect to activity recognition");
        client.connect();
    }

    public void stopDetectingActivities() {
        Log.d("SENSING", "attempt to DISCONNECT activity recognition");
        client.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("SENSING", "attempt to request activity recognition updates");
        PendingResult<Status> result = ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(client, 0, intent);
        result.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                Log.d("SENSING", "updates status: " + status + " s:" + status.isSuccess());
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("SENSING", "suspend connection " + i);
        ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(client, intent);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // TODO: make something meaningful here...
        Log.d("SENSING", "could not connect play service " + connectionResult.toString());
    }
}
