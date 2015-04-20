package android.upipc.knowcenter.at.speedkitty.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j_simon on 20/04/15.
 *
 * Utils for working with SQLite
 *
 */
public class DBUtils {

    // this is taken from:
    // http://stackoverflow.com/questions/525512/drop-all-tables-command
    public static void dropAllTables(SQLiteDatabase db) {
        List<String> tables = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM sqlite_master WHERE type='table';", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String tableName = cursor.getString(1);
            if (!tableName.equals("android_metadata") &&
                    !tableName.equals("sqlite_sequence"))
                tables.add(tableName);
            cursor.moveToNext();
        }
        cursor.close();

        for(String tableName:tables) {
            db.execSQL("DROP TABLE IF EXISTS " + tableName);
        }
    }
}
