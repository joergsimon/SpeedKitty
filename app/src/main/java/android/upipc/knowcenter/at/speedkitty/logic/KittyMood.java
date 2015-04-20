package android.upipc.knowcenter.at.speedkitty.logic;

/**
 * Created by j_simon on 17/04/15.
 *
 * An Enum presenting the Moods our Kitty can have.
 * If you did cast it to an int, use fromOrdinal to transfer it back
 */
public enum KittyMood {
    RUNNING(0),
    SCARED(1),
    NEUTRAL(2),
    MOTIVATED(3),
    DEAD(4);

    private static KittyMood[] allValues = values();
    public static KittyMood fromOrdinal(int n) {return allValues[n];}

    private final int value;
    private KittyMood(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
