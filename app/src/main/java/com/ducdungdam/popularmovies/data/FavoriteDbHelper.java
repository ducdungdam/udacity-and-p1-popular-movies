package com.ducdungdam.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ducdungdam.popularmovies.data.FavoriteContract.FavoriteEntry;

public class FavoriteDbHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "favorite.db";
  private static final int DATABASE_VERSION = 1;

  FavoriteDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    final String SQL_CREATE_FAVORITE_TABLE =
        "CREATE TABLE " + FavoriteEntry.TABLE_NAME + " (" +
            FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FavoriteEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
            FavoriteEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL," +
            " UNIQUE (" + FavoriteEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE);";

    sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteEntry.TABLE_NAME);
    sqLiteDatabase
        .execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + FavoriteEntry.TABLE_NAME + "'");
    onCreate(sqLiteDatabase);
  }
}
