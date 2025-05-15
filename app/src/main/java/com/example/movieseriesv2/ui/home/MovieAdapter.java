package com.example.movieseriesv2.ui.home;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cinemaseries.R;
import com.example.movieseriesv2.data.model.Movie;
import com.example.movieseriesv2.ui.details.MovieDetailActivity;

import java.util.ArrayList;
import java.util.List;
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private List<Movie> fullMovieList; // Store full list to reset filter

    public MovieAdapter(List<Movie> movieList) {
        this.fullMovieList = new ArrayList<>(movieList); // Store the full list for resetting filter
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        holder.title.setText(movie.getTitle());
        holder.releaseDate.setText(movie.getReleaseDate());
        holder.rating.setText(String.valueOf(movie.getVoteAverage()));

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.poster);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.EXTRA_ID, movie.getIdMovie());
            intent.putExtra(MovieDetailActivity.EXTRA_TITLE, movie.getTitle());
            intent.putExtra(MovieDetailActivity.EXTRA_DATE, movie.getReleaseDate());
            intent.putExtra(MovieDetailActivity.EXTRA_POSTER, movie.getPosterPath());
            intent.putExtra(MovieDetailActivity.EXTRA_OVERVIEW, movie.getOverview());
            intent.putExtra(MovieDetailActivity.EXTRA_VOTE, movie.getVoteAverage());
            intent.putExtra(MovieDetailActivity.EXTRA_POPULARITY, movie.getPopularity());
            intent.putExtra(MovieDetailActivity.EXTRA_LANGUAGE, movie.getOriginalLanguage());

            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    public void updateMovies(List<Movie> movies) {
        this.fullMovieList = new ArrayList<>(movies); // Store the full list
        this.movieList = movies;
        notifyDataSetChanged();
    }

    public void addMovies(List<Movie> newMovies) {
        int start = movieList.size();
        movieList.addAll(newMovies);
        fullMovieList.addAll(newMovies); // Optional if filtering
        notifyItemRangeInserted(start, newMovies.size());
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView title, releaseDate, rating;
        ImageView poster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            releaseDate = itemView.findViewById(R.id.release_Date);
            rating = itemView.findViewById(R.id.rating);
            poster = itemView.findViewById(R.id.poster);
        }
    }
}
