package com.shofiq.tvshowapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shofiq.tvshowapp.R;
import com.shofiq.tvshowapp.databinding.ItemContainerTvShowBinding;
import com.shofiq.tvshowapp.listeners.TvShowListener;
import com.shofiq.tvshowapp.models.TvShow;

import java.util.List;

public class RecyclerTvShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TvShow> tvShows;
    private LayoutInflater layoutInflater;
    private TvShowListener listener;

    public RecyclerTvShowAdapter(List<TvShow> tvShows, TvShowListener tvShowListener) {
        this.tvShows = tvShows;
        this.listener=tvShowListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater==null)
            layoutInflater=LayoutInflater.from(parent.getContext());
        ItemContainerTvShowBinding binding= DataBindingUtil.inflate(layoutInflater, R.layout.item_container_tv_show,parent,false);
        return new MostPopularTvShowItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MostPopularTvShowItemViewHolder viewHolder=(MostPopularTvShowItemViewHolder)holder;
        viewHolder.bindTvShow(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public class MostPopularTvShowItemViewHolder extends RecyclerView.ViewHolder{
        ItemContainerTvShowBinding binding;
        public MostPopularTvShowItemViewHolder(@NonNull ItemContainerTvShowBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
        public void bindTvShow(TvShow tvShow){
            binding.setTvItem(tvShow);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onTvShowClicked(tvShow);
                }
            });
        }
    }
}
