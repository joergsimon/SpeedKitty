package android.upipc.knowcenter.at.speedkitty;

/**
 * Created by j_simon on 17/04/15.
 *
 * An Enum presenting the Moods our Kitty can have.
 * If you did cast it to an int, use fromOrdinal to transfer it back
 */
public enum KittyMood {
    RUNNING,
    SCARED,
    NEUTRAL,
    MOTIVATED;

    private static KittyMood[] allValues = values();
    public static KittyMood fromOrdinal(int n) {return allValues[n];}
}
