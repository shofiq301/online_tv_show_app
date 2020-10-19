package com.shofiq.tvshowapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shofiq.tvshowapp.R;
import com.shofiq.tvshowapp.adapters.ImageSliderAdapter;
import com.shofiq.tvshowapp.databinding.ActivityTvShowDetailsBinding;
import com.shofiq.tvshowapp.models.TvShow;
import com.shofiq.tvshowapp.responses.TvShowDetailsResponse;
import com.shofiq.tvshowapp.viewmodels.TvShowDetailsViewModel;

import java.util.List;

public class TvShowDetailsActivity extends AppCompatActivity {

    private ActivityTvShowDetailsBinding binding;
    private TvShowDetailsViewModel viewModel;
    private TvShow tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=new ViewModelProvider(this).get(TvShowDetailsViewModel.class);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_tv_show_details);

        tvShow=new Gson().fromJson(getIntent().getStringExtra("tvshowitem"), TvShow.class);
        getTvShowDetails();
    }

    private void getTvShowDetails() {
        binding.setIsLoading(true);
        viewModel.getTvShowDewtails(String.valueOf(tvShow.getId())).observe(this, tvShowDetailsResponse -> {
            binding.setIsLoading(false);
           if (tvShowDetailsResponse.getTvShow()!=null){
               if (tvShowDetailsResponse.getTvShow().getPictures()!=null){
                   loadSliders(tvShowDetailsResponse.getTvShow().getPictures());
               }
           }
        });
    }
    private void loadSliders(List<String> sliderImages){
        binding.sliderViewPager.setOffscreenPageLimit(1);
        binding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImages));
        binding.sliderViewPager.setVisibility(View.VISIBLE);
        binding.viewFadingEdge.setVisibility(View.VISIBLE);
        setUpSliderIndicator(sliderImages.size());
        binding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderPosition(position);
            }
        });
    }
    private void setUpSliderIndicator(int count){
        ImageView[] indicators=new ImageView[count];
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i=0;i<indicators.length;i++){
            indicators[i] =new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.background_slider_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            binding.layoutSliderIndecator.addView(indicators[i]);
        }
        binding.layoutSliderIndecator.setVisibility(View.VISIBLE);
    }

    private void setCurrentSliderPosition(int position){
        int childrenCount= binding.layoutSliderIndecator.getChildCount();
        for (int i=0; i<childrenCount; i++){
            ImageView imageView=(ImageView)binding.layoutSliderIndecator.getChildAt(i);
            if(i==position){
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.background_slider_indicator_active
                ));
            }
            else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.background_slider_indicator_inactive
                ));
            }
        }
    }
}