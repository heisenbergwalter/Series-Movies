package com.example.movieseriesv2.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieseriesv2.data.model.Serie;
import com.example.movieseriesv2.data.model.SeriesResponse;
import com.example.movieseriesv2.data.network.ApiService;
import com.example.movieseriesv2.data.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesRepo {

    private static final String API_KEY = "4ae714577c934fe554cea0c015667e4c";
    private ApiService apiService;
    private int currentPage = 1;
    private final MutableLiveData<List<Serie>> seriesLiveData = new MutableLiveData<>();
    private final List<Serie> allSeries = new ArrayList<>();

    public SeriesRepo() {
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public LiveData<List<Serie>> getSeriesLiveData() {
        return seriesLiveData;
    }
    public void getPopularSeries(int page, Callback<SeriesResponse> callback) {
        apiService.getSeries(API_KEY, page).enqueue(callback);
    }

    public void searchSeries(String query) {
        apiService.searchSeries(query, API_KEY).enqueue(new Callback<SeriesResponse>() {
            @Override
            public void onResponse(Call<SeriesResponse> call, Response<SeriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    seriesLiveData.postValue(response.body().getResults());
                }
                // handle else case if needed
            }

            @Override
            public void onFailure(Call<SeriesResponse> call, Throwable t) {
                // handle failure if needed
            }
        });
    }


    public void getSerieById(int serieId, Callback<Serie> callback) {
        Call<Serie> call = apiService.getSerieById(serieId, API_KEY); // Ensure API key is correct
        call.enqueue(callback);
    }

    public void loadNextPage() {
        apiService.getSeries(API_KEY, currentPage).enqueue(new Callback<SeriesResponse>() {
            @Override
            public void onResponse(Call<SeriesResponse> call, Response<SeriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Serie> newSeries = response.body().getResults();
                    if (newSeries != null) {
                        allSeries.addAll(newSeries);
                        seriesLiveData.setValue(allSeries);
                        currentPage++;
                    }
                }
            }

            @Override
            public void onFailure(Call<SeriesResponse> call, Throwable t) {
                Log.w("repoSerie", "error fetch");
            }
        });
    }

    public void resetPagination() {
        currentPage = 1;
        allSeries.clear();
        loadNextPage();
    }
}
