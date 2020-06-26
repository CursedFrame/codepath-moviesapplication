package com.example.moviesapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.moviesapplication.databinding.ActivityMovieDetailsBinding;
import com.example.moviesapplication.models.Movie;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity{

    public static final String TAG = "MovieDetailsActivity";
    public static final String MOVIEDB_URL_BEGIN= "https://api.themoviedb.org/3";

    // Movie referenced and key initialized
    String movieVideoKey;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailsBinding movieDetailsBinding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());

        View view = movieDetailsBinding.getRoot();
        setContentView(view);

        // Unwrapping movie passed through intent
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        //Set movie details
        movieDetailsBinding.tvTitle.setText(movie.getTitle());
        movieDetailsBinding.tvOverview.setText(movie.getOverview());
        float voteAverage = movie.getVoteAverage().floatValue();
        movieDetailsBinding.rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        //API request to get YouTube video from MovieDB
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MOVIEDB_URL_BEGIN + "/movie/" + movie.getId() + "/videos?api_key=" + getString(R.string.moviedb_api_key), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;

                // Provides info on results array
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    jsonObject = results.getJSONObject(0);

                    //Sets video key for YouTube url
                    movieVideoKey = jsonObject.getString("key");
                    Log.i(TAG, "Video Id received successfully: " + movieVideoKey);

                    createYoutubeVideo();
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

    public void createYoutubeVideo(){
        YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtubeFragment);
        youtubeFragment.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.
                youTubePlayer.cueVideo(movieVideoKey);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
    /*@Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivYoutube){
            Intent intent = new Intent(getBaseContext(), MovieTrailerActivity.class);
            intent.putExtra("VIDEO_KEY", videoKey);
            startActivity(intent);
        }
    }*/
}