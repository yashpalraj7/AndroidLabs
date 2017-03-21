package com.example.james.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by james on 2017-02-14.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    final static String ACTIVITY_NAME="ChatDatabaseHelper";
    public final static String TABLE_NAME = "CHAT";
    final static String KEY_ID = "_ID";
    final static String KEY_MESSAGE = "KEY_MESSAGE";
    final static String DATABASE_NAME = "Chats.db";
    static int VERSION_NUM = 2;

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(ACTIVITY_NAME, "Calling onCreate");
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + KEY_ID +" INTEGER PRIMARY KEY, " + KEY_MESSAGE + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
