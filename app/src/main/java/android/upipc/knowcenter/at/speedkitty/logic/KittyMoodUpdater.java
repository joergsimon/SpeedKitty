package android.upipc.knowcenter.at.speedkitty.logic;

/**
 * Created by j_simon on 17/04/15.
 *
 * Interface for anyone who is interested in reacting to Broadcast Events from the Motivation Logic
 *
 */
public interface KittyMoodUpdater {
    void updateKittyMood(KittyMood mood);
}
