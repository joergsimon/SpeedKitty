package android.upipc.knowcenter.at.speedkitty.sensing.accelerometer;

import android.content.Context;
import android.hardware.SensorManager;

/**
 * Created by j_simon on 16/04/15.
 *
 * A LinearAccelerationListener with configurations siutable for our running detector
 * Sensor Delay is Fastst, But will be processed only every 5 seconds
 *
 */
public class HighFrequencyBatchedLinearAccelerationListener extends LinearAccelerationListener {
    public HighFrequencyBatchedLinearAccelerationListener(Context ctx) {
        super(ctx, SensorManager.SENSOR_DELAY_FASTEST, 2000000);
    }
}
