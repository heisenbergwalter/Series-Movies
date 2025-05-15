package com.example.movieseriesv2.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemaseries.R;
import com.example.movieseriesv2.Utils.SessionManager;
import com.example.movieseriesv2.data.db.entities.User;
import com.example.movieseriesv2.ui.home.MovieFragment;
import com.example.movieseriesv2.ui.splash.MainActivity;
import com.example.movieseriesv2.ui.viewmodel.UserViewModel;

public class Login extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button loginButton;
    private UserViewModel userViewModel;

    private SessionManager sessionManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        TextView registerText = findViewById(R.id.registerText);

        // Navigate to registration activity
        registerText.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
        sessionManager = new SessionManager(this.getApplicationContext());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Set onClickListener for the login button
        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            userViewModel.login(email, password).observe(this, user -> {
                if (user != null) {
                    // Login successful, store user data in SharedPreferences
                    saveUserToSharedPrefs(user);
                    logUserFromSharedPrefs(); //
                    // After successful login
                    sessionManager.createLoginSession(user.getId(),user.getUsername(),user.getEmail());

                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this.getApplicationContext(), MainActivity.class));
                } else {
                    Log.w("Login", "Invalid login attempt: " + email + " " + password);
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void saveUserToSharedPrefs(User user) {
        SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("user_id", user.getId());
        editor.putString("username", user.getUsername());
        editor.putString("email", user.getEmail());
        editor.putBoolean("is_logged_in", true);
        editor.apply();
    }

    // 🔍 Log SharedPreferences content
    private void logUserFromSharedPrefs() {
        SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        int id = sharedPref.getInt("user_id", -1);
        String username = sharedPref.getString("username", "N/A");
        String email = sharedPref.getString("email", "N/A");
        boolean isLoggedIn = sharedPref.getBoolean("is_logged_in", false);

        Log.d("SharedPref", "User ID: " + id);
        Log.d("SharedPref", "Username: " + username);
        Log.d("SharedPref", "Email: " + email);
        Log.d("SharedPref", "Is Logged In: " + isLoggedIn);
    }
}
