package com.ducdungdam.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ReviewList {
  @SerializedName("id")
  @Expose
  private Integer id;
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
  private List<Review> reviewList = null;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

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

  public List<Review> getReviewList() {
    return reviewList;
  }

  public void setReviewList(List<Review> reviewList) {
    this.reviewList = reviewList;
  }
}
