package com.example.movieseriesv2.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.cinemaseries.R;
import com.example.movieseriesv2.Utils.SessionManager;
import com.example.movieseriesv2.data.db.entities.Favorite;
import com.example.movieseriesv2.ui.favorite.FavoriteSerie;
import com.example.movieseriesv2.ui.viewmodel.FavoriteViewModel;

public class SerieDetailActivity extends AppCompatActivity {
    public static final String EXTRAID = "EXTRA_ID";
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_POSTER = "poster";
    public static final String EXTRA_OVERVIEW = "overview";
    public static final String EXTRA_DATE = "first_air_date";
    public static final String EXTRA_VOTE = "vote_average";
    public static final String EXTRA_POPULARITY = "popularity";
    public static final String EXTRA_LANGUAGE = "original_language";

    FavoriteViewModel viewModel;
    SessionManager sessionManager;

    private ImageView poster;
    private TextView title, overview, releaseDate, vote, language;
    private Button btn;

    private Favorite currentFavorite; // Keep this to pass to removeFavorite

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie_detail);

        poster = findViewById(R.id.series_poster);
        title = findViewById(R.id.series_title);
        overview = findViewById(R.id.series_overview);
        releaseDate = findViewById(R.id.series_air_date);
        vote = findViewById(R.id.series_vote);
        language = findViewById(R.id.series_language_popularity);
        btn = findViewById(R.id.watch_now_button);

        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(FavoriteViewModel.class);

        sessionManager = new SessionManager(this.getApplicationContext());

        Intent intent = getIntent();
        int serieId = intent.getIntExtra(EXTRAID, -1);
        String serieName = intent.getStringExtra(EXTRA_NAME);
        String seriePoster = intent.getStringExtra(EXTRA_POSTER);
        String serieOverview = intent.getStringExtra(EXTRA_OVERVIEW);
        String serieDate = intent.getStringExtra(EXTRA_DATE);
        float serieVote = intent.getFloatExtra(EXTRA_VOTE, 0);
        double seriePopularity = intent.getDoubleExtra(EXTRA_POPULARITY, 0);
        String serieLanguage = intent.getStringExtra(EXTRA_LANGUAGE);

        // Set UI
        title.setText(serieName);
        releaseDate.setText("First Air Date: " + serieDate);
        vote.setText("Rating: " + serieVote);
        language.setText("Original Language: " + serieLanguage + "    |  Popularity: " + seriePopularity);
        overview.setText(serieOverview);

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + seriePoster)
                .into(poster);

        if (serieId == -1) {
            Toast.makeText(this, "Invalid serie ID!", Toast.LENGTH_SHORT).show();
            Log.e("SerieDetailActivity", "Serie ID is missing or invalid.");
            btn.setEnabled(false);
            return;
        }

        int userId = sessionManager.getUserId();

        currentFavorite = new Favorite();
        currentFavorite.id = serieId;
        currentFavorite.title = serieName;
        currentFavorite.type = "serie";
        currentFavorite.userId = userId;

        // Observe if this serie is favorite or not:
        viewModel.isFavorite(serieId, userId).observe(this, isFav -> {
            if (isFav == null) return;

            btn.setText(isFav ? "Remove from Favorites" : "Add to Favorites");

            btn.setOnClickListener(v -> {
                if (!sessionManager.isLoggedIn()) {
                    Toast.makeText(this, "Please log in to add favorites.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isFav) {
                    viewModel.removeFavorite(currentFavorite);
                    Toast.makeText(this, "Serie removed from favorites", Toast.LENGTH_SHORT).show();

                    finish(); // Optional: close detail after removal
                } else {
                    viewModel.addFavorite(currentFavorite);
                    Toast.makeText(this, "Serie added to favorites", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
