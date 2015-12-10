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

import me.rodrigo.sputiflais.domain.Artist;
import me.rodrigo.sputiflais.io.model.ArtistResponse;

public class ArtistResponseDeserializer implements JsonDeserializer<ArtistResponse> {

    @Override
    public ArtistResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        ArtistResponse response = gson.fromJson(json, ArtistResponse.class);

        //The artists array will be parsed manually due nested elements
        JsonObject artistsResponseData = json.getAsJsonObject().getAsJsonObject("artists");

        JsonArray artistsArray = artistsResponseData.getAsJsonArray("items");
        response.setArtists(extractArtistsFromJsonArray(artistsArray));

        return response;
    }

    private ArrayList<Artist> extractArtistsFromJsonArray(JsonArray artistsArray) {
        ArrayList<Artist> artists = new ArrayList<>();

        for (int i = 0; i < artistsArray.size(); i++) {
            JsonObject artistData = artistsArray.get(i).getAsJsonObject();

            Artist currentArtist = new Artist();
            currentArtist.setName(artistData.get("name").getAsString());
            currentArtist.extractUrlsFromImagesArray(artistData.get("images").getAsJsonArray());
            artists.add(currentArtist);
        }

        return artists;
    }
}
