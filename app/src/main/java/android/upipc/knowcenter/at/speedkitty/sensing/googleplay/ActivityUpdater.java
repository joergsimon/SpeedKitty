package android.upipc.knowcenter.at.speedkitty.sensing.googleplay;

/**
 * Created by j_simon on 22/04/15.
 */
public interface ActivityUpdater {
    void activityUpdates(int running, int confidence, long time);
}
