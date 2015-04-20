package android.upipc.knowcenter.at.speedkitty.sensing;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by j_simon on 16/04/15.
 *
 * Standart code for the TYPE_LINEAR_ACCELERATION Sensor of Android
 *
 */
public class LinearAccelerationListener implements SensorEventListener {

    private SensorManager sensorManager;
    private Context ctx;
    private int sensorDelay;
    private int batchDelay;
    private LinearAccelerationHandler handler;

    public LinearAccelerationListener(Context ctx, int sensorDelay, int batchDelay) {
        this.ctx = ctx;
        this.sensorDelay = sensorDelay;
        this.batchDelay = batchDelay;
    }

    public void setHandler(LinearAccelerationHandler handler) {
        this.handler = handler;
    }

    public void startSensing() {
        if (handler == null) {
            Log.wtf("sensing", "no handler!");
        }

        sensorManager = (SensorManager)ctx.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), sensorDelay, batchDelay);
    }

    public void stopSensing() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        handler.accelerationChanged(event.timestamp, event.values[0], event.values[1], event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
