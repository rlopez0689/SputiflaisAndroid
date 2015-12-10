package me.rodrigo.sputiflais.io;


import me.rodrigo.sputiflais.io.model.AlbumResponse;
import me.rodrigo.sputiflais.io.model.ArtistResponse;
import retrofit.http.GET;
import retrofit.http.Query;

import rx.Observable;

public interface SpotifyApiService {

    @GET(ApiConstants.URL__ARTISTS)
    Observable<ArtistResponse> getArtists(@Query(ApiConstants.PARAM_API_QUERY) String query);

    @GET(ApiConstants.URL__ALBUMS)
    Observable<AlbumResponse> getAlbums(@Query(ApiConstants.PARAM_API_QUERY) String query);
}
