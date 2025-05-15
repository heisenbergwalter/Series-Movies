package com.example.movieseriesv2.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieseriesv2.data.db.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    LiveData<User> login(String email, String password);


    @Query("UPDATE users SET image = :image WHERE id = :userId")
    void updateProfileImage(int userId, byte[] image);
    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();


    @Query("SELECT * FROM users WHERE email = :email")
    LiveData<User> getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE id = :id")
    LiveData<User> getUserById(int id);

    @Query("DELETE FROM users WHERE id = :id")
    void deleteUserById(int id);
}
