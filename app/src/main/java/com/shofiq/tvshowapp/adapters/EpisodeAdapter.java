package com.shofiq.tvshowapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shofiq.tvshowapp.R;
import com.shofiq.tvshowapp.databinding.ItemContainerEpisodeBinding;
import com.shofiq.tvshowapp.models.Episode;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {

    private final List<Episode> episodeList;
    private LayoutInflater layoutInflater;

    public EpisodeAdapter(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerEpisodeBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_container_episode,parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindEpisode(episodeList.get(position));
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainerEpisodeBinding binding;
        public ViewHolder(@NonNull ItemContainerEpisodeBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bindEpisode(Episode episode){
            String title ="S";

            title = title.concat(String.valueOf(episode.getSeason())) + "E".concat(String.valueOf(episode.getEpisode()));
            binding.setTitle(title);
            binding.setName(episode.getName());
            binding.setAirDate(episode.getAirDate());


        }
    }
}
