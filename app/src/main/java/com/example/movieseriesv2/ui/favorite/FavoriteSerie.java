package com.example.movieseriesv2.ui.favorite;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cinemaseries.R;
import com.example.movieseriesv2.Utils.SessionManager;
import com.example.movieseriesv2.data.db.entities.Favorite;
import com.example.movieseriesv2.data.model.Serie;
import com.example.movieseriesv2.data.repository.SeriesRepo;
import com.example.movieseriesv2.ui.home.SerieAdapter;
import com.example.movieseriesv2.ui.viewmodel.FavoriteViewModel;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteSerie extends Fragment {
    private RecyclerView recyclerView;
    private SerieAdapter adapter;
    private FavoriteViewModel viewModel;
    private SessionManager sessionManager;
    private int userId;

    private SeriesRepo seriesRepo;  // Corrected the type to SeriesRepo
    private List<Serie> favoriteSeries = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.serie_fragement, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new SerieAdapter(favoriteSeries);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        sessionManager = new SessionManager(getContext());
        userId = sessionManager.getUserId();
        seriesRepo = new SeriesRepo(); // Instantiate SeriesRepo

        loadFavoriteSeries();

        return view;
    }

    private void loadFavoriteSeries() {
        viewModel.getFavoriteSeries(userId).observe(getViewLifecycleOwner(), favorites -> {
            Log.d("FavoriteSerie", "Favorites loaded: " + favorites.size());
            favoriteSeries.clear();
            if (favorites.isEmpty()) {
                Log.d("FavoritSerie", "No favorites found.");
                return;
            }

            for (Favorite fav : favorites) {
                if ("serie".equalsIgnoreCase(fav.getType())) {
                    fetchSerieById(fav.getFavId());
                }
            }
        });
    }


    private void fetchSerieById(int serieId) {
        seriesRepo.getSerieById(serieId, new Callback<Serie>() {  // Correct method for fetching series
            @Override
            public void onResponse(Call<Serie> call, Response<Serie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_SERIE_RESULT", "Fetched serie: " + response.body().getName()); // or getTitle()
                    favoriteSeries.add(response.body());
                    adapter.notifyItemInserted(favoriteSeries.size() - 1);

                }
            }

            @Override
            public void onFailure(Call<Serie> call, Throwable t) {
                Log.e("FavoriteSeries", "Failed to load series with ID: " + serieId, t);
            }
        });
    }
}
