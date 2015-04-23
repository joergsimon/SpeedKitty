package android.upipc.knowcenter.at.speedkitty.sensing.accelerometer;

/**
 * Created by j_simon on 23/04/15.
 */
public interface LinearAccelerationHandler {
    void accelertionChanges(long timestamp, float x, float y, float z);
}
