package ru.dan.translator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by  DubininA on 21.04.2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_HISTORY = "TableHistory";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ORIGLANG = "origLang";
    public static final String COLUMN_ORIGTEXT = "origText";
    public static final String COLUMN_TRANSLATELANG = "translateLang";
    public static final String COLUMN_TRANSLATETEXT = "translateText";
    public static final String COLUMN_FAV = "FAV";
    public static final String HISTORY_CREATE =
            "create table " + TABLE_HISTORY +
                    " (" + COLUMN_ID +
                    " integer primary key autoincrement," +
                    COLUMN_ORIGLANG + " text not null" +
                    COLUMN_ORIGTEXT + " text not null" +
                    COLUMN_ORIGLANG + " text not null" +
                    COLUMN_TRANSLATELANG + " text not null" +
                    COLUMN_TRANSLATETEXT + " text not null" +
                    COLUMN_FAV + " text not null" +
                    ");";

    private static final String mDataBase = "history.db";
    private static final int mVersion = 1;

    public DBHelper(Context context) {
        super(context, mDataBase, null, mVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HISTORY_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
