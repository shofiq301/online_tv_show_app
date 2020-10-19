package com.shofiq.tvshowapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.shofiq.tvshowapp.repositories.TvShowDetailsRepository;
import com.shofiq.tvshowapp.responses.TvShowDetailsResponse;

public class TvShowDetailsViewModel extends ViewModel {
    private TvShowDetailsRepository tvShowDetailsRepository;

    public TvShowDetailsViewModel() {
        this.tvShowDetailsRepository = new TvShowDetailsRepository();
    }

    public LiveData<TvShowDetailsResponse> getTvShowDewtails(String showId){
        return tvShowDetailsRepository.getTvShowDetailsLiveData(showId);
    }
}
