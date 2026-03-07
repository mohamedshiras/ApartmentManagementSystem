package com.example.apartmentmanagementsystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

public class PostActivity extends AppCompatActivity {

    // ── Image picker launcher ──────────────────────────────
    private final ActivityResultLauncher<String> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    handleSelectedImage(uri);
                }
            });

    // ── Permission request launcher (for photo) ────────────
    private final ActivityResultLauncher<String> imagePermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) {
                    imagePickerLauncher.launch("image/*");
                } else {
                    Toast.makeText(this, "Permission denied. Cannot access photos.", Toast.LENGTH_SHORT).show();
                }
            });

    // ── File/any picker launcher (for Add More) ────────────
    private final ActivityResultLauncher<String[]> anyFileLauncher =
            registerForActivityResult(new ActivityResultContracts.OpenDocument(), uri -> {
                if (uri != null) {
                    handleSelectedFile(uri);
                }
            });

    // ── Storage permission launcher (for Add More) ─────────
    private final ActivityResultLauncher<String> storagePermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) {
                    openAddMoreSheet();
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // Safely check if 'main' exists before applying insets
        View mainView = findViewById(R.id.activity_post);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // ── Safely attach Back button ────────────────────────────────────
        ImageButton btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                Intent intent = new Intent(PostActivity.this, FeedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }

        // ── Safely attach Photo button ─
        LinearLayout optionPhoto = findViewById(R.id.optionPhoto);
        if (optionPhoto != null) {
            optionPhoto.setOnClickListener(v -> requestImagePermissionAndPick());
        }

        // ── Safely attach Add More button ────
        MaterialButton btnAddMore = findViewById(R.id.btnAddMore);
        if (btnAddMore != null) {
            btnAddMore.setOnClickListener(v -> requestStoragePermissionAndShowSheet());
        }
    }
    // ══════════════════════════════════════════════════════
    //  PHOTO PERMISSION + PICK
    // ══════════════════════════════════════════════════════
    private void requestImagePermissionAndPick() {
        String permission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                ? Manifest.permission.READ_MEDIA_IMAGES          // Android 13+
                : Manifest.permission.READ_EXTERNAL_STORAGE;     // Android 12-

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            imagePickerLauncher.launch("image/*");
        } else {
            imagePermissionLauncher.launch(permission);
        }
    }

    // ══════════════════════════════════════════════════════
    //  ADD MORE — PERMISSION + BOTTOM SHEET
    // ══════════════════════════════════════════════════════
    private void requestStoragePermissionAndShowSheet() {
        String permission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                ? Manifest.permission.READ_MEDIA_IMAGES
                : Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            openAddMoreSheet();
        } else {
            storagePermissionLauncher.launch(permission);
        }
    }

    private void openAddMoreSheet() {
        BottomSheetDialog sheet = new BottomSheetDialog(this);
        sheet.setContentView(R.layout.add_more); // ← create this layout

        // Image option inside sheet
        sheet.findViewById(R.id.sheetOptionImage).setOnClickListener(v -> {
            imagePickerLauncher.launch("image/*");
            sheet.dismiss();
        });

        // Document option inside sheet
        sheet.findViewById(R.id.sheetOptionDocument).setOnClickListener(v -> {
            anyFileLauncher.launch(new String[]{"application/pdf", "application/msword",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"});
            sheet.dismiss();
        });

        // Video option inside sheet
        sheet.findViewById(R.id.sheetOptionVideo).setOnClickListener(v -> {
            anyFileLauncher.launch(new String[]{"video/*"});
            sheet.dismiss();
        });

        sheet.show();
    }

    // ══════════════════════════════════════════════════════
    //  HANDLE SELECTED FILES
    // ══════════════════════════════════════════════════════
    private void handleSelectedImage(Uri uri) {
        // TODO: show preview in imgMediaPreview card
        Toast.makeText(this, "Image selected: " + uri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
    }

    private void handleSelectedFile(Uri uri) {
        // TODO: handle document/video uri
        Toast.makeText(this, "File selected: " + uri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
    }
}