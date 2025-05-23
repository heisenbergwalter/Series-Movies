package com.example.movieseriesv2.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieseriesv2.data.db.entities.Favorite;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(Favorite favorite);



    @Query("DELETE FROM favorites WHERE title = :title AND user_id = :userId")
    void deleteFavorite(String title, int userId);

    @Query("SELECT COUNT(*) FROM favorites WHERE id = :movieId AND user_id = :userId AND type = 'movie'")
    int countFavoriteMovie(int movieId, int userId);


    @Query("SELECT COUNT(*) FROM favorites WHERE id = :serieId AND user_id = :userId AND type = 'serie'")
    int countFavorite(int serieId, int userId);

    @Query("SELECT * FROM favorites WHERE user_id = :userId")
    List<Favorite> getAllFavoritesForUser(int userId);

    @Query("SELECT * FROM favorites WHERE user_id = :userId AND type = 'movie'")
    List<Favorite> getFavoriteMovies(int userId);

    @Query("SELECT * FROM favorites WHERE user_id = :userId AND type = 'serie'")
    List<Favorite> getFavoriteSeries(int userId);


}
