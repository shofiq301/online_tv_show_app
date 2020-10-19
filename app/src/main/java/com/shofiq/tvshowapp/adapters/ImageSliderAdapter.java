package com.shofiq.tvshowapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shofiq.tvshowapp.R;
import com.shofiq.tvshowapp.databinding.ItemContainerSliderImageBinding;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> sliderList;
    private LayoutInflater layoutInflater;

    public ImageSliderAdapter(List<String> sliderList) {
        this.sliderList = sliderList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater==null)
            layoutInflater=LayoutInflater.from(parent.getContext());
        ItemContainerSliderImageBinding binding= DataBindingUtil.inflate(layoutInflater, R.layout.item_container_slider_image,parent,false);
        return new ImageSliderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageSliderViewHolder viewHolder= (ImageSliderViewHolder)holder;
        viewHolder.bindingSliderImage(sliderList.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderList.size();
    }

    static public class ImageSliderViewHolder extends RecyclerView.ViewHolder{
        ItemContainerSliderImageBinding binding;
        public ImageSliderViewHolder(@NonNull ItemContainerSliderImageBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }

        public void bindingSliderImage(String imageUrl){
            binding.setImageURL(imageUrl);
        }
    }
}
