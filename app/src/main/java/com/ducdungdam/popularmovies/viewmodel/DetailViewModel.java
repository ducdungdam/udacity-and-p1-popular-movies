package com.ducdungdam.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.ducdungdam.popularmovies.data.MovieRepository;
import com.ducdungdam.popularmovies.data.MovieRepository.LoadingListener;
import com.ducdungdam.popularmovies.model.Movie;
import com.ducdungdam.popularmovies.utilities.NetworkUtils;

/**
 * Created by ducdungdam on 20.03.18.
 *
 * ViewModel for {@link com.ducdungdam.popularmovies.view.DetailActivity}
 */

public class DetailViewModel extends BaseViewModel {

  private LiveData<Movie> movie;

  DetailViewModel(Application app) {
    super(app);
    movie = new MutableLiveData<>();
  }

  public LiveData<Movie> getMovie(int movieId) {
    if (!NetworkUtils.hasNetwork(getApplication().getBaseContext())) {
      state.set(State.NO_NETWORK);
      return movie;
    }
    if (movie.getValue() == null) {
      state.set(State.LOADING);
      movie = MovieRepository
          .getMovie(getApplication().getApplicationContext(), movieId, new LoadingListener() {
            @Override
            public void onFinish() {
              state.set(State.DEFAULT);
            }
          });
    }
    return movie;
  }
}
