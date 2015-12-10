package me.rodrigo.sputiflais.io.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import me.rodrigo.sputiflais.domain.Album;

public class AlbumResponse {

    @SerializedName(JsonKeys.ALBUMS_RESPONSE)
    private MainResponse mainResponse;

    public ArrayList<Album> getAlbums(){
        return  mainResponse.albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        mainResponse.albums = albums;
    }

    public class MainResponse{
        private ArrayList<Album> albums;
    }

}
