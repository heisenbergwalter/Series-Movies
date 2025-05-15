package com.example.movieseriesv2.ui.favorite;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaseries.R;
import com.example.movieseriesv2.Utils.SessionManager;
import com.example.movieseriesv2.data.db.entities.Favorite;
import com.example.movieseriesv2.data.model.Movie;
import com.example.movieseriesv2.data.model.MovieResponse;
import com.example.movieseriesv2.data.repository.MoviesRepo;
import com.example.movieseriesv2.ui.home.MovieAdapter;
import com.example.movieseriesv2.ui.viewmodel.FavoriteViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteMovies extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> favoriteMovies = new ArrayList<>();
    private FavoriteViewModel favoriteViewModel;
    private MoviesRepo moviesRepo;
    private SessionManager sessionManager;
    private int userId; // Replace with actual logged-in user

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_fragement, container, false);

        // Initialize views and adapter
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        movieAdapter = new MovieAdapter(favoriteMovies);
        recyclerView.setAdapter(movieAdapter);

        // Initialize session manager and ViewModel
        sessionManager = new SessionManager(getContext());
        userId = sessionManager.getUserId();
        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        moviesRepo = new MoviesRepo();

        // Load favorite movies
        loadFavoriteMovies();

        return view;
    }

    private void loadFavoriteMovies() {
        favoriteViewModel.getFavorites(userId).observe(getViewLifecycleOwner(), favorites -> {
            Log.d("FavoriteMovies", "Favorites loaded: " + favorites.size());
            favoriteMovies.clear();
            if (favorites.isEmpty()) {
                Log.d("FavoriteMovies", "No favorites found.");
                return;
            }

            for (Favorite fav : favorites) {
                if ("movie".equalsIgnoreCase(fav.getType())) {
                    fetchMovieById(fav.getFavId());
                }
            }
        });
    }



    private void fetchMovieById(int movieId) {
        moviesRepo.getMovieById(movieId, new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    favoriteMovies.add(response.body());
                    movieAdapter.notifyItemInserted(favoriteMovies.size() - 1);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("FavoriteMovies", "Failed to load movie with ID: " + movieId, t);
            }
        });
    }

}
