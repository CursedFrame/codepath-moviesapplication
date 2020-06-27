package com.example.moviesapplication.models;

//Imported JSON and Parceler libraries

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Parcel indicates that the class is Parcelable
@Parcel
public class Movie {
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    String releaseDate;
    Double voteAverage;
    Integer id;
    Integer[] genreIds;
    public List<String> genres;

    public Movie() {

    }

    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        releaseDate = jsonObject.getString("release_date");
        voteAverage = jsonObject.getDouble("vote_average");
        id = jsonObject.getInt("id");

        JSONArray genreArray = jsonObject.getJSONArray("genre_ids");
        genreIds = new Integer[genreArray.length()];
        for (int i = 0 ; i < genreArray.length() ; i++) {
            genreIds[i] = genreArray.getInt(i);
        }
        // Sort genreIds
        Arrays.sort(genreIds);
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Integer getId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Integer[] getGenreIds() {
        return genreIds;
    }

    public List<String> getGenres(){
        return genres;
    }
}
