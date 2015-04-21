package android.upipc.knowcenter.at.speedkitty.db;

/**
 * Created by j_simon on 20/04/15.
 */
public class Run {
    protected int id;
    protected long start;
    protected long end;
    protected float avarageSpeed;

    /**
     * @param start should be a timestamp IN SECONDS
     * @param end should be a timestamp IN SECONDS
     * @param avarageSpeed float for km/h speed
     */
    public Run(long start, long end, float avarageSpeed) {
        this(-1, start, end, avarageSpeed);
    }

    /**
     * @param id id of the record in the database
     * @param start should be a timestamp IN SECONDS
     * @param end should be a timestamp IN SECONDS
     * @param avarageSpeed float for km/h speed
     */
    public Run(int id, long start, long end, float avarageSpeed) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.avarageSpeed = avarageSpeed;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }
}
