package com.example.movieseriesv2.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemaseries.R;
import com.example.movieseriesv2.data.db.entities.User;
import com.example.movieseriesv2.ui.viewmodel.UserViewModel;

public class Register extends AppCompatActivity {

    private EditText fullNameInput, emailRegisterInput, passwordRegisterInput;
    private Button registerButton;
    private TextView loginInsteadText;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Initialize views
        fullNameInput = findViewById(R.id.fullNameInput);
        emailRegisterInput = findViewById(R.id.emailRegisterInput);
        passwordRegisterInput = findViewById(R.id.passwordRegisterInput);
        registerButton = findViewById(R.id.registerButton);
        loginInsteadText = findViewById(R.id.loginInsteadText);

        // Register button click
        registerButton.setOnClickListener(v -> {
            String fullName = fullNameInput.getText().toString().trim();
            String email = emailRegisterInput.getText().toString().trim();
            String password = passwordRegisterInput.getText().toString().trim();

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create new user
            User newUser = new User(fullName, email, password);

            // Insert user using ViewModel
            userViewModel.insertUser(newUser);

            Toast.makeText(Register.this, "Registration successful!", Toast.LENGTH_SHORT).show();

            // Navigate to Login
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
            finish();
        });

        // Login instead click
        loginInsteadText.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        });
    }
}
