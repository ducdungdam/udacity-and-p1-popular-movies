package com.ducdungdam.popularmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ducdungdam on 23.03.18.
 *
 * Class handles all Network connectivity
 */

public final class NetworkUtils {

  /**
   * Checks whether Device has access to the internet
   *
   * @param context Context to retrieve System Service
   * @return Boolean, whether Device is connected to the internet
   */
  public static boolean hasNetwork(Context context) {
    ConnectivityManager cm =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = cm.getActiveNetworkInfo();
    return netInfo != null && netInfo.isConnectedOrConnecting();
  }
}
