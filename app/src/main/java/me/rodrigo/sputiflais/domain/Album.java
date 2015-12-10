package me.rodrigo.sputiflais.domain;

import com.google.gson.JsonArray;

public class Album {

    private String name;

    private String image;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                '}';
    }

    public Album extractUrlsFromImagesArray(JsonArray imagesJson){
        if(imagesJson.size()>0)
            setImage(imagesJson.get(0).getAsJsonObject().get("url").getAsString());
        return this;
    }


}
