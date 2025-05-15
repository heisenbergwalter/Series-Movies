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
    private TextView title , overview, releaseDate, vote, language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie_detail);

        poster = findViewById(R.id.series_poster);
        title = findViewById(R.id.series_title);
        overview = findViewById(R.id.series_overview);
        releaseDate = findViewById(com.example.cinemaseries.R.id.series_air_date);
        vote = findViewById(R.id.series_vote);

        language = findViewById(R.id.series_language_popularity);

        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(FavoriteViewModel.class);

        sessionManager = new SessionManager(this.getApplicationContext());



        Intent intent = getIntent();
        title.setText(intent.getStringExtra(EXTRA_NAME));
        releaseDate.setText("First Air Date: " + intent.getStringExtra(EXTRA_DATE));
        vote.setText("Rating: " + intent.getFloatExtra(EXTRA_VOTE, 0));

        language.setText("Original Language: " + intent.getStringExtra(EXTRA_LANGUAGE) + "    |  Popularity : " +intent.getDoubleExtra(EXTRA_POPULARITY,0)  );
        overview.setText(intent.getStringExtra(EXTRA_OVERVIEW));

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + intent.getStringExtra(EXTRA_POSTER))
                .into(poster);


        Button btn = findViewById(R.id.watch_now_button);

        btn.setOnClickListener(v -> {
            if (sessionManager.isLoggedIn()) {
                int movieId = getIntent().getIntExtra(EXTRAID, -1);
                if (movieId == -1) {
                    Toast.makeText(this, "Invalid serie ID!", Toast.LENGTH_SHORT).show();
                    Log.e("MovieDetailActivity", "Serie ID is missing or invalid.");
                    return;
                }

                Favorite favorite = new Favorite();
                favorite.id = movieId;
                favorite.title = getIntent().getStringExtra(EXTRA_NAME);
                favorite.type = "serie";
                favorite.userId = sessionManager.getUserId();

                Log.w("idUser", String.valueOf(favorite.userId));
                Log.w("IDserie", String.valueOf(favorite.id));

                viewModel.addFavorite(favorite);
                Log.d("FavoriteDao", "Inserting favorite: " + favorite.getTitle());
                Toast.makeText(this, "Serie added to favorites", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please log in to add favorites.", Toast.LENGTH_SHORT).show();
            }
        });

    }




}
