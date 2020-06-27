package com.example.moviesapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.moviesapplication.adapters.MovieAdapter;
import com.example.moviesapplication.databinding.ActivityMainBinding;
import com.example.moviesapplication.models.Genre;
import com.example.moviesapplication.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final String MOVIEDB_URL_BEGIN = "https://api.themoviedb.org/3";
    List<Movie> movies;
    List<Genre> genres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //-----------------------------------------------------------------------------------------------
        super.onCreate(savedInstanceState);

        //activity_main.xml -> ActivityMainBinding
        ActivityMainBinding mainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        //Layout of activity is stored in a special property called root
        View view = mainBinding.getRoot();
        setContentView(view);

        // Create new ArrayLists
        movies = new ArrayList<>();
        genres = new ArrayList<>();

        // Create the adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this, movies, genres);

        // Set the adapter on the recycler view
        mainBinding.rvMovies.setAdapter(movieAdapter);

        // Set a Layout Manager on the recycler view
        mainBinding.rvMovies.setLayoutManager(new LinearLayoutManager(this));
        //-----------------------------------------------------------------------------------------------
        // Movie data retrieval from MovieDB
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MOVIEDB_URL_BEGIN + "/movie/now_playing?api_key=" + getString(R.string.moviedb_api_key), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;

                // Provides info on results array
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + movies.size());
                }
                // Provides info on thrown exception
                catch (JSONException e) {
                    Log.e(TAG, "JSON Exception here", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

        // Genre data retrieval from MovieDB
        AsyncHttpClient client2 = new AsyncHttpClient();
        client2.get(MOVIEDB_URL_BEGIN + "/genre/movie/list?api_key=" + getString(R.string.moviedb_api_key), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;

                // Provides info on results array
                try {
                    JSONArray results = jsonObject.getJSONArray("genres");
                    Log.i(TAG, "Genres: " + results.toString());
                    genres.addAll(Genre.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    // Sort the genres list
                    Collections.sort(genres);
                }
                // Provides info on thrown exception
                catch (JSONException e) {
                    Log.e(TAG, "JSON Exception here", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

    }
}
