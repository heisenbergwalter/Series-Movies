package com.example.movieseriesv2.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.movieseriesv2.data.db.AppDatabase;
import com.example.movieseriesv2.data.db.dao.UserDao;
import com.example.movieseriesv2.data.db.entities.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private final UserDao userDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        userDao = db.userDao();
    }

    public void insertUser(User user) {
        executor.execute(() -> userDao.insertUser(user));
    }

    public void updateUser(User user) {
        executor.execute(() -> userDao.updateUser(user));
    }

    public LiveData<List<User>> getAll(){
        return userDao.getAllUsers();
    }

    public void deleteUser(int id) {
        executor.execute(() -> userDao.deleteUserById(id));
    }

    public LiveData<User> login(String email, String password) {
        return userDao.login(email, password);
    }

    public LiveData<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public LiveData<User> getUserById(int id) {
        return userDao.getUserById(id);
    }
}
