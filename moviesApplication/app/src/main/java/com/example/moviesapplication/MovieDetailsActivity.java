package com.example.moviesapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.moviesapplication.databinding.ActivityMovieDetailsBinding;
import com.example.moviesapplication.models.Genre;
import com.example.moviesapplication.models.Movie;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity{

    public static final String TAG = "MovieDetailsActivity";
    public static final String MOVIEDB_URL_BEGIN= "https://api.themoviedb.org/3";

    // Movie referenced and key initialized
    String movieVideoKey;
    StringBuilder stringGenre = new StringBuilder();
    Movie movie;
    List<Genre> genres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailsBinding movieDetailsBinding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());

        View view = movieDetailsBinding.getRoot();
        setContentView(view);

        // Unwrapping movie passed through intent
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        //Unwrapping genres list passed through intent
        genres = Parcels.unwrap(getIntent().getParcelableExtra(Genre.class.getSimpleName()));

        //Set movie details
        movieDetailsBinding.tvTitle.setText(movie.getTitle());
        movieDetailsBinding.tvOverview.setText(movie.getOverview());
        float voteAverage = movie.getVoteAverage().floatValue();
        movieDetailsBinding.rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
        movieDetailsBinding.tvReleaseDate.setText("Release Date: " + movie.getReleaseDate());
        movieDetailsBinding.tvPopularity.setText(movie.getPopularity() + "%");
        double moviePopularity = movie.getPopularity();
        movieDetailsBinding.pbPopularity.setProgress((int) ((moviePopularity - Math.floor(moviePopularity) > 0.5) ? Math.ceil(moviePopularity) : Math.floor(moviePopularity)));

        getMovieGenres();
        createGenreString();
        movieDetailsBinding.tvGenre.setText(stringGenre);


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

    // Create
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
                Toast.makeText(getApplicationContext(), "Error occurred loading video", Toast.LENGTH_LONG);
            }
        });
    }

    public void createGenreString(){

        if(movie.getGenres().size() == 0){
            Log.e("MovieDetailsActivity", "createGenreString: Genre list empty");
            return;
        }

        else {
            stringGenre.append("Genres: " + movie.getGenres().get(0));
            for (int i = 1 ; i < movie.getGenres().size() ; i++){
                stringGenre.append(", " + movie.getGenres().get(i));
            }
        }
    }
    public void getMovieGenres(){
        Integer[] movieGenres = movie.getGenreIds();
        for (int i = 0 ; i < movieGenres.length ; i++){
            for (int j = 0 ; j < genres.size() ; j++)
                if ((movieGenres[i].equals(genres.get(j).getId())) && (movie.getGenreIds().length != movie.listGenres.size())) {
                    movie.listGenres.add(genres.get(j).getNameGenre());
                }
        }
    }
}