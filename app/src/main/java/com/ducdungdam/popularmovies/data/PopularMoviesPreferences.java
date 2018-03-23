package com.ducdungdam.popularmovies.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.ducdungdam.popularmovies.R;

/**
 * Created by damdu on 07.03.2018.
 *
 * Final class to handle all Preferences relevant actions within the App
 */

public final class PopularMoviesPreferences {

  /**
   * Returns the key of sort type which is currently set in Preferences. The default sort type is "popular"
   * which is set in {@link R.string#pref_sort_default}
   *
   * @param context Context used to access SharedPreferences
   * @return current key of Sort Type
   */
  public static String getSortType(Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

    String keySort = context.getString(R.string.pref_sort_key);
    String defaultSort = context.getString(R.string.pref_sort_default);

    return sp.getString(keySort, defaultSort);
  }

  /**
   * Returns the index of the current selected sort type in respect of the given sort array which is
   * defined in {@link R.array#sortValuesLabel} and {@link R.array#sortValuesKey}
   *
   * @param context Context used to access SharedPreferences
   * @return index of the current selected sort type
   */
  public static int getSortTypeIndex(Context context) {
    String sortType = getSortType(context);
    String[] sortValues = context.getResources().getStringArray(R.array.sortValuesKey);
    for(int i = 0; i < sortValues.length; i++){
      if (sortValues[i].equals(sortType)){
        return i;
      }
    }
    return -1;
  }

  public static void setSortType(Context context, String sortType) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sp.edit();

    String keySort = context.getString(R.string.pref_sort_key);

    editor.putString(keySort, sortType);
    editor.apply();
  }
}
