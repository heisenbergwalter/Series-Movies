package com.example.movieseriesv2.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaseries.R;
import com.example.movieseriesv2.ui.viewmodel.SerieViewModel;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class SeriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private SerieAdapter serieAdapter;
    private SerieViewModel serieViewModel;
    private EditText searchInput;

    public SeriesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.serie_fragement, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchInput = view.findViewById(R.id.search_input);

        serieAdapter = new SerieAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(serieAdapter);

        serieViewModel = new ViewModelProvider(this).get(SerieViewModel.class);
        serieViewModel.getSerieList().observe(getViewLifecycleOwner(), series -> {
            serieAdapter.updateSeries(series);
        });

        // Pagination support
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastVisibleItemPosition() >= serieAdapter.getItemCount() - 5) {
                    serieViewModel.loadMoreSeries();
                }
            }
        });

        // Search input listener
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                serieViewModel.searchSeries(query);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        return view;
    }
}
