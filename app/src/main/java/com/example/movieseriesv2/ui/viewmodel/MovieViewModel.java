package com.example.movieseriesv2.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieseriesv2.data.model.Movie;
import com.example.movieseriesv2.data.repository.MoviesRepo;

import java.util.List;

public class MovieViewModel extends ViewModel {

    private final MoviesRepo movieRepo;

    public MovieViewModel() {
        movieRepo = new MoviesRepo();
        movieRepo.loadNextPage(); // Load the first page on init
    }

    public LiveData<List<Movie>> getMovieList() {
        return movieRepo.getMoviesLiveData();
    }

    public void loadMoreMovies() {
        movieRepo.loadNextPage();  // Load next page
    }

    public void refreshMovies() {
        movieRepo.resetPagination();  // Reload from page 1
    }
}
