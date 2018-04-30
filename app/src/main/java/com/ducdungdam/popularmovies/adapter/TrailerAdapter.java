package com.ducdungdam.popularmovies.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ducdungdam.popularmovies.R;
import com.ducdungdam.popularmovies.databinding.ViewTrailerListItemBinding;
import com.ducdungdam.popularmovies.model.Trailer;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<ViewHolder> {
  private List<Trailer> trailerList;
  private OnClickListener clickListener;

  public TrailerAdapter(List<Trailer> trailerList) {
    this.trailerList = trailerList;
  }

  @Override
  @NonNull public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new TrailerViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.view_trailer_list_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Trailer trailer = trailerList.get(position);
    ((TrailerViewHolder) holder).bind(trailer);
  }

  @Override
  public int getItemCount() {
    return trailerList.size();
  }

  public void setTrailerList(List<Trailer> l) {
    trailerList = l;
    notifyDataSetChanged();
  }

  class TrailerViewHolder extends ViewHolder implements View.OnClickListener {

    private ViewTrailerListItemBinding rootView;

    TrailerViewHolder(View itemView) {
      super(itemView);
      rootView = DataBindingUtil.bind(itemView);
    }

    void bind(Trailer trailer) {
      rootView.setTrailer(trailer);
      rootView.getRoot().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      clickListener.onClick(rootView.ivMovieTrailerThumbnail, rootView.getTrailer());
    }
  }


  public void setOnClickListener(@Nullable OnClickListener l) {
    clickListener = l;
  }

  /**
   * Interface definition for a callback to be invoked when a view is clicked.
   *
   */
  public interface OnClickListener {
    void onClick(View view, Trailer trailer);
  }
}
