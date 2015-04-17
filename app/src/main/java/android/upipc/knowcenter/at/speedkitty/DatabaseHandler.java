package android.upipc.knowcenter.at.speedkitty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by j_simon on 17/04/15.
 *
 * Our very simple DB. Has a speed value, a info if there was a run this week and that is it.
 *
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SpeedKittyDB";

    private static final String KEY_ID = "_id"; // global id key

    private static final String TABLE_SPEED = "Speed";
    private static final String KEY_COUNT = "count";

    private static final String TABLE_WEEK = "Week";
    private static final String KEY_DIDRUN = "didrun";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SPEED_TABLE = "CREATE TABLE " + TABLE_SPEED + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_COUNT + " INTEGER)";
        db.execSQL(CREATE_SPEED_TABLE);

        String CREATE_WEEK_TABLE = "CREATE TABLE " + TABLE_WEEK + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_DIDRUN + " BOOLEAN)";
        db.execSQL(CREATE_WEEK_TABLE);

        insertSpeed(1);
        insertDidRunThisWeek(false);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // not supported currently
    }

    private void insertSpeed(int speed) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COUNT, speed); // Contact Name

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
    }

    public int getSpeed() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SPEED, new String[] { KEY_COUNT }, KEY_ID + "=?",
                new String[] { "1" }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            insertSpeed(1);
            return 1;
        }
        // return contact
        return Integer.parseInt(cursor.getString(0));
    }

    private void insertDidRunThisWeek(boolean didRun) {

    }
}
