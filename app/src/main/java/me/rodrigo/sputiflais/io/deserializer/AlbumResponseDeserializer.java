package me.rodrigo.sputiflais.io.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

import me.rodrigo.sputiflais.domain.Album;
import me.rodrigo.sputiflais.io.model.AlbumResponse;

public class AlbumResponseDeserializer implements JsonDeserializer<AlbumResponse> {

    @Override
    public AlbumResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        AlbumResponse response = gson.fromJson(json, AlbumResponse.class);

        //The artists array will be parsed manually due nested elements
        JsonObject artistsResponseData = json.getAsJsonObject().getAsJsonObject("albums");

        JsonArray artistsArray = artistsResponseData.getAsJsonArray("items");
        response.setAlbums(extractArtistsFromJsonArray(artistsArray));

        return response;
    }

    private ArrayList<Album> extractArtistsFromJsonArray(JsonArray artistsArray) {
        ArrayList<Album> albums = new ArrayList<>();

        for (int i = 0; i < artistsArray.size(); i++) {
            JsonObject artistData = artistsArray.get(i).getAsJsonObject();

            Album currentAlbum = new Album();
            currentAlbum.setName(artistData.get("name").getAsString());
            currentAlbum.extractUrlsFromImagesArray(artistData.get("images").getAsJsonArray());
            albums.add(currentAlbum);
        }

        return albums;
    }
}
