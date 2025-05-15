package com.example.movieseriesv2.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movieseriesv2.data.db.entities.User;
import com.example.movieseriesv2.data.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public void insertUser(User user) {
        repository.insertUser(user);
    }

    public void updateUser(User user) {
        repository.updateUser(user);
    }

    public void deleteUser(int id) {
        repository.deleteUser(id);
    }

    public LiveData<User> login(String email, String password) {
        return repository.login(email, password);
    }

    public LiveData<List<User>> getAllUsers(){
        return repository.getAll();
    }

    public LiveData<User> getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }

    public LiveData<User> getUserById(int id) {
        return repository.getUserById(id);
    }
}
