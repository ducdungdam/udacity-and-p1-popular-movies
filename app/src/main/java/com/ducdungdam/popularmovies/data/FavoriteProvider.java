package com.ducdungdam.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.ducdungdam.popularmovies.data.FavoriteContract.FavoriteEntry;

public class FavoriteProvider extends ContentProvider {

  public final static int FAVORITES = 100;
  public final static int FAVORITE_WITH_ID = 101;

  private static final UriMatcher sUriMatcher = buildUriMatcher();

  public static UriMatcher buildUriMatcher() {
    UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    uriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.PATH_FAVORITES, FAVORITES);
    uriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.PATH_FAVORITES + "/#",
        FAVORITE_WITH_ID);

    return uriMatcher;
  }

  private FavoriteDbHelper mDbHelper;


  @Override
  public boolean onCreate() {
    Context context = getContext();
    mDbHelper = new FavoriteDbHelper(context);

    return true;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
      @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    final SQLiteDatabase db = mDbHelper.getReadableDatabase();
    int match = sUriMatcher.match(uri);

    Cursor retCursor;
    switch (match) {
      case FAVORITES:
        retCursor = db.query(
            FavoriteEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder);
        break;
      case FAVORITE_WITH_ID:
        String movieId = uri.getPathSegments().get(1);
        String mSelection = FavoriteEntry.COLUMN_MOVIE_ID + "=?";
        String[] mSelectionArgs = new String[]{movieId};

        retCursor = db.query(
            FavoriteEntry.TABLE_NAME,
            projection,
            mSelection,
            mSelectionArgs,
            null,
            null,
            sortOrder);
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    if(getContext() != null) {
      retCursor.setNotificationUri(getContext().getContentResolver(), uri);
    }
    return retCursor;
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
    final SQLiteDatabase db = mDbHelper.getWritableDatabase();
    int match = sUriMatcher.match(uri);

    Uri returnUri;
    switch (match) {
      case FAVORITES:
        long id = db.insert(FavoriteEntry.TABLE_NAME, null, contentValues);
        if (id > 0) {
          returnUri = ContentUris.withAppendedId(FavoriteEntry.CONTENT_URI, id);
        } else {
          throw new SQLException("Failed to insert row into " + uri);
        }
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    if(getContext() != null) {
      getContext().getContentResolver().notifyChange(uri, null);
    }
    return returnUri;
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
    final SQLiteDatabase db = mDbHelper.getWritableDatabase();
    int match = sUriMatcher.match(uri);

    int favoritesDeleted;
    switch (match) {
      case FAVORITE_WITH_ID:
        String movieId = uri.getPathSegments().get(1);
        favoritesDeleted = db
            .delete(FavoriteEntry.TABLE_NAME, FavoriteEntry.COLUMN_MOVIE_ID + "= ?",
                new String[]{movieId});
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    if (favoritesDeleted != 0 && getContext() != null) {
      getContext().getContentResolver().notifyChange(uri, null);
    }

    return favoritesDeleted;
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s,
      @Nullable String[] strings) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
