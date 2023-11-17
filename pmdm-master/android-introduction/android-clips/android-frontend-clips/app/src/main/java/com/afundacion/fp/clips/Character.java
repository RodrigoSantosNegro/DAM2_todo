package com.afundacion.fp.clips;

import org.json.JSONException;
import org.json.JSONObject;

public class Character {
    private String name;
    private String lastName;
    private String characterDescription;
    private String urlImage;

    public Character(JSONObject json) throws JSONException {
        this.name = json.getString("name");
        this.lastName = json.getString("surname");
        this.characterDescription = json.getString("description");
        this.urlImage = json.getString("imageUrl");
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCharacterDescription() {
        return characterDescription;
    }

    public String getUrlImage() {
        return urlImage;
    }
}
