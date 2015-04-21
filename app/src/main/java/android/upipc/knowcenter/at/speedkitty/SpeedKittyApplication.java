package android.upipc.knowcenter.at.speedkitty;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by j_simon on 21/04/15.
 */
public class SpeedKittyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
