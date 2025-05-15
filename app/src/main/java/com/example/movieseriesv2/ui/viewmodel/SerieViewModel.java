package com.example.movieseriesv2.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieseriesv2.data.model.Serie;
import com.example.movieseriesv2.data.repository.SeriesRepo;

import java.util.List;

public class SerieViewModel extends ViewModel {

    private final SeriesRepo seriesRepo;

    public SerieViewModel() {
        seriesRepo = new SeriesRepo();
        seriesRepo.loadNextPage(); // Load first page when ViewModel is created
    }

    public LiveData<List<Serie>> getSerieList() {
        return seriesRepo.getSeriesLiveData();
    }

    public void loadMoreSeries() {
        seriesRepo.loadNextPage();
    }

    public void refreshSeries() {
        seriesRepo.resetPagination();
    }
}
