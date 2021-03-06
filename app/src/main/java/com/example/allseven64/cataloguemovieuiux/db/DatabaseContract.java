package com.example.allseven64.cataloguemovieuiux.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_MOVIE = "movie_favorite";

    public static final class MovieColums implements BaseColumns {
        public static String POSTER = "posterPath";
        public static String TITLE = "title";
        public static String RELEASE = "releaseDate";
        public static String POPULARITY = "popularity";
        public static String OVERVIEW = "overview";
        public static String VOTE = "vote_average";

    }

    public static final String AUTHORITY = "com.example.allseven64.cataloguemovieuiux";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public  static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

}
