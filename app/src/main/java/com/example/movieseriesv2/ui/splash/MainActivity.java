package com.example.movieseriesv2.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.cinemaseries.R;
import com.example.movieseriesv2.Utils.NetworkMonitor;
import com.example.movieseriesv2.Utils.SessionManager;
import com.example.movieseriesv2.ui.favorite.FavoriteMovies;
import com.example.movieseriesv2.ui.favorite.FavoriteSerie;
import com.example.movieseriesv2.ui.home.MovieFragment;
import com.example.movieseriesv2.ui.home.Profile;
import com.example.movieseriesv2.ui.home.SeriesFragment;
import com.example.movieseriesv2.ui.login.Login;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private SessionManager sessionManager;
    private TextView headerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View noInternetLayout = findViewById(R.id.noInternetLayout);
        View mainContentLayout = findViewById(R.id.mainContentLayout);

        NetworkMonitor networkMonitor = new NetworkMonitor(this);
        networkMonitor.observe(this, isConnected -> {
            if (isConnected) {
                mainContentLayout.setVisibility(View.VISIBLE);
                noInternetLayout.setVisibility(View.GONE);
            } else {
                mainContentLayout.setVisibility(View.GONE);
                noInternetLayout.setVisibility(View.VISIBLE);
            }
        });


        // Initialize session manager
        sessionManager = new SessionManager(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable hamburger menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.open,
                R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set up header view
        View headerView = navigationView.getHeaderView(0);
        headerName = headerView.findViewById(R.id.drawerHeaderName);

        // Set click listener for header to navigate to login
        headerName.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        });

        // Set up the navigation item selected listener
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_movies) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MovieFragment())
                        .commit();
            } else if (id == R.id.nav_series) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new SeriesFragment())
                        .commit();
            } else if (id == R.id.nav_profile) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new Profile())
                        .commit();
            } else if (id == R.id.nav_users) {
                startActivity(new Intent(this, com.example.movieseriesv2.ui.home.UserActivity.class));
            }else if (id == R.id.nav_fav_m) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new FavoriteMovies())
                        .addToBackStack(null)
                        .commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if (id == R.id.nav_fav_s) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FavoriteSerie())
                    .addToBackStack(null)
                    .commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        }

            drawerLayout.closeDrawers();
            return true;
        });

        // Load default fragment if not savedInstanceState
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MovieFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_movies);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume called");  // Log to verify if the method is triggered
        updateDrawerUsername();
    }

    // Update the drawer username based on login status
    private void updateDrawerUsername() {
        // Get the username from the session
        String username = sessionManager.getUsername();
        Log.d("MainActivity", "Retrieved username: " + username);  // Log to verify if username is correct

        // Set the username in the drawer header
        if (sessionManager.isLoggedIn()) {
            headerName.setText("Hello  "+username);
            headerName.setClickable(false);
        } else {
            headerName.setText("Guest");
        }
    }
}
