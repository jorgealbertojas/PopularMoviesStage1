package com.example.jorge.popularmoviesstage1.utilities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.example.jorge.popularmoviesstage1.data.StarContract.StarEntry.COLUMN_ID;
import static com.example.jorge.popularmoviesstage1.data.StarContract.StarEntry.TABLE_NAME;

/**
 * Created by jorge on 13/11/2017.
 */

public class Common {


    public static boolean verifyStarExist(String id, SQLiteDatabase db) {
        Cursor cursor;
        cursor = 	db.rawQuery(" SELECT " +  COLUMN_ID + " FROM " + TABLE_NAME + " WHERE ID = ? ", new String[]{id});
        if (cursor.getCount() > 0){
            return 	true;
        }else{
            return 	false;
        }
    }

    public static Cursor getAllStar (SQLiteDatabase db) {
        return 	db.rawQuery(" SELECT " +  COLUMN_ID + " FROM " + TABLE_NAME , null);
    }
}
