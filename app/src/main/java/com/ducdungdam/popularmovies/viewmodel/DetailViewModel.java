package com.ducdungdam.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.ducdungdam.popularmovies.data.MovieRepository;
import com.ducdungdam.popularmovies.model.Movie;

/**
 * Created by ducdungdam on 20.03.18.
 *
 * ViewModel for {@link com.ducdungdam.popularmovies.view.DetailActivity}
 */

public class DetailViewModel extends AndroidViewModel {

  private LiveData<Movie> movie;

  DetailViewModel(Application app) {
    super(app);
  }

  public LiveData<Movie> getMovie(int movieId) {
    if (movie == null) {
      movie = MovieRepository.getMovie(this.getApplication().getApplicationContext(), movieId);
    }
    return movie;
  }
}
