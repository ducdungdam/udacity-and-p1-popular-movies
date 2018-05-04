package com.ducdungdam.popularmovies.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import com.ducdungdam.popularmovies.R;
import com.ducdungdam.popularmovies.adapter.MovieAdapter;
import com.ducdungdam.popularmovies.data.PopularMoviesPreferences;
import com.ducdungdam.popularmovies.databinding.ActivityMainBinding;
import com.ducdungdam.popularmovies.model.Movie;
import com.ducdungdam.popularmovies.utilities.NetworkUtils;
import com.ducdungdam.popularmovies.viewmodel.MainViewModel;
import com.ducdungdam.popularmovies.viewmodel.BaseViewModel.State;
import com.ducdungdam.popularmovies.widget.MovieItemDecoration;
import com.ducdungdam.popularmovies.widget.SortDialog;
import com.ducdungdam.popularmovies.widget.SortDialog.OnClickListener;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding rootView;

  private MainViewModel model;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    rootView = DataBindingUtil.setContentView(this, R.layout.activity_main);

    setSupportActionBar(rootView.toolbar);

    final int numColumns;
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      numColumns = 2;
    } else {
      numColumns = 3;
    }

    //Setting Data
    model = ViewModelProviders.of(this).get(MainViewModel.class);
    rootView.setViewModel(model);

    model.getMovieList().observe(this, new Observer<List<Movie>>() {
      @Override
      public void onChanged(@Nullable final List<Movie> movieList) {
        if (movieList == null || movieList.size() == 0) {
          model.state.set(State.MESSAGE);
        } else {
          if (rootView.rvMovieList.getAdapter() == null) {
            rootView.rvMovieList
                .setLayoutManager(new GridLayoutManager(MainActivity.this, numColumns));
            MovieAdapter movieAdapter = new MovieAdapter(movieList);
            movieAdapter.setOnClickListener(new MovieAdapter.OnClickListener() {
              @Override
              public void onClick(View view, Movie movie) {
                startDetailActivity(view, movie.getId());
              }
            });
            rootView.rvMovieList.setAdapter(movieAdapter);
            rootView.rvMovieList
                .addItemDecoration(new MovieItemDecoration(MainActivity.this, numColumns));
          } else {
            MovieAdapter adapter = (MovieAdapter) rootView.rvMovieList.getAdapter();
            adapter.setMovieList(movieList);
          }
        }
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_toolbar, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.menu_sort) {
      final SortDialog sortDialog = new SortDialog();
      sortDialog.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(String sortType) {
          if (!NetworkUtils.hasNetwork(MainActivity.this)) {
            model.state.set(State.NO_NETWORK);
            sortDialog.dismiss();
            return;
          }

          String currentSortType = PopularMoviesPreferences.getSortType(getApplicationContext());
          if (!currentSortType.equals(sortType)) {
            PopularMoviesPreferences.setSortType(getApplicationContext(), sortType);
            sortDialog.dismiss();
            model.setSortType(sortType);
          }
        }
      });
      sortDialog.show(getSupportFragmentManager(), SortDialog.TAG);
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onResume() {
    super.onResume();
    String sortType = PopularMoviesPreferences.getSortType(this);
    if (model.state.get() == State.NO_NETWORK && NetworkUtils.hasNetwork(this)) {
      PopularMoviesPreferences.setSortType(this, sortType);
      model.setSortType(sortType);
    }
  }

  private void startDetailActivity(View v, int movieId) {
    Activity activity = (Activity) v.getContext();

    Intent intent = new Intent(this, DetailActivity.class);
    intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movieId);
    intent.putExtra(DetailActivity.EXTRA_MOVIE_POSTER_TRANSITION_NAME,
        ViewCompat.getTransitionName(v));

    ActivityOptionsCompat options;
    Pair<View, String> p0 = Pair.create(v, ViewCompat.getTransitionName(v));
    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
      options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p0);
    } else {
      // Pair for Navigation Bar needs to be set, otherwise View will overlap Navigation Bar on transition
      View navigationBar = activity.findViewById(android.R.id.navigationBarBackground);
      Pair<View, String> p1 = Pair
          .create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);

      // Pair for Status Bar needs to be set, otherwise View will overlap Status Bar on transition
      View statusBar = activity.findViewById(android.R.id.statusBarBackground);
      Pair<View, String> p2 = Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);

      options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p0, p1, p2);
    }

    startActivity(intent, options.toBundle());
  }
}
