package com.example.apartmentmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.getSupportActionBar().hide();
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        FrameLayout navFeed = findViewById(R.id.nav_btn_feed);
        LinearLayout navNotices = findViewById(R.id.nav_btn_notices);
        LinearLayout navChat = findViewById(R.id.nav_btn_chat);
        LinearLayout navServices = findViewById(R.id.nav_btn_services);
        LinearLayout navProfile = findViewById(R.id.nav_btn_profile);

        android.view.View btnPost = findViewById(R.id.createPostFab);
        if (btnPost != null) {
            btnPost.setOnClickListener(v -> {
                startActivity(new Intent(this, PostActivity.class));
                overridePendingTransition(0, 0);
            });
        }

        // Feed is the current activity, so we can disable its click listener or simply do nothing
        navFeed.setOnClickListener(v -> {});

        navNotices.setOnClickListener(v -> {
            startActivity(new Intent(this, AnnouncementActivity.class));
            overridePendingTransition(0, 0);
        });

        navChat.setOnClickListener(v -> {
            startActivity(new Intent(this, ChatActivity.class));
            overridePendingTransition(0, 0);
        });

        navServices.setOnClickListener(v -> {
            startActivity(new Intent(this, ServicesActivity.class));
            overridePendingTransition(0, 0);
        });

        navProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
            overridePendingTransition(0, 0);
        });
    }



        BottomNavigationView bottomNav = findViewById(R.id.bottomNavBar);

        // ✅ Set listener FIRST before setSelectedItemId
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_profile) {
                // Already here, do nothing
                return true;

            } else if (id == R.id.nav_feed) {
                startActivity(new Intent(this, FeedActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;

            } else if (id == R.id.nav_announcements) {
                // Uncomment when AnnouncementActivity is ready
                 startActivity(new Intent(this, AnnouncementActivity.class));
                 overridePendingTransition(0, 0);
                 finish();
                return true;

            } else if (id == R.id.nav_chat) {
                // Uncomment when ChatActivity is ready
                 startActivity(new Intent(this, ChatActivity.class));
                 overridePendingTransition(0, 0);
                 finish();
                return true;

            } else if (id == R.id.nav_services) {
                // Uncomment when ServicesActivity is ready
                 startActivity(new Intent(this, ServicesActivity.class));
                 overridePendingTransition(0, 0);
                 finish();
                return true;
            }

            return false;
        });

        // ✅ Set selected item AFTER listener is attached
        bottomNav.setSelectedItemId(R.id.nav_profile);
    }
}