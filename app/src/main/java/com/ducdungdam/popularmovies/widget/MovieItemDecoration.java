package com.ducdungdam.popularmovies.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;
import com.ducdungdam.popularmovies.R;

/**
 * Created by ducdungdam on 20.03.18.
 *
 * ItemDecoration for GridView Movie Items
 */

public class MovieItemDecoration extends ItemDecoration {

  private final int paddingHorizontal;
  private final int numColumns;

  public MovieItemDecoration(Context context, int numColumns) {
    this.paddingHorizontal = (int) context.getResources().getDimension(R.dimen.movie_item_spacing);
    this.numColumns = numColumns;
  }


  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {

    int position = parent.getChildAdapterPosition(view);

    // Set Padding
    int left = paddingHorizontal;
    int top = 0;
    int right = paddingHorizontal;

    if (position < numColumns) { // first Row
      top = paddingHorizontal;
    }

    if (position % numColumns == 0) { // all left columns
      right = paddingHorizontal / 2;
    } else if (position % numColumns == numColumns - 1) { // all right columns
      left = paddingHorizontal / 2;
    } else {
      right = paddingHorizontal / 2;
      left = paddingHorizontal / 2;
    }

    outRect.set(left, top, right, paddingHorizontal);
  }
}