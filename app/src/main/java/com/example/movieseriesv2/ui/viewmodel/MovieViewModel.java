package com.example.movieseriesv2.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieseriesv2.data.model.Movie;
import com.example.movieseriesv2.data.model.MovieResponse;
import com.example.movieseriesv2.data.repository.MoviesRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {

    private final MoviesRepo moviesRepo = new MoviesRepo();
    private final MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();

    private int currentPage = 1;
    private boolean isLoading = false;

    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }

    public void loadPopularMovies() {
        currentPage = 1;
        isLoading = true;
        moviesRepo.getPopularMovies(currentPage, new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                isLoading = false;
                if (response.isSuccessful() && response.body() != null) {
                    movieList.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                isLoading = false;
                t.printStackTrace();
            }
        });
    }

    public void loadMoreMovies() {
        if (isLoading) return;
        isLoading = true;
        currentPage++;
        moviesRepo.getPopularMovies(currentPage, new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                isLoading = false;
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> current = movieList.getValue();
                    if (current != null) {
                        current.addAll(response.body().getResults());
                        movieList.setValue(current);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                isLoading = false;
                t.printStackTrace();
            }
        });
    }

    public void searchMovies(String query) {
        moviesRepo.searchMovies(query, new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movieList.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
