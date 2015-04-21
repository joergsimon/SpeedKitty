package android.upipc.knowcenter.at.speedkitty.logic;

/**
 * Created by j_simon on 16/04/15.
 *
 * An Interface for any class responsible to notify other parts of an app
 * that the mode (running|not runnin) has changed
 *
 */
public interface RunningNotifier {
    void runDidStart(long timestamp);
    void runDidEnd(long timestamp, float avarageSpeed);
}
