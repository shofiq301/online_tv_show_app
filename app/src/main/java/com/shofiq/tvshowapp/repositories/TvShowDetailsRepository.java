package com.shofiq.tvshowapp.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shofiq.tvshowapp.network.ApiClient;
import com.shofiq.tvshowapp.responses.TvShowDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowDetailsRepository {
    public LiveData<TvShowDetailsResponse> getTvShowDetailsLiveData(String showId){
        MutableLiveData<TvShowDetailsResponse> serverTvShowData=new MutableLiveData<>();
        ApiClient.getInstance().getApi().getTvShowsDetailsResponse(showId).enqueue(new Callback<TvShowDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowDetailsResponse> call, @NonNull Response<TvShowDetailsResponse> response) {
                serverTvShowData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowDetailsResponse> call, @NonNull Throwable t) {
                serverTvShowData.setValue(null);
            }
        });
        return serverTvShowData;
    }
}
