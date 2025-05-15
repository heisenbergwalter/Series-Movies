package com.example.movieseriesv2.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieseriesv2.data.db.entities.Favorite;
import com.example.movieseriesv2.data.repository.FavoriteRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private final FavoriteRepository repository;

    public FavoriteViewModel(Application application) {
        super(application);
        repository = new FavoriteRepository(application);
    }

    // ✅ Check if serie is favorite
    public LiveData<Boolean> isFavorite(int serieId, int userId) {
        MutableLiveData<Boolean> isFav = new MutableLiveData<>();
        repository.isFavorite(serieId, userId, exists -> {
            isFav.postValue(exists);
            Log.d("FavoriteViewModel", "isFavorite for serieId=" + serieId + ": " + exists);
        });
        return isFav;
    }

   public LiveData<List<Favorite>> getFavorites(int userId) {
        MutableLiveData<List<Favorite>> favorites = new MutableLiveData<>();
        repository.getFavoritesForUser(userId, favorites::postValue);
        return favorites;
    }
    // ✅ Add favorite
    public void addFavorite(Favorite favorite) {
        repository.addFavorite(favorite);
        Log.d("FavoriteViewModel", "Adding favorite: " + favorite.getTitle());
    }

    // ✅ Remove favorite
    public void removeFavorite(Favorite favorite) {
        repository.removeFavorite(favorite.getTitle(), favorite.getUserId());
        Log.d("FavoriteViewModel", "Removing favorite: " + favorite.getTitle());
    }

    // ✅ Get all favorite movies for user
    public LiveData<List<Favorite>> getFavoriteMovies(int userId) {
        MutableLiveData<List<Favorite>> movies = new MutableLiveData<>();
        repository.getFavoriteMovies(userId, movies::postValue);
        return movies;
    }

    public LiveData<Boolean> isFavoriteMovie(int movieId, int userId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        repository.isFavoriteMovie(movieId, userId, result::postValue);
        return result;
    }

    // ✅ Get all favorite series for user
    public LiveData<List<Favorite>> getFavoriteSeries(int userId) {
        MutableLiveData<List<Favorite>> series = new MutableLiveData<>();
        repository.getFavoriteSeries(userId, series::postValue);
        return series;
    }
}
