package com.shofiq.tvshowapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.shofiq.tvshowapp.repositories.MostPopularTvShowRepository;
import com.shofiq.tvshowapp.responses.TvShowResponse;

public class MostPopularTvShowsViewModel extends ViewModel {
    private final MostPopularTvShowRepository mostPopularTvShowRepository;

    public MostPopularTvShowsViewModel() {
        this.mostPopularTvShowRepository = new MostPopularTvShowRepository();
    }

    public LiveData<TvShowResponse> getMostPopularTvShows(int page){
        return mostPopularTvShowRepository.getMostPopularTvShows(page);
    }
}
