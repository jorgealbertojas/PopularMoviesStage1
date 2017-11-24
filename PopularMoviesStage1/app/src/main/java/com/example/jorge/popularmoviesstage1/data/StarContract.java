package com.example.jorge.popularmoviesstage1.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jorge on 07/11/2017.
 */

public class StarContract {

    public static final String PATH_STAR = "data";

    public static final class StarEntry implements BaseColumns {
        public static final String TABLE_NAME = "star";
        public static final String COLUMN_ID = "id";

        // this parameters for user Content Provider
        public static final String STAR_CHECK = "100";
        public static final String STAR_INSERT = "101";
        public static final String STAR_DELETE = "102";
        public static final String STAR_GET_ALL = "102";
        public static final String CONTENT_AUTHORITY = "com.example.jorge.popularmoviesstage1";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_STAR)
                .query(STAR_CHECK)
                .build();

        public static Uri CONTENT_URI_INSERT = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_STAR)
                .query(STAR_INSERT)
                .build();

        public static Uri CONTENT_URI_DELETE = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_STAR)
                .query(STAR_DELETE)
                .build();

        public static Uri CONTENT_URI_GET_ALL = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_STAR)
                .query(STAR_GET_ALL)
                .build();


    }

}
