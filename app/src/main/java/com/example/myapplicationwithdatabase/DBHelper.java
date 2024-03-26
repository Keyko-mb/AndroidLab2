package com.example.myapplicationwithdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "myDb";
    public static final String TABLE_CLASSMATES = "classmates";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TIME = "time";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("create table " + TABLE_CLASSMATES + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text," + KEY_TIME + " text" + ")");

        addStartClassmates(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CLASSMATES);

        onCreate(db);
    }

    void addStartClassmates(SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();

        for (int i = 0; i < 5; i++) {
            String name = "Алексеев Алексей Алексеевич" + (i + 1);
            String currentTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

            contentValues.put(DBHelper.KEY_NAME, name);
            contentValues.put(DBHelper.KEY_TIME, currentTime);

            db.insert(DBHelper.TABLE_CLASSMATES, null, contentValues);
        }
    }
}
