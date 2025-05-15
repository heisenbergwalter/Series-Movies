package com.example.movieseriesv2.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieseriesv2.data.db.entities.Favorite;
import com.example.movieseriesv2.data.repository.FavoriteRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private FavoriteRepository repository;
    private MutableLiveData<List<Favorite>> favorites = new MutableLiveData<>();



    public FavoriteViewModel(Application application) {
        super(application);
        repository = new FavoriteRepository(application);
    }

    public LiveData<List<Favorite>> getFavorites(int userId) {
        repository.getFavoritesForUser(userId, favorites::postValue);
        return favorites;
    }

    public void addFavorite(Favorite favorite) {
        repository.addFavorite(favorite);
        Log.d("FavoriteDao", "Inserting favorite: " + favorite.getTitle());
    }

    public void removeFavorite(String title, int userId) {
        repository.removeFavorite(title, userId);
    }

    public LiveData<List<Favorite>> getFavoriteSeries(int userId) {
        MutableLiveData<List<Favorite>> favoriteSeries = new MutableLiveData<>();
        repository.getFavoriteSeries(userId, favoriteSeries::postValue);
        return favoriteSeries;
    }




}
