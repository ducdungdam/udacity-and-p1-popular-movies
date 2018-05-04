package com.ducdungdam.popularmovies.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.databinding.ObservableField;
import com.ducdungdam.popularmovies.data.MovieRepository;
import com.ducdungdam.popularmovies.data.MovieRepository.LoadingListener;
import com.ducdungdam.popularmovies.model.Movie;
import com.ducdungdam.popularmovies.model.ReviewList;
import com.ducdungdam.popularmovies.model.TrailerList;
import com.ducdungdam.popularmovies.utilities.NetworkUtils;

/**
 * Created by ducdungdam on 20.03.18.
 *
 * ViewModel for {@link com.ducdungdam.popularmovies.view.DetailActivity}
 */

public class DetailViewModel extends BaseViewModel {

  private MutableLiveData<Integer> movieId;

  private LiveData<Movie> movie;
  private LiveData<TrailerList> trailerList;
  private LiveData<ReviewList> reviewList;
  public ObservableField<Boolean> isFavorite;


  DetailViewModel(Application app) {
    super(app);
    movieId = new MutableLiveData<>();

    movie = new MutableLiveData<>();
    trailerList = new MutableLiveData<>();
    reviewList = new MutableLiveData<>();
    isFavorite = new ObservableField<>();

    movie = Transformations.switchMap(movieId, new Function<Integer, LiveData<Movie>>() {
      @Override
      public LiveData<Movie> apply(Integer movieId) {
        if (!NetworkUtils.hasNetwork(getApplication().getBaseContext())) {
          state.set(State.NO_NETWORK);
          return movie;
        }
        if (movie.getValue() == null) {
          state.set(State.LOADING);
          return MovieRepository
              .getMovie(getApplication().getApplicationContext(), movieId, new LoadingListener<Movie>() {
                @Override
                public void onFinish(Movie m) {
                  state.set(State.DEFAULT);
                }
              });
        }
        return movie;
      }
    });

    trailerList = Transformations.switchMap(movieId, new Function<Integer, LiveData<TrailerList>>() {
      @Override
      public LiveData<TrailerList> apply(Integer movieId) {
        return MovieRepository.getTrailers(getApplication().getApplicationContext(), movieId, null);
      }
    });

    reviewList = Transformations.switchMap(movieId, new Function<Integer, LiveData<ReviewList>>() {
      @Override
      public LiveData<ReviewList> apply(Integer movieId) {
        return MovieRepository.getReviews(getApplication().getApplicationContext(), movieId, null);
      }
    });
  }

  public void setMovieId(Integer movieId){
    this.isFavorite.set(MovieRepository.isFavorite(getApplication().getApplicationContext(), movieId));
    this.movieId.setValue(movieId);
  }

  public LiveData<Integer> getMovieId(){
    return this.movieId;
  }

  public LiveData<Movie> getMovie() {
    return movie;
  }

  public LiveData<TrailerList> getTrailerList() {
    return trailerList;
  }

  public LiveData<ReviewList> getReviewList() {
    return reviewList;
  }
}
