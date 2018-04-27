package com.ducdungdam.popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Switch;
import com.ducdungdam.popularmovies.R;
import com.ducdungdam.popularmovies.model.Movie;
import com.ducdungdam.popularmovies.model.MovieList;
import com.ducdungdam.popularmovies.model.Review;
import com.ducdungdam.popularmovies.model.ReviewList;
import com.ducdungdam.popularmovies.model.Trailer;
import com.ducdungdam.popularmovies.model.TrailerList;
import com.ducdungdam.popularmovies.utilities.YoutubeUtils;
import java.util.List;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by damdu on 09.03.2018.
 *
 * Repository to handles all necessary data retrieving processes
 */

public class MovieRepository {

  private static final String TAG = MovieRepository.class.getSimpleName();

  private static final String BASE_URL = "http://api.themoviedb.org/3/";
  private static Retrofit retrofit = null;

  private static final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p";
  private static final String PATH_QUALITY = "w780"; //Possible qualities: "w92, w154, w185, w342, w500, w780, original"

  public interface TMBDbService {

    @GET("movie/popular")
    Call<MovieList> getMoviesPopular(@Query("api_key") String key);

    @GET("movie/top_rated")
    Call<MovieList> getMoviesTopRated(@Query("api_key") String key);

    @GET("movie/{movieId}")
    Call<Movie> getMovie(@Path("movieId") int movieId, @Query("api_key") String key);

    @GET("movie/{movieId}/videos")
    Call<TrailerList> getTrailers(@Path("movieId") int movieId, @Query("api_key") String key);

    @GET("movie/{movieId}/reviews")
    Call<ReviewList> getReviews(@Path("movieId") int movieId, @Query("api_key") String key);
  }


  private static Retrofit getRetrofitClient() {
    if (retrofit == null) {
      retrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .callbackExecutor(Executors.newSingleThreadExecutor())
          .build();
    }
    return retrofit;
  }

  /**
   * Returns a List of Movies using the TMDB API sorted by the given sort type set in
   * SharedPreferences.
   *
   * @param context Context used to access SharedPreferences
   * @return List of Movies sorted by sort criteria set in SharedPreferences
   */
  public static LiveData<List<Movie>> getSortedMovies(Context context, final LoadingListener l) {
    String sortType = PopularMoviesPreferences.getSortType(context);
    String tmdbApiKey = context.getResources().getString(R.string.TMDB_API_KEY);

    Call<MovieList> call = null;
    if (sortType.equals(context.getResources().getString(R.string.pref_sort_popular))) {
      call = getRetrofitClient().create(TMBDbService.class)
          .getMoviesPopular(tmdbApiKey);
    } else if (sortType.equals(context.getResources().getString(R.string.pref_sort_rated))) {
      call = getRetrofitClient().create(TMBDbService.class)
          .getMoviesTopRated(tmdbApiKey);
    }

    final MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();
    if (call != null) {
      call.enqueue(new Callback<MovieList>() {
        @Override
        public void onResponse(@NonNull Call<MovieList> call,
            @NonNull Response<MovieList> response) {
          MovieList movies = response.body();
          if (movies != null) {
            movieList.postValue(movies.getMovieList());
            l.onFinish();
          }
        }

        @Override
        public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
          Log.e(TAG, "Retrofit onFailure");
        }
      });
    }

    return movieList;
  }

  /**
   * Returns a Movie by a given ID.
   *
   * @param context Context used to access String Resource
   * @return Movie by given ID
   */
  public static LiveData<Movie> getMovie(Context context, int movieId, final LoadingListener l) {
    String tmdbApiKey = context.getResources().getString(R.string.TMDB_API_KEY);

    final MutableLiveData<Movie> movie = new MutableLiveData<>();

    Call<Movie> call = getRetrofitClient().create(TMBDbService.class)
        .getMovie(movieId, tmdbApiKey);

    call.enqueue(new Callback<Movie>() {
      @Override
      public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
        movie.postValue(response.body());
        l.onFinish();
      }

      @Override
      public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
        Log.e(TAG, "Retrofit onFailure");
      }
    });

    return movie;
  }

  /**
   * Returns a List of Trailers of a Movie by a given ID
   *
   * @param context Context used to access String Resource
   * @return List of Trailer by given ID
   */
  public static LiveData<TrailerList> getTrailers(Context context, int movieId,
      final LoadingListener l) {
    String tmdbApiKey = context.getResources().getString(R.string.TMDB_API_KEY);

    final MutableLiveData<TrailerList> trailers = new MutableLiveData<>();

    Call<TrailerList> call = getRetrofitClient().create(TMBDbService.class)
        .getTrailers(movieId, tmdbApiKey);

    call.enqueue(new Callback<TrailerList>() {
      @Override
      public void onResponse(@NonNull Call<TrailerList> call,
          @NonNull Response<TrailerList> response) {
        trailers.postValue(response.body());
        if (l != null) {
          l.onFinish();
        }
      }

      @Override
      public void onFailure(@NonNull Call<TrailerList> call, @NonNull Throwable t) {
        Log.e(TAG, "Retrofit onFailure");
      }
    });

    return trailers;
  }

  /**
   * Returns a List of Reviews of a Movie by a given ID
   *
   * @param context Context used to access String Resource
   * @return List of Reviews by given ID
   */
  public static LiveData<ReviewList> getReviews(Context context, int movieId,
      final LoadingListener l) {
    String tmdbApiKey = context.getResources().getString(R.string.TMDB_API_KEY);

    final MutableLiveData<ReviewList> reviews = new MutableLiveData<>();

    Call<ReviewList> call = getRetrofitClient().create(TMBDbService.class)
        .getReviews(movieId, tmdbApiKey);

    call.enqueue(new Callback<ReviewList>() {
      @Override
      public void onResponse(@NonNull Call<ReviewList> call,
          @NonNull Response<ReviewList> response) {
        reviews.postValue(response.body());
        if (l != null) {
          l.onFinish();
        }
      }

      @Override
      public void onFailure(@NonNull Call<ReviewList> call, @NonNull Throwable t) {
        Log.e(TAG, "Retrofit onFailure");
      }
    });

    return reviews;
  }

  /**
   * Returns absolute Image Url.
   *
   * @param url relative path of the image
   * @return absolute path of the image
   */
  public static String getImageUrl(String url) {
    Uri requestQueryUri = Uri.parse(BASE_URL_IMAGE).buildUpon()
        .appendPath(PATH_QUALITY)
        .build();
    return requestQueryUri.toString() + url;
  }


  static final String TRAILER_SOURCE_YOUTUBE = "YouTube";
  /**
   * Returns Trailer Thumbnail Image Url.
   *
   * @param trailer pojo of Trailer
   * @return absolute path of the trailer thumbnail
   */

  public static String getTrailerUrl(Trailer trailer) {
    switch (trailer.getSite()){
      case TRAILER_SOURCE_YOUTUBE:
        return YoutubeUtils.getVideoUrl(trailer.getKey());
      default:
        return null;
    }
  }
  /**
   * Returns Trailer Thumbnail Image Url.
   *
   * @param trailer pojo of Trailer
   * @return absolute path of the trailer thumbnail
   */

  public static String getTrailerThumbnail(Trailer trailer) {
    switch (trailer.getSite()){
      case TRAILER_SOURCE_YOUTUBE:
        return YoutubeUtils.getThumbnail(trailer.getKey());
      default:
        return null;
    }
  }

  /**
   * Interface to handle stuffs, after finish fetching Data.
   */
  public interface LoadingListener {

    void onFinish();
  }
}
