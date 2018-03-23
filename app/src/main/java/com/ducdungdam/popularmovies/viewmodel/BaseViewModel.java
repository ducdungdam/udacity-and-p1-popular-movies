package com.ducdungdam.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;

/**
 * Created by damdu on 05.03.2018.
 *
 * BaseViewModel implements States for all ViewModels
 */

public class BaseViewModel extends AndroidViewModel {

  public ObservableField<State> state;

  public enum State {
    DEFAULT,
    LOADING,
    NO_NETWORK
  }

  BaseViewModel(Application app) {
    super(app);
    state = new ObservableField<>();
  }
}