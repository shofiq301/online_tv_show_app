package com.shofiq.tvshowapp.network;

import com.shofiq.tvshowapp.responses.TvShowDetailsResponse;
import com.shofiq.tvshowapp.responses.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("most-popular")
    Call<TvShowResponse> getPopularTvShows(@Query("page") int page);

    @GET("show-details")
    Call<TvShowDetailsResponse> getTvShowsDetailsResponse(@Query("q") String showId);
}
