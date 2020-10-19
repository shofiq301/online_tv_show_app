package com.shofiq.tvshowapp.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shofiq.tvshowapp.R;
import com.shofiq.tvshowapp.adapters.RecyclerTvShowAdapter;
import com.shofiq.tvshowapp.databinding.ActivityMainBinding;
import com.shofiq.tvshowapp.listeners.TvShowListener;
import com.shofiq.tvshowapp.models.TvShow;
import com.shofiq.tvshowapp.responses.TvShowResponse;
import com.shofiq.tvshowapp.viewmodels.MostPopularTvShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TvShowListener {

    private ActivityMainBinding binding;
    private MostPopularTvShowsViewModel viewModel;
    private List<TvShow> tvShows;
    private RecyclerTvShowAdapter tvShowAdapter;
    private int currentPage=1;
    private int totalPages=1;
    private boolean isLoading=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUpViews();

        viewModel = new ViewModelProvider(this).get(MostPopularTvShowsViewModel.class);
        getMostPopularTvShows();
    }

    private void setUpViews() {
        binding.recyclerTvList.setHasFixedSize(true);
        tvShows = new ArrayList<>();
        tvShowAdapter = new RecyclerTvShowAdapter(tvShows, this);
        binding.recyclerTvList.setAdapter(tvShowAdapter);

        binding.recyclerTvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.recyclerTvList.canScrollVertically(1)){
                    if (currentPage<=totalPages && !isLoading){
                        currentPage+=1;
                        getMostPopularTvShows();
                    }
                }
            }
        });
    }

    private void getMostPopularTvShows() {
        toggleLoading();
        viewModel.getMostPopularTvShows(currentPage).observe(this, tvShowResponse -> {
           toggleLoading();
            if (tvShowResponse!=null){
                isLoading=false;
                totalPages=tvShowResponse.getPages();
                if (tvShowResponse.getTvShows()!=null){
                    int oldCount = tvShows.size();
                    tvShows.addAll(tvShowResponse.getTvShows());
                    tvShowAdapter.notifyItemRangeInserted(oldCount,tvShows.size());
                }
            }
        });
    }

    private void toggleLoading(){
        if (currentPage==1){
            binding.setIsLoading(binding.getIsLoading() == null || !binding.getIsLoading());
        }else {
            isLoading=true;
            binding.setIsLoadingMore(binding.getIsLoadingMore() == null || !binding.getIsLoadingMore());
        }
    }

    @Override
    public void onTvShowClicked(TvShow tvShow) {
        Intent intent=new Intent(getApplicationContext(), TvShowDetailsActivity.class);
        intent.putExtra("tvshowitem", new Gson().toJson(tvShow));
        startActivity(intent);
    }
}