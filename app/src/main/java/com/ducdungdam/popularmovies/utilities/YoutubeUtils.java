package com.ducdungdam.popularmovies.utilities;

/**
 * Created by ducdungdam on 23.03.18.
 *
 * Class to build all Youtube related URL's
 */

public final class YoutubeUtils {

  private static final String BASE_VIDEO_URL = "https://www.youtube.com/watch?v=";

  private static final String BASE_IMAGE_URL = "https://img.youtube.com/vi/";
  private static final String QUALITY_DEFAULT = "/0.jpg";
  private static final String QUALITY_HQ = "/hqdefault.jpg";
  private static final String QUALITY_MQ = "/mqdefault.jpg";

  /**
   * Return path of default Thumbnail
   *
   * @param key Youtube Key
   * @return URL of Youtube Video
   */
  public static String getVideoUrl(String key) {
    return BASE_VIDEO_URL + key;
  }

  /**
   * Return path of default Thumbnail
   *
   * @param key Youtube Key
   * @return URL of Youtube Thumbnail
   */
  public static String getThumbnail(String key) {
    return BASE_IMAGE_URL + key + QUALITY_DEFAULT;
  }

  /**
   * Return path of HQ Thumbnail
   *
   * @param key Youtube Key
   * @return URL of Youtube Thumbnail
   */
  public static String getThumbnailHQ(String key) {
    return BASE_IMAGE_URL + key + QUALITY_HQ;
  }

  /**
   * Return path of MQ Thumbnail
   *
   * @param key Youtube Key
   * @return URL of Youtube Thumbnail
   */
  public static String getThumbnailMQ(String key) {
    return BASE_IMAGE_URL + key + QUALITY_MQ;
  }

}
