package com.ducdungdam.popularmovies.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ducdungdam on 23.03.18.
 *
 * Class to formats all inputs to representative output for App usage
 */

public final class FormatUtils {

  /**
   * Format Release Date String given by the API to a
   * representative String
   *
   * @param releaseDate unformatted String
   * @return formatted release Date String
   */
  public static String formatReleaseDate(String releaseDate) {
    try {
      Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(releaseDate);
      return new SimpleDateFormat("dd. MMMM yyyy", Locale.ENGLISH).format(d);
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  /**
   * Format Vote Average given by the API to a representative
   * String
   *
   * @param voteAverage Number of the vote average
   * @return formatted vote average String
   */
  public static String formatVoteAverage(Double voteAverage) {
    try {
      return String.format("★ %s", voteAverage.toString());
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }
}
