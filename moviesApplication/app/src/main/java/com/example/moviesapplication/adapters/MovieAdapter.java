package com.example.moviesapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesapplication.MovieDetailsActivity;
import com.example.moviesapplication.R;
import com.example.moviesapplication.models.Genre;
import com.example.moviesapplication.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;
   /* List<Genre> genres;*/

    public MovieAdapter(Context context, List<Movie> movies, List<Genre> genres) {
        this.context = context;
        this.movies = movies;
        /*this.genres = genres;*/
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);

        // Get the movie at the passed in position
        Movie movie = movies.get(position);

        // Bind the movie data into the ViewHolder
        holder.bind(movie);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){
                Movie movie = movies.get(position);
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                /*intent.putExtra(Genre.class.getSimpleName(), Parcels.wrap(genres));*/
                context.startActivity(intent);
            }

        }

        public void bind(Movie movie) {

            // Initialize function variables
            int radiusRoundedCorners = 30;
            int marginRoundedCorners = 10;
            String imageUrl;
            int placeholderImageId;

            // Get title and overview from "movie" object
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            // Modify text color for title and overview from "movie" object
            tvTitle.setTextColor(Color.parseColor("#DAA520"));
            tvOverview.setTextColor(Color.parseColor("#FFFFFF"));

            // If phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // Then imageUrl = back drop image
                imageUrl = movie.getBackdropPath();
                placeholderImageId = R.drawable.flicks_backdrop_placeholder;
            }
            else {
                // Else imageUrl = poster image
                imageUrl = movie.getPosterPath();
                placeholderImageId = R.drawable.flicks_movie_placeholder;
            }

            // Glide image loading and rounded corners transformation
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(placeholderImageId)
                    .error(placeholderImageId)
                    .transform(new RoundedCornersTransformation(radiusRoundedCorners, marginRoundedCorners))
                    .into(ivPoster);
        }
    }
}
