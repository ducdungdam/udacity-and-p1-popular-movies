package com.ducdungdam.popularmovies.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.databinding.ObservableField;
import com.ducdungdam.popularmovies.data.MovieRepository;
import com.ducdungdam.popularmovies.data.MovieRepository.LoadingListener;
import com.ducdungdam.popularmovies.data.PopularMoviesPreferences;
import com.ducdungdam.popularmovies.model.Movie;
import java.util.List;

/**
 * Created by damdu on 05.03.2018.
 *
 * ViewModel for {@link com.ducdungdam.popularmovies.view.MainActivity}
 */

public class MainViewModel extends AndroidViewModel {

  private LiveData<List<Movie>> movieList;
  private MutableLiveData<String> sortType;
  public ObservableField<Boolean> isLoading;

  MainViewModel(Application app) {
    super(app);
    final Context context = app.getBaseContext();

    isLoading = new ObservableField<>(); // Needs to trigger Loading Screen in {@link com.ducdungdam.popularmovies.R.layout#activity_main}
    sortType = new MutableLiveData<>();
    sortType.setValue(PopularMoviesPreferences.getSortType(context));

    movieList = Transformations.switchMap(sortType, new Function<String, LiveData<List<Movie>>>() {
      @Override
      public LiveData<List<Movie>> apply(String newSortType) {
        isLoading.set(true);
        return MovieRepository.getSortedMovies(context, new LoadingListener() {
          @Override
          public void onFinish() {
            isLoading.set(false);
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