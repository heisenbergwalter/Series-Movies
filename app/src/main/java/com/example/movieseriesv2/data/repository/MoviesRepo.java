package com.example.movieseriesv2.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieseriesv2.data.model.Movie;
import com.example.movieseriesv2.data.model.MovieResponse;
import com.example.movieseriesv2.data.network.ApiService;
import com.example.movieseriesv2.data.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepo {

    private static final String API_KEY = "4ae714577c934fe554cea0c015667e4c";
    private ApiService apiService;
    private int currentPage = 1;
    private final MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();
    private final List<Movie> allMovies = new ArrayList<>();

    public MoviesRepo() {
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public LiveData<List<Movie>> getMoviesLiveData() {
        return moviesLiveData;
    }

    // Search Movie by Title
    public void getMovieById(int movieId, Callback<Movie> callback) {
        apiService.getMovieById(movieId, API_KEY).enqueue(callback);
    }







    public void loadNextPage() {
        Log.d("MoviesRepo", "Requesting page: " + currentPage);
        apiService.getMovies(API_KEY, currentPage).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> newMovies = response.body().getResults();
                    if (newMovies != null && !newMovies.isEmpty()) {
                        allMovies.addAll(newMovies);
                        moviesLiveData.setValue(new ArrayList<>(allMovies));
                        currentPage++;  // ✅ Increment page only if success
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("MoviesRepo", "Failed to load movies: " + t.getMessage());
            }
        });
    }

    public void resetPagination() {
        currentPage = 1;
        allMovies.clear();
        loadNextPage();  // Reload from first page
    }
}
