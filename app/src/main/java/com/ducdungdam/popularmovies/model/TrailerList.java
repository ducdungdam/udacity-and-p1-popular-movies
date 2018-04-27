package com.ducdungdam.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TrailerList {
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("results")
  @Expose
  private List<Trailer> trailerList = null;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public List<Trailer> getTrailerList() {
    return trailerList ;
  }

  public void setTrailerList(List<Trailer> trailerList) {
    this.trailerList = trailerList ;
  }
}
