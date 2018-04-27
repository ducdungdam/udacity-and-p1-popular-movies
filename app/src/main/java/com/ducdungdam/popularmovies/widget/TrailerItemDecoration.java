package com.ducdungdam.popularmovies.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.util.Log;
import android.view.View;
import com.ducdungdam.popularmovies.R;

/**
 * Created by ducdungdam on 20.03.18.
 *
 * ItemDecoration for GridView Movie Items
 */

public class TrailerItemDecoration extends ItemDecoration {

  private final int paddingHorizontal;

  public TrailerItemDecoration(Context context) {
    this.paddingHorizontal = (int) context.getResources().getDimension(R.dimen.trailer_item_spacing);
  }


  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {

    int position = parent.getChildAdapterPosition(view);

    // Set Padding
    int left = paddingHorizontal;
    int right = paddingHorizontal;

    if (position == 0) { // first item
      left = 0;
    } else if (position == parent.getAdapter().getItemCount()-1) { // last item
      right = 0;
    }

    outRect.set(left, 0, right, 0);
  }
}