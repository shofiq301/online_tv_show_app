package com.shofiq.tvshowapp.ui.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.shofiq.tvshowapp.R;
import com.shofiq.tvshowapp.adapters.EpisodeAdapter;
import com.shofiq.tvshowapp.adapters.ImageSliderAdapter;
import com.shofiq.tvshowapp.databinding.ActivityTvShowDetailsBinding;
import com.shofiq.tvshowapp.databinding.LayoutEpisodesBottomSheetBinding;
import com.shofiq.tvshowapp.models.TvShow;
import com.shofiq.tvshowapp.responses.TvShowDetailsResponse;
import com.shofiq.tvshowapp.viewmodels.TvShowDetailsViewModel;

import java.util.List;
import java.util.Locale;

public class TvShowDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityTvShowDetailsBinding binding;
    private TvShowDetailsViewModel viewModel;
    private TvShow tvShow;

    private BottomSheetDialog  episodeBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=new ViewModelProvider(this).get(TvShowDetailsViewModel.class);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_tv_show_details);

        tvShow=new Gson().fromJson(getIntent().getStringExtra("tvshowitem"), TvShow.class);
        getTvShowDetails();

        binding.imgBack.setOnClickListener(this);
    }

    private void getTvShowDetails() {
        binding.setIsLoading(true);
        viewModel.getTvShowDewtails(String.valueOf(tvShow.getId())).observe(this, tvShowDetailsResponse -> {
            binding.setIsLoading(false);
           if (tvShowDetailsResponse.getTvShow()!=null){
               if (tvShowDetailsResponse.getTvShow().getPictures()!=null){
                   loadSliders(tvShowDetailsResponse.getTvShow().getPictures());
               }
               binding.setTvShowImageUrl(tvShowDetailsResponse.getTvShow().getImagePath());
               binding.imgTvShowImage.setVisibility(View.VISIBLE);
               loadTvShowBasicDetails();
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                   binding.setTvShowDescription(String.valueOf(Html.fromHtml(tvShowDetailsResponse.getTvShow().getDescription(), Html.FROM_HTML_MODE_LEGACY)));
               }else {
                   binding.setTvShowDescription(String.valueOf(Html.fromHtml(tvShowDetailsResponse.getTvShow().getDescription())));
               }
               binding.txtTvShowDescription.setVisibility(View.VISIBLE);
               binding.txtTvShowDescriptionreadMore.setVisibility(View.VISIBLE);
               binding.txtTvShowDescriptionreadMore.setOnClickListener(this);
               binding.setRating(String.format(Locale.getDefault(),
                       "%.2f",
                       Double.parseDouble(tvShowDetailsResponse.getTvShow().getRating())));
               if (tvShowDetailsResponse.getTvShow().getGenres()!=null){
                   binding.setGenre(tvShowDetailsResponse.getTvShow().getGenres().get(0));
               }
               else {
                   binding.setGenre("N/A");
               }
               binding.setRuntime(tvShowDetailsResponse.getTvShow().getRuntime()+" Min");
               binding.layoutMisc.setVisibility(View.VISIBLE);
               binding.devider1.setVisibility(View.VISIBLE);
               binding.devider2.setVisibility(View.VISIBLE);
               binding.btnWebsite.setOnClickListener(view -> {
                   Intent intent=new Intent(Intent.ACTION_VIEW);
                   intent.setData(Uri.parse(tvShowDetailsResponse.getTvShow().getUrl()));
                   startActivity(intent);
               });
               binding.btnWebsite.setVisibility(View.VISIBLE);
               binding.btnEpisodes.setVisibility(View.VISIBLE);

               binding.btnEpisodes.setOnClickListener(view -> {
                   episodeBottomSheet = null;
                   showEpisodeBottomSheet(tvShowDetailsResponse);
               });

           }
        });
    }

    private void showEpisodeBottomSheet(TvShowDetailsResponse tvShowDetailsResponse) {
        if (episodeBottomSheet==null){
            episodeBottomSheet = new BottomSheetDialog(this);
            LayoutEpisodesBottomSheetBinding bottomSheetBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.layout_episodes_bottom_sheet, findViewById(R.id.episodesContainer), false);
            episodeBottomSheet.setContentView(bottomSheetBinding.getRoot());
            bottomSheetBinding.recyclerEpisodeList.setAdapter(new EpisodeAdapter(tvShowDetailsResponse.getTvShow().getEpisodes()));
            bottomSheetBinding.txtTitle.setText(String.format("Episode | %s", tvShow.getName()));
            bottomSheetBinding.imgClose.setOnClickListener(view -> episodeBottomSheet.dismiss());
            FrameLayout frameLayout= episodeBottomSheet.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (frameLayout!=null){
                BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
            episodeBottomSheet.show();
        }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.txtTvShowDescriptionreadMore:
                if (binding.txtTvShowDescriptionreadMore.getText().equals(getString(R.string.read_more))){
                    binding.txtTvShowDescription.setMaxLines(Integer.MAX_VALUE);
                    binding.txtTvShowDescription.setEllipsize(null);
                    binding.txtTvShowDescriptionreadMore.setText(R.string.show_less);
                }else {
                    binding.txtTvShowDescription.setMaxLines(4);
                    binding.txtTvShowDescription.setEllipsize(TextUtils.TruncateAt.END);
                    binding.txtTvShowDescriptionreadMore.setText(R.string.read_more);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    private void loadTvShowBasicDetails(){
        binding.setTvShowName(tvShow.getName());
        binding.setTvShowNetwork(tvShow.getNetwork());
        binding.setTvShowCountry(tvShow.getCountry());
        binding.setTvShowStatus(tvShow.getStatus());
        binding.setTvShowStartedDate(tvShow.getStartDate());

        binding.txtTvShowName.setVisibility(View.VISIBLE);
        binding.txtTvShowNetworkCountry.setVisibility(View.VISIBLE);
        binding.txtTvShowStatus.setVisibility(View.VISIBLE);
        binding.txtTvShowStartedate.setVisibility(View.VISIBLE);
    }
}