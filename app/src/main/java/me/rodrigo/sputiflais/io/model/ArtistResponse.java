package me.rodrigo.sputiflais.io.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import me.rodrigo.sputiflais.domain.Artist;

public class ArtistResponse {

    @SerializedName(JsonKeys.ARTISTS_RESPONSE)
    private MainResponse mainResponse;

    public ArrayList<Artist> getArtists(){
        return  mainResponse.artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        mainResponse.artists = artists;
    }

    public class MainResponse{
        private ArrayList<Artist> artists;
    }

}
