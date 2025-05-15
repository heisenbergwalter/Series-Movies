package com.example.movieseriesv2.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieseriesv2.data.model.Serie;
import com.example.movieseriesv2.data.repository.SeriesRepo;

import java.util.List;

public class SerieViewModel extends ViewModel {

    private final SeriesRepo seriesRepo;

    private boolean isSearching = false;
    private String currentQuery = "";

    public SerieViewModel() {
        seriesRepo = new SeriesRepo();
        seriesRepo.loadNextPage(); // Load first page when ViewModel is created
    }

    public LiveData<List<Serie>> getSerieList() {
        return seriesRepo.getSeriesLiveData();
    }

    public void loadMoreSeries() {
        if (!isSearching) {
            seriesRepo.loadNextPage();
        }
    }

    public void refreshSeries() {
        isSearching = false;
        currentQuery = "";
        seriesRepo.resetPagination();
    }

    public void searchSeries(String query) {
        if (query == null || query.trim().isEmpty()) {
            // Empty query => cancel search and refresh popular
            refreshSeries();
            return;
        }
        isSearching = true;
        currentQuery = query;
        seriesRepo.searchSeries(query);
    }
}
