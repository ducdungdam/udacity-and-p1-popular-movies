package com.ducdungdam.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteContract {

  //declaring variables for content provider
  public static final String AUTHORITY = "com.ducdungdam.popularmovies";
  static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

  public static final String PATH_FAVORITES = "favorites";

  public static final class FavoriteEntry implements BaseColumns {

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES)
        .build();

    public static final String TABLE_NAME = "favorites";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_MOVIE_TITLE = "movie_title";
  }
}
