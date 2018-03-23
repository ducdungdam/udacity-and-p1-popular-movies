package com.ducdungdam.popularmovies.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.databinding.ObservableField;
import com.ducdungdam.popularmovies.data.MovieRepository;
import com.ducdungdam.popularmovies.data.MovieRepository.LoadingListener;
import com.ducdungdam.popularmovies.data.PopularMoviesPreferences;
import com.ducdungdam.popularmovies.model.Movie;
import com.ducdungdam.popularmovies.utilities.NetworkUtils;
import java.util.List;

/**
 * Created by damdu on 05.03.2018.
 *
 * ViewModel for {@link com.ducdungdam.popularmovies.view.MainActivity}
 */

public class MainViewModel extends BaseViewModel {

  private LiveData<List<Movie>> movieList;
  private MutableLiveData<String> sortType;

  MainViewModel(Application app) {
    super(app);
    final Context context = app.getBaseContext();

    state = new ObservableField<>();
    sortType = new MutableLiveData<>();
    sortType.setValue(PopularMoviesPreferences.getSortType(context));

    movieList = Transformations.switchMap(sortType, new Function<String, LiveData<List<Movie>>>() {
      @Override
      public LiveData<List<Movie>> apply(String newSortType) {
        if (!NetworkUtils.hasNetwork(context)) {
          state.set(State.NO_NETWORK);
          return null;
        }
        state.set(State.LOADING);
        return MovieRepository.getSortedMovies(context, new LoadingListener() {
          @Override
          public void onFinish() {
            state.set(State.DEFAULT);
          }
        });
      }
    });
  }

  public LiveData<List<Movie>> getMovieList() {
    return movieList;
  }

  public MutableLiveData<String> getSortType() {
    return sortType;
  }

}