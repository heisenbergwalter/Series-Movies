package com.example.movieseriesv2.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemaseries.R;
import com.example.movieseriesv2.Utils.SessionManager;
import com.example.movieseriesv2.ui.login.Login;
import com.example.movieseriesv2.ui.splash.MainActivity;

public class Profile extends Fragment {

    private static final int GALLERY_REQUEST_CODE = 100;

    private SessionManager sessionManager;
    private ImageView profileImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_profile, container, false);

        sessionManager = new SessionManager(requireContext());

        TextView tvUsername = view.findViewById(R.id.tvUsername);
        TextView tvEmail = view.findViewById(R.id.tvEmail);
        TextView tvUserId = view.findViewById(R.id.tvUserId);
        Button btnLogout = view.findViewById(R.id.btnLogout);
        profileImage = view.findViewById(R.id.prfl);

        if (sessionManager.isLoggedIn()) {
            tvUsername.setText("Username: " + sessionManager.getUsername());
            tvEmail.setText("Email: " + sessionManager.getEmail());
            tvUserId.setText("User ID: " + sessionManager.getUserId());
            btnLogout.setText("Logout");
        } else {
            tvUsername.setText("Guest");
            tvEmail.setText("Guest");
            tvUserId.setText("Guest");
            btnLogout.setText("Login");
        }

        btnLogout.setOnClickListener(v -> {
            String buttonText = btnLogout.getText().toString();
            if (buttonText.equals("Login")) {
                startActivity(new Intent(requireContext(), Login.class));
            } else if (buttonText.equals("Logout")) {
                sessionManager.logout();
                Intent intent = new Intent(requireContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        profileImage.setOnClickListener(v -> openGallery());

        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null && profileImage != null) {
                profileImage.setImageURI(selectedImageUri);
            }
        }
    }
}
