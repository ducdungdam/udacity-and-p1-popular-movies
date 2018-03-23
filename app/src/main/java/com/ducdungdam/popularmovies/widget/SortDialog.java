package com.ducdungdam.popularmovies.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import com.ducdungdam.popularmovies.R;
import com.ducdungdam.popularmovies.data.PopularMoviesPreferences;

/**
 * Created by damdu on 16.03.2018.
 *
 * Sort Dialog which is used in {@link com.ducdungdam.popularmovies.view.MainActivity}
 */

public class SortDialog extends DialogFragment {

  public static final String TAG = SortDialog.class.getSimpleName();

  OnClickListener clickListener;

  @Override
  @NonNull public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(
        new ContextThemeWrapper(getActivity(), R.style.AlertDialogTheme));

    final Context context = getContext();
    if (context == null) {
      return builder
          .setTitle(R.string.error_title)
          .setMessage(R.string.error_message)
          .create();
    }

    final int checkedItemPos = PopularMoviesPreferences.getSortTypeIndex(context);
    final String[] sortValuesLabel = context.getResources().getStringArray(R.array.sortValuesLabel);
    final String[] sortValuesKey = context.getResources().getStringArray(R.array.sortValuesKey);

    return builder
        .setTitle(context.getResources().getString(R.string.pref_sort_label))
        .setSingleChoiceItems(sortValuesLabel, checkedItemPos,
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int pos) {
                clickListener.onClick(sortValuesKey[pos]);
              }
            })
        .create();
  }

  public void setOnClickListener(@Nullable OnClickListener l) {
    clickListener = l;
  }

  /**
   * Interface definition for a callback to be invoked when a view is clicked.
   */
  public interface OnClickListener {

    void onClick(String sortType);
  }
}
