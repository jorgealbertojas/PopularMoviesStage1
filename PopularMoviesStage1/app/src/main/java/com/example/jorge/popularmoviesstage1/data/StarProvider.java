package com.example.jorge.popularmoviesstage1.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.jorge.popularmoviesstage1.R;

import static com.example.jorge.popularmoviesstage1.data.StarContract.StarEntry.COLUMN_ID;
import static com.example.jorge.popularmoviesstage1.data.StarContract.StarEntry.TABLE_NAME;


/**
 * Created by jorge on 23/11/2017.
 */

/** Create the class (ContentProvider) for support the update in BD SQLLite**/

public class StarProvider extends ContentProvider {

    private SQLiteDatabase mDb;
    private StarDbHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new StarDbHelper(getContext());
        return true;
    }

    /** Insert Star in DB SQLLite**/
    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        String uriType = uri.getEncodedQuery();

        switch (uriType) {

            case StarContract.StarEntry.STAR_INSERT:
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(StarContract.StarEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    try{
                        getContext().getContentResolver().notifyChange(uri, null);
                    }catch(NullPointerException e){
                        System.out.println("onActivityResult consume crashed");
                    }
                }

                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }

    /** Queries Star for get information of the DB SQLLite**/
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        String uriType = uri.getEncodedQuery();

        Cursor cursor;

        StarDbHelper dbHelper = new StarDbHelper(getContext());
        mDb = dbHelper.getWritableDatabase();

        switch (uriType) {
            case StarContract.StarEntry.STAR_CHECK:

                cursor = mDb.rawQuery(" SELECT " +  COLUMN_ID + " FROM " + TABLE_NAME + " WHERE ID = ? ", new String[]{selection});
                break;
            case StarContract.StarEntry.STAR_GET_ALL:

                cursor = 	mDb.rawQuery(" SELECT " +  COLUMN_ID + " FROM " + TABLE_NAME , null);
                break;
            default:

                throw new IllegalArgumentException("Unknown URI");

        }

        return cursor;



    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    /** Delete Star in DB SQLLite**/
    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        String uriType = uri.getEncodedQuery();

        switch (uriType) {

            case StarContract.StarEntry.STAR_DELETE:
                db.beginTransaction();
                int rowsInserted = 0;
                try{
                    try {
                        for (String value : strings) {

                            long _id = db.delete(StarContract.StarEntry.TABLE_NAME, COLUMN_ID + "=" + value, null);
                            if (_id != -1) {
                                rowsInserted++;
                            }
                        }
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }

                    if (rowsInserted > 0) {
                        getContext().getContentResolver().notifyChange(uri, null);
                    }
                }catch(NullPointerException e){
                    System.out.println("onActivityResult consume crashed");

                }

                return rowsInserted;
            default:
                return 0;

        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }





}
