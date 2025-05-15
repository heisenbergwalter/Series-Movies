package com.example.movieseriesv2;

import android.app.Application;

import androidx.room.Room;


import com.example.movieseriesv2.data.db.AppDatabase;

public class MyApp extends Application {
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "movie_series_db"
        ).fallbackToDestructiveMigration().build();
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
