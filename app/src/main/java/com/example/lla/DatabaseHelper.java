package com.example.lla;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String COLUMN_HEADING = "heading";
    public static final String COLUMN_IMAGE = "image";
    public static final String TABLE_NAME = "videos";
    private static final String COLUMN_ID = "id";
    // Database name and version
    private static final String DATABASE_NAME = "lla3";
    private static final int DATABASE_VERSION = 1;

    // Table name and columns


    // Create table query
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_HEADING + " TEXT, " +
            COLUMN_IMAGE + " INTEGER" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
    }
}

