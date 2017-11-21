package com.example.jorge.popularmoviesstage1.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jorge on 07/11/2017.
 */

public class StarDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "star.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public StarDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold waitlist data
        final String SQL_CREATE_STAR_TABLE = "CREATE TABLE IF NOT EXISTS " + StarContract.StarEntry.TABLE_NAME + " (" +
                StarContract.StarEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                StarContract.StarEntry.COLUMN_ID + " INTEGER NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_STAR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StarContract.StarEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }




}
