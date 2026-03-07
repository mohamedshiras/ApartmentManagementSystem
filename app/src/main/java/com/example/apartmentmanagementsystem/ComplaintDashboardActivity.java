package com.example.apartmentmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ComplaintDashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ComplaintsAdapter adapter;
    private List<Complaint> complaintList;
    private String currentStatusFilter = "All";
    private String currentSearchQuery = "";
    private TextView pendingCountText, resolvedCountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_dashboard);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize Views
        recyclerView = findViewById(R.id.complaintsRecyclerView);
        pendingCountText = findViewById(R.id.pendingCount);
        resolvedCountText = findViewById(R.id.resolvedCount);
        EditText searchEditText = findViewById(R.id.searchEditText);
        ChipGroup filterChipGroup = findViewById(R.id.filterChipGroup);

        // Setup RecyclerView
        complaintList = new ArrayList<>();
        loadDummyData();
        adapter = new ComplaintsAdapter(complaintList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateStats();

        // Setup Search
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentSearchQuery = s.toString();
                adapter.filter(currentSearchQuery, currentStatusFilter);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Setup Filters
        filterChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) {
                currentStatusFilter = "All";
            } else {
                int id = checkedIds.get(0);
                if (id == R.id.chipAll) currentStatusFilter = "All";
                else if (id == R.id.chipPending) currentStatusFilter = "Pending";
                else if (id == R.id.chipResolved) currentStatusFilter = "Resolved";
            }
            adapter.filter(currentSearchQuery, currentStatusFilter);
        });

        // Setup Back Button
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> onBackPressed());

        // Setup FAB
        ExtendedFloatingActionButton addComplaintFab = findViewById(R.id.addComplaintFab);
        addComplaintFab.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateComplaintActivity.class));
        });
    }

    private void loadDummyData() {
        complaintList.add(new Complaint("1", "Leaking Pipe in Kitchen", "Water is leaking from the main pipe.", "Plumbing", "High", "PENDING", "2 hours ago", "4B"));
        complaintList.add(new Complaint("2", "Elevator Noise", "The lift makes a grinding sound.", "Maintenance", "Medium", "RESOLVED", "Yesterday", "Block C"));
        complaintList.add(new Complaint("3", "No Hot Water", "Shower only has cold water.", "Plumbing", "Emergency", "PENDING", "5 mins ago", "12A"));
        complaintList.add(new Complaint("4", "Broken Light", "Bulb is flickering in the hallway.", "Electrical", "Low", "RESOLVED", "2 days ago", "Floor 2"));
    }

    private void updateStats() {
        int pending = 0;
        int resolved = 0;
        for (Complaint c : complaintList) {
            if (c.getStatus().equals("PENDING")) pending++;
            else resolved++;
        }
        pendingCountText.setText(String.valueOf(pending));
        resolvedCountText.setText(String.valueOf(resolved));
    }
}