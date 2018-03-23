package com.ducdungdam.popularmovies.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by damdu on 09.03.2018.
 *
 * POJO of Movie for retrieving Data from the API
 */

public class MovieList {

  @SerializedName("page")
  @Expose
  private Integer page;
  @SerializedName("total_results")
  @Expose
  private Integer totalResults;
  @SerializedName("total_pages")
  @Expose
  private Integer totalPages;
  @SerializedName("results")
  @Expose
  private List<Movie> movieList = null;

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getTotalResults() {
    return totalResults;
  }

  public void setTotalResults(Integer totalResults) {
    this.totalResults = totalResults;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public List<Movie> getMovieList() {
    return movieList;
  }

  public void setMovieList(List<Movie> movieList) {
    this.movieList = movieList;
  }

}