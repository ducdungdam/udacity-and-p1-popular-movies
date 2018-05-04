package com.ducdungdam.popularmovies.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ducdungdam.popularmovies.R;
import com.ducdungdam.popularmovies.databinding.ViewMovieListItemBinding;
import com.ducdungdam.popularmovies.model.Movie;
import java.util.List;

/**
 * Created by damdu on 06.03.2018.
 *
 * Adapter for MovieList
 */

public class MovieAdapter extends RecyclerView.Adapter<ViewHolder> {

  private List<Movie> movieList;
  private OnClickListener clickListener;

  public MovieAdapter(List<Movie> movieList) {
    this.movieList = movieList;
  }

  @Override
  @NonNull
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new MovieViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.view_movie_list_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Movie movie = movieList.get(position);
    ((MovieViewHolder) holder).bind(movie);
  }

  @Override
  public int getItemCount() {
    return movieList.size();
  }

  public void setMovieList(List<Movie> l) {
    movieList = l;
    notifyDataSetChanged();
  }

  class MovieViewHolder extends ViewHolder implements View.OnClickListener {

    private ViewMovieListItemBinding rootView;

    MovieViewHolder(View itemView) {
      super(itemView);
      rootView = DataBindingUtil.bind(itemView);
    }

    void bind(Movie movie) {
      ViewCompat.setTransitionName(rootView.ivMoviePoster, movie.getId().toString());
      rootView.setMovie(movie);
      rootView.getRoot().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      clickListener.onClick(rootView.ivMoviePoster, rootView.getMovie());
    }
  }


  public void setOnClickListener(@Nullable OnClickListener l) {
    clickListener = l;
  }

  /**
   * Interface definition for a callback to be invoked when a view is clicked.
   */
  public interface OnClickListener {

    void onClick(View view, Movie movie);
  }
}
