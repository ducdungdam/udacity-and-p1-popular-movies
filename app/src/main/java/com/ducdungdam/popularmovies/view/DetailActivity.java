package com.ducdungdam.popularmovies.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import com.ducdungdam.popularmovies.R;
import com.ducdungdam.popularmovies.adapter.ReviewAdapter;
import com.ducdungdam.popularmovies.adapter.TrailerAdapter;
import com.ducdungdam.popularmovies.data.MovieRepository;
import com.ducdungdam.popularmovies.databinding.ActivityDetailBinding;
import com.ducdungdam.popularmovies.model.Movie;
import com.ducdungdam.popularmovies.model.ReviewList;
import com.ducdungdam.popularmovies.model.Trailer;
import com.ducdungdam.popularmovies.model.TrailerList;
import com.ducdungdam.popularmovies.viewmodel.DetailViewModel;
import com.ducdungdam.popularmovies.widget.ReviewItemDecoration;
import com.ducdungdam.popularmovies.widget.TrailerItemDecoration;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by ducdungdam on 20.03.18.
 *
 * Activity to shows Detail of a selected Movie
 */

public class DetailActivity extends AppCompatActivity {

  public static final String EXTRA_MOVIE_ID = "extra_movie_id";
  public static final String EXTRA_MOVIE_POSTER_TRANSITION_NAME = "extra_movie_poster_transition_name";

  private ActivityDetailBinding rootView;
  private DetailViewModel model;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    rootView = DataBindingUtil.setContentView(this, R.layout.activity_detail);

    Intent intent = getIntent();
    if (intent == null) {
      closeOnError();
      return;
    }

    final int movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1);
    if (movieId == -1) {
      closeOnError();
      return;
    }

    if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
      postponeEnterTransition(); // needs to be set to avoid image load flickering
      String imageTransitionName = intent.getStringExtra(EXTRA_MOVIE_POSTER_TRANSITION_NAME);
      rootView.ivMoviePoster.setTransitionName(imageTransitionName);
    }

    setSupportActionBar(rootView.toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setDisplayShowTitleEnabled(false);
    }

    model = ViewModelProviders.of(this).get(DetailViewModel.class);
    rootView.setViewModel(model);

    if (model.getMovieId().getValue() == null || model.getMovieId().getValue() != movieId) {
      model.setMovieId(movieId);
    }

    model.getMovie().observe(this, new Observer<Movie>() {
      @Override
      public void onChanged(@Nullable final Movie movie) {
        if (movie != null) {
          rootView.setMovie(movie);
        }
      }
    });

    model.getTrailerList().observe(this, new Observer<TrailerList>() {
      @Override
      public void onChanged(@Nullable final TrailerList trailerList) {
        if (trailerList != null) {
          TrailerAdapter trailerAdapter = (TrailerAdapter) rootView.rvTrailer.getAdapter();
          if (trailerAdapter != null) {
            trailerAdapter.setTrailerList(trailerList.getTrailerList());
          } else {
            TrailerAdapter adapter = new TrailerAdapter(trailerList.getTrailerList());
            adapter.setOnClickListener(new TrailerAdapter.OnClickListener() {
              @Override
              public void onClick(View view, Trailer trailer) {
                String url = MovieRepository.getTrailerUrl(trailer);
                if (url != null && !url.isEmpty()) {
                  Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                  startActivity(browserIntent);
                } else {
                  AlertDialog alertDialog = new AlertDialog.Builder(
                      new ContextThemeWrapper(DetailActivity.this, R.style.AlertDialogTheme))
                      .create();
                  alertDialog.setMessage(getString(R.string.error_source_message));
                  alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                      new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                          dialog.dismiss();
                        }
                      });
                  alertDialog.show();
                }
              }
            });
            rootView.rvTrailer.setAdapter(adapter);
            rootView.rvTrailer
                .addItemDecoration(new TrailerItemDecoration(DetailActivity.this));
          }
        }
      }
    });

    rootView.rvReview.setNestedScrollingEnabled(
        false); //Avoid scroll collusion with CoordinatorLayout and NestedScroll in layout
    model.getReviewList().
        observe(this, new Observer<ReviewList>() {
          @Override
          public void onChanged(@Nullable final ReviewList reviewList) {
            if (reviewList != null) {
              rootView.tvReviewLabel.setText(String.format(getString(R.string.movie_review_label),
                  reviewList.getReviewList().size()));
              if (reviewList.getReviewList().size() == 0) {
                rootView.rvReview.setVisibility(View.GONE);
                rootView.tvReviewEmpty.setVisibility(View.VISIBLE);
              } else {
                rootView.rvReview.setVisibility(View.VISIBLE);
                rootView.tvReviewEmpty.setVisibility(View.GONE);
                ReviewAdapter reviewAdapter = (ReviewAdapter) rootView.rvReview.getAdapter();
                if (reviewAdapter != null) {
                  reviewAdapter.setReviewList(reviewList.getReviewList());
                } else {
                  ReviewAdapter adapter = new ReviewAdapter(reviewList.getReviewList());
                  rootView.rvReview.setAdapter(adapter);
                  rootView.rvReview
                      .addItemDecoration(new ReviewItemDecoration(DetailActivity.this));
                }
              }
            }
          }
        });

    rootView.fabFavorite.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Movie m = model.getMovie().getValue();
        boolean b = model.isFavorite.get();
        if (m != null) {
          if (!b) {
            MovieRepository.addFavorite(DetailActivity.this, m);
            model.isFavorite.set(true);
          } else {
            MovieRepository.removeFavorite(DetailActivity.this, m);
            model.isFavorite.set(false);
          }
        }
      }
    });
  }

  private void closeOnError() {
    finish();
    Toast.makeText(this, "Movie Data not available", Toast.LENGTH_SHORT).show();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        supportFinishAfterTransition();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * Loads an Image by a given String Source to an ImageView in DetailsActivity.
   * It also can be used for DataBinding.
   */
  @BindingAdapter("detailSetSharedTransitionMovieImage")
  public static void setMovieImage(ImageView view, String url) {
    String imagePath = MovieRepository.getImageUrl(url);
    final DetailActivity activity = (DetailActivity) view.getContext();
    Picasso.with(activity)
        .load(imagePath)
        .noFade()
        .into(view, new Callback() {
          @Override
          public void onSuccess() {
            if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
              activity.startPostponedEnterTransition();
            }
          }

          @Override
          public void onError() {
            if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
              activity.startPostponedEnterTransition();
            }
          }
        });
  }
}
