package com.shofiq.tvshowapp.responses;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shofiq.tvshowapp.models.TvShowDetails;

public class TvShowDetailsResponse {
    @SerializedName("tvShow")
    @Expose
    private TvShowDetails tvShow;

    public TvShowDetails getTvShow() {
        return tvShow;
    }

}
