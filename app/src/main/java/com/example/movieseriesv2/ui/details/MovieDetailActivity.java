package com.example.movieseriesv2.ui.details;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.cinemaseries.R;
import com.example.movieseriesv2.Utils.SessionManager;
import com.example.movieseriesv2.data.db.entities.Favorite;
import com.example.movieseriesv2.ui.viewmodel.FavoriteViewModel;

public class MovieDetailActivity extends AppCompatActivity {

    FavoriteViewModel viewModel;
    SessionManager sessionManager;

    public static final String EXTRA_LANGUAGE = "language";
    public static final String EXTRA_POPULARITY = "popularity";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_ID = "movie_id";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_POSTER = "poster_path";
    public static final String EXTRA_OVERVIEW = "overview";
    public static final String EXTRA_VOTE = "vote_average";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(FavoriteViewModel.class);

        sessionManager = new SessionManager(this.getApplicationContext());

        TextView title = findViewById(R.id.movie_title);
        TextView overviewText = findViewById(R.id.movie_overview);
        TextView releaseDateText = findViewById(R.id.movie_release_date);
        TextView voteText = findViewById(R.id.movie_rate);
        ImageView posterImage = findViewById(R.id.movie_poster);
        RatingBar rate = findViewById(R.id.movie_rating_bar);
        Button btn = findViewById(R.id.watch_now_button);

        double rating = getIntent().getDoubleExtra(EXTRA_VOTE, 0.0);
        rate.setRating((float) rating / 2);

        String movieTitle = getIntent().getStringExtra(EXTRA_TITLE);
        int movieId = getIntent().getIntExtra(EXTRA_ID, -1);
        String posterPath = getIntent().getStringExtra(EXTRA_POSTER);
        int userId = sessionManager.getUserId();

        title.setText(movieTitle);
        overviewText.setText(getIntent().getStringExtra(EXTRA_OVERVIEW));
        releaseDateText.setText("Release: " + getIntent().getStringExtra(EXTRA_DATE));
        voteText.setText("Rating: " + rating);

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + posterPath)
                .into(posterImage);

        if (movieId == -1) {
            Toast.makeText(this, "Invalid movie ID!", Toast.LENGTH_SHORT).show();
            Log.e("MovieDetailActivity", "Movie ID is missing or invalid.");
            return;
        }

        Favorite favorite = new Favorite();
        favorite.id = movieId;
        favorite.title = movieTitle;
        favorite.type = "movie";
        favorite.userId = userId;

        if (!sessionManager.isLoggedIn()) {
            btn.setEnabled(false);
            btn.setText("Login to Favorite");
            return;
        }

        // Observe if movie is already in favorites
        viewModel.isFavoriteMovie(movieId, userId).observe(this, isFav -> {
            btn.setText(isFav ? "Remove from Favorites" : "Add to Favorites");

            btn.setOnClickListener(v -> {
                if (isFav) {
                    viewModel.removeFavorite(favorite);

                    Toast.makeText(this, "Movie removed from favorites", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    viewModel.addFavorite(favorite);
                    Toast.makeText(this, "Movie added to favorites", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
