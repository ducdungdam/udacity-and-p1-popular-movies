package com.ducdungdam.popularmovies.utilities;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.ducdungdam.popularmovies.data.MovieRepository;
import com.ducdungdam.popularmovies.model.Trailer;
import com.squareup.picasso.Picasso;

/**
 * Created by ducdungdam on 23.03.18.
 *
 * Class to all global DataBinding functions
 */

public class DataBindingUtils {

  /**
   * Loads an Image by a given String Source to an ImageView.
   *
   * @param view ImageView in which the  Image is load into
   * @param url relative Path to the image
   */
  @BindingAdapter("setMovieImage")
  public static void setMovieImage(ImageView view, String url) {
    String imagePath = MovieRepository.getImageUrl(url);
    Picasso.with(view.getContext())
        .load(imagePath)
        .error(android.R.color.transparent)
        .placeholder(android.R.color.transparent)
        .into(view);
  }

  /**
   * Loads an Trailer Thumbnail by a given Trailer Pojo to an ImageView.
   *
   * @param view ImageView in which the  Image is load into
   * @param trailer Trailer Pojo
   */
  @BindingAdapter("setTrailerThumbnail")
  public static void setTrailerThumbnail(ImageView view, Trailer trailer) {
    String imagePath = MovieRepository.getTrailerThumbnail(trailer);
    Picasso.with(view.getContext())
        .load(imagePath)
        .error(android.R.color.transparent)
        .placeholder(android.R.color.transparent)
        .into(view);
  }
}
