package com.example.movieseriesv2.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.movieseriesv2.data.model.Movie;
import com.example.movieseriesv2.ui.viewmodel.MovieViewModel;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private MovieViewModel movieViewModel;
    private List<Movie> allMovies = new ArrayList<>();
    private EditText searchInput;
    private boolean isSearching = false;

    public MovieFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_fragement, container, false);

        // Initialize RecyclerView and search input
        recyclerView = view.findViewById(R.id.recyclerView);
        searchInput = view.findViewById(R.id.search_input);

        movieAdapter = new MovieAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(movieAdapter);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        // Observe changes to the movie list
        movieViewModel.getMovieList().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                movieAdapter.updateMovies(movies);
            }
        });

        // Load initial popular movies
        movieViewModel.loadPopularMovies();

        // Search listener
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                if (!query.isEmpty()) {
                    isSearching = true;
                    movieViewModel.searchMovies(query);
                } else {
                    isSearching = false;
                    movieViewModel.getMovieList();
                }
            }

            @Override public void afterTextChanged(Editable s) {}
        });

        // Pagination for popular movies only
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!isSearching) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (layoutManager != null && layoutManager.findLastVisibleItemPosition() >= movieAdapter.getItemCount() - 5) {
                        movieViewModel.loadMoreMovies();
                    }
                }
            }
        });

        return view;
    }
}
