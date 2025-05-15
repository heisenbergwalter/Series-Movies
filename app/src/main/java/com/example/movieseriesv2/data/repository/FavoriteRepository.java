package com.example.movieseriesv2.data.repository;



import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import com.example.movieseriesv2.data.db.AppDatabase;
import com.example.movieseriesv2.data.db.dao.FavoriteDao;
import com.example.movieseriesv2.data.db.entities.Favorite;
import com.example.movieseriesv2.data.model.Movie;
import com.example.movieseriesv2.data.model.Serie;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;

public class FavoriteRepository {
    private final FavoriteDao favoriteDao;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public FavoriteRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        favoriteDao = db.favoriteDao();
    }




    public void addFavorite(Favorite favorite) {
        Executors.newSingleThreadExecutor().execute(() -> favoriteDao.insertFavorite(favorite));
    }

    public void removeFavorite(String title, int userId) {
        Executors.newSingleThreadExecutor().execute(() -> favoriteDao.deleteFavorite(title, userId));
    }

        //for movies hada
    public void getFavoritesForUser(int userId, Consumer<List<Favorite>> callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Favorite> favorites = favoriteDao.getFavoriteMovies(userId);
            mainHandler.post(() -> callback.accept(favorites));
        });
    }


    public void getFavoriteSeries(int userId, Consumer<List<Favorite>> callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Favorite> favorites = favoriteDao.getFavoriteSeries(userId);
            mainHandler.post(() -> callback.accept(favorites));
        });
    }




    public void getFavoriteMovies(int userId, Consumer<List<Favorite>> callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Favorite> favorites = favoriteDao.getFavoriteMovies(userId);
            mainHandler.post(() -> callback.accept(favorites));
        });
    }

    public interface Callback<T> {
        void onResult(T result);
    }

    public void isFavorite(int serieId, int userId, Callback<Boolean> callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            int count = favoriteDao.countFavorite(serieId, userId); // this method must be in your DAO
            boolean exists = count > 0;
            mainHandler.post(() -> callback.onResult(exists));
        });

    }
}
