package com.example.moviesapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moviesapplication.databinding.ActivityMovieDetailsBinding;
import com.example.moviesapplication.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {
    // Movie referenced
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

        //Set movie title and overview
        movieDetailsBinding.tvTitle.setText(movie.getTitle());
        movieDetailsBinding.tvOverview.setText(movie.getOverview());
        float voteAverage = movie.getVoteAverage().floatValue();
        movieDetailsBinding.rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
    }
}