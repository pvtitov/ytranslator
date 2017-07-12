package pvtitov.ytranslator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pvtitov.ytranslator.database.DatabaseWrapper.HistoryTable;

/**
 * Created by Павел on 25.06.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "historyDatabase.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + HistoryTable.NAME + "( _id integer primary key autoincrement, "
        + HistoryTable.Collumns.WORD + ", " + HistoryTable.Collumns.TRANSLATION + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
