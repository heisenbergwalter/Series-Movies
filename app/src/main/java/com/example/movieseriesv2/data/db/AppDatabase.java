package com.example.movieseriesv2.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movieseriesv2.data.db.entities.User;
import com.example.movieseriesv2.data.db.entities.Favorite;
import com.example.movieseriesv2.data.db.dao.UserDao;
import com.example.movieseriesv2.data.db.dao.FavoriteDao;

@Database(entities = {User.class, Favorite.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract FavoriteDao favoriteDao();

    private static volatile AppDatabase INSTANCE;
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Create the database if it does not exist
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app-db")
                            .fallbackToDestructiveMigration() // Handle database schema changes
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
