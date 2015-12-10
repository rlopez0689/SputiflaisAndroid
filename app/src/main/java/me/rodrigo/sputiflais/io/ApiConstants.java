package me.rodrigo.sputiflais.io;

public class ApiConstants {

    public static final String BASE_URL = "https://api.spotify.com/v1/search";

    public static final String PARAM_API_QUERY = "query";
    public static final String PARAM_ARTIST = "artist";
    public static final String PARAM_ALBUM = "album";
    public static final String PARAM_TRACK = "track";
    public static final String TYPE = "type";


    public static final String URL__ARTISTS = "/?"
            + TYPE + "=" + PARAM_ARTIST;
    public static final String URL__ALBUMS = "/?"
            + TYPE + "=" + PARAM_ALBUM;
    public static final String URL__TRACKS = "/?"
            + TYPE + "=" + PARAM_TRACK;
}
