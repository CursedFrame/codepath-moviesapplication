package com.example.moviesapplication.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Genre {
    Integer Id;
    String nameGenre;

    public Genre(){

    }

    public Genre(JSONObject jsonObject) throws JSONException {
        Id = jsonObject.getInt("id");
        nameGenre = jsonObject.getString("name");
    }

    public static List<Genre> fromJsonArray(JSONArray genreJsonArray) throws JSONException {
        List<Genre> genres = new ArrayList<>();
        for (int i = 0; i < genreJsonArray.length(); i++){
            genres.add(new Genre(genreJsonArray.getJSONObject(i)));
        }
        return genres;
    }

    public Integer getId() {
        return Id;
    }

    public String getNameGenre() {
        return nameGenre;
    }
}
