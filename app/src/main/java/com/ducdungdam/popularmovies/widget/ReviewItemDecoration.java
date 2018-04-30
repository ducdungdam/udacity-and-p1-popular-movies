package com.ducdungdam.popularmovies.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;
import com.ducdungdam.popularmovies.R;

/**
 * Created by ducdungdam on 20.03.18.
 *
 * ItemDecoration for GridView Movie Items
 */

public class ReviewItemDecoration extends ItemDecoration {

  private final int paddingVertical;
  private final Drawable divider;

  public ReviewItemDecoration(Context context) {
    this.paddingVertical = (int) context.getResources().getDimension(R.dimen.review_item_spacing);
    this.divider = context.getResources().getDrawable(R.drawable.item_decoration_divider);
  }


  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    // Set Padding
    outRect.set(0, paddingVertical, 0, paddingVertical);
  }

  @Override
  public void onDrawOver(Canvas c, RecyclerView parent, State state) {
    int left = parent.getPaddingLeft();
    int right = parent.getWidth() - parent.getPaddingRight();
    int childCount = parent.getChildCount();

    for (int i = 0; i < childCount - 1; i++) {
      View child = parent.getChildAt(i);

      int top = child.getBottom() + paddingVertical;
      int bottom = top + divider.getIntrinsicHeight();

      divider.setBounds(left, top, right, bottom);

      divider.draw(c);
    }
  }
}