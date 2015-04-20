package android.upipc.knowcenter.at.speedkitty.sensing;

/**
 * Created by j_simon on 16/04/15.
 *
 * A interface for any class using the linear acceleration. only one class will receive these
 * events, so if you need multiple destinations write a broadcaster
 */
public interface LinearAccelerationHandler {
    void accelerationChanged(long timestampInMicrosecend, float x, float y, float z);
}
