package com.shofiq.tvshowapp.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shofiq.tvshowapp.network.ApiClient;
import com.shofiq.tvshowapp.responses.TvShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTvShowRepository {

    public LiveData<TvShowResponse> getMostPopularTvShows(int page){
        MutableLiveData<TvShowResponse> data=new MutableLiveData<>();
        ApiClient.getInstance().getApi().getPopularTvShows(page).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call,@NonNull Response<TvShowResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call,@NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
