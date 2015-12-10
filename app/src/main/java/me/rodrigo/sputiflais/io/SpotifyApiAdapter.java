package me.rodrigo.sputiflais.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.rodrigo.sputiflais.io.deserializer.AlbumResponseDeserializer;
import me.rodrigo.sputiflais.io.deserializer.ArtistResponseDeserializer;
import me.rodrigo.sputiflais.io.model.AlbumResponse;
import me.rodrigo.sputiflais.io.model.ArtistResponse;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Observable;

public class SpotifyApiAdapter {
    private static SpotifyApiService API_SERVICE;

    public static SpotifyApiService getApiService () {

        if(API_SERVICE == null){
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(ApiConstants.BASE_URL)
                    .setConverter(buildLastFmApiGsonConverter())
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .build();

            API_SERVICE = adapter.create(SpotifyApiService.class);
        }

        return API_SERVICE;

    }

    private static GsonConverter buildLastFmApiGsonConverter() {
        Gson gsonConf = new GsonBuilder()
                .registerTypeAdapter(ArtistResponse.class, new ArtistResponseDeserializer())
                .registerTypeAdapter(AlbumResponse.class, new AlbumResponseDeserializer())
                .create();

        return new GsonConverter(gsonConf);
    }

    public static Observable<ArtistResponse> getArtist(String query){
        return getApiService().getArtists(query);
    }

    public static Observable<AlbumResponse> getAlbum(String query){
        return getApiService().getAlbums(query);
    }

}
