package android.upipc.knowcenter.at.speedkitty.sensing.accelerometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by j_simon on 23/04/15.
 */
public class LinearAccelerationListener implements SensorEventListener {

    Context ctx;
    SensorManager sensorManager;
    LinearAccelerationHandler handler;

    public LinearAccelerationListener(Context ctx, LinearAccelerationHandler handler) {
        this.handler = handler;
        this.ctx = ctx;
    }

    public void startSensing() {
        sensorManager = (SensorManager)ctx.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_FASTEST, 2000000);
    }

    public void stopSensing() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        handler.accelertionChanges(event.timestamp,
                event.values[0],
                event.values[1],
                event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
