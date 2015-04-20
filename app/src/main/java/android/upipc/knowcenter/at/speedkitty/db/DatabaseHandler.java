package android.upipc.knowcenter.at.speedkitty.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by j_simon on 17/04/15.
 *
 * Our very simple DB. Has a speed value, a info if there was a run this week and that is it.
 *
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "SpeedKittyDB";

    private static final String KEY_ID = "_id"; // global id key

    private static final String TABLE_SPEED = "Speed";
    private static final String KEY_COUNT = "count";

    private static final String TABLE_RUNS = "Runs";
    private static final String KEY_RUN_START = "start_timestamp";
    private static final String KEY_RUN_END = "end_timestamp";
    private static final String KEY_RUN_AVARAGE_SPEED = "speed";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SPEED_TABLE = "CREATE TABLE " + TABLE_SPEED + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_COUNT + " INTEGER)";
        db.execSQL(CREATE_SPEED_TABLE);

        String CREATE_WEEK_TABLE = "CREATE TABLE " + TABLE_RUNS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_RUN_START + " INTEGER, "
                + KEY_RUN_END + " INTEGER, " + KEY_RUN_AVARAGE_SPEED + " REAL)";
        db.execSQL(CREATE_WEEK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no really supported currently. We just drop all and recreate
        DBUtils.dropAllTables(db);
        onCreate(db);
    }

    private void insertSpeed(int speed) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COUNT, speed);

        // Inserting Row
        db.insert(TABLE_SPEED, null, values);
        db.close(); // Closing database connection
    }

    public void updateSpeed(int speed) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COUNT, speed);

        // updating row
        db.update(TABLE_SPEED, values, KEY_ID + " = ?",
                new String[]{"1"});
        db.close();
    }

    public int getSpeed() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SPEED, new String[]{KEY_COUNT}, KEY_ID + "=?",
                new String[]{"1"}, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
        } else {
            insertSpeed(1);
            return 1;
        }
        int results = cursor.getCount();
        int position = cursor.getPosition();
        String speedStr = cursor.getString(0);
        db.close();
        return Integer.parseInt(speedStr);
    }

    public void insertRun(Run run) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = assembleContentValues(run);

        // Inserting Row
        db.insert(TABLE_RUNS, null, values);
        db.close(); // Closing database connection
    }

    public void updateRun(Run run) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = assembleContentValues(run);

        // Inserting Row
        db.update(TABLE_SPEED, values, KEY_ID + " = ?",
                new String[]{run.getId()});
        db.close(); // Closing database connection
    }

    private ContentValues assembleContentValues(Run run) {
        ContentValues values = new ContentValues();
        values.put(KEY_RUN_START, run.start);
        values.put(KEY_RUN_END, run.end);
        values.put(KEY_RUN_AVARAGE_SPEED, run.avarageSpeed);
        return values;
    }

    public List<Run> getAllRuns() {
        return null; // TODO: implement
    }

    public Run getLastRun() {
        return null; // TODO: implement
    }
}
