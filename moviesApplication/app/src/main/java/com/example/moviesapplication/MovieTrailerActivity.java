package com.example.moviesapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.moviesapplication.databinding.ActivityMovieTrailerBinding;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //activity_movie_trailer.xml -> ActivityMovieTrailerBinding
        ActivityMovieTrailerBinding movieTrailerBinding = ActivityMovieTrailerBinding.inflate(getLayoutInflater());

        View view = movieTrailerBinding.getRoot();
        setContentView(R.layout.activity_movie_trailer);



        final String videoKey = getIntent().getStringExtra("VIDEO_KEY");

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.loadVideo(videoKey);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        // log the error
                        Log.e("MovieTrailerActivity", "Error initializing YouTube player");
                    }
                });
    }
}