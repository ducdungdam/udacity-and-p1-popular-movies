package com.ducdungdam.popularmovies.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ducdungdam.popularmovies.R;
import com.ducdungdam.popularmovies.databinding.ViewReviewListItemBinding;
import com.ducdungdam.popularmovies.model.Review;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ViewHolder> {
  private List<Review> reviewList;

  public ReviewAdapter(List<Review> reviewList) {
    this.reviewList = reviewList;
  }

  @Override
  @NonNull public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ReviewViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.view_review_list_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Review review = reviewList.get(position);
    ((ReviewViewHolder) holder).bind(review);
  }

  @Override
  public int getItemCount() {
    return reviewList.size();
  }

  public void setReviewList(List<Review> l) {
    reviewList = l;
    notifyDataSetChanged();
  }

  class ReviewViewHolder extends ViewHolder {

    private ViewReviewListItemBinding rootView;

    ReviewViewHolder(View itemView) {
      super(itemView);
      rootView = DataBindingUtil.bind(itemView);
    }

    void bind(Review review) {
      rootView.setReview(review);
    }
  }
}
