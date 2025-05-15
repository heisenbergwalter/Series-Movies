package com.example.movieseriesv2.data.db.entities;

import static androidx.room.ForeignKey.NO_ACTION;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = NO_ACTION))
public class Favorite {
    @PrimaryKey()
    public int id;

    @ColumnInfo(name = "title")
    public String title; // Movie or Serie ID

    @ColumnInfo(name = "type")
    public String type; // "movie" or "serie"

    @ColumnInfo(name = "user_id")
    public int userId; // The ID of the user who favorited the item

    // Getters
    public int getFavId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public int getUserId() {
        return userId;
    }
}
