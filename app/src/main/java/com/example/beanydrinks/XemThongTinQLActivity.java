package com.example.beanydrinks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class XemThongTinQLActivity extends AppCompatActivity {
    private Button btnSelectPhoto;
    private ImageView imgAvt;
    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_xem_thong_tin_qlactivity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSelectPhoto = findViewById(R.id.btn_select_img_avt);
        imgAvt = findViewById(R.id.image_avt);

        registerResult();

        btnSelectPhoto.setOnClickListener(view -> pickImage());

        ImageButton btnBack = findViewById(R.id.btnBack);
        Button buttonCancel = findViewById(R.id.button_Cancel);

        btnBack.setOnClickListener(v -> finish());
        buttonCancel.setOnClickListener(v -> finish());
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }

    private void registerResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Uri imageUri = result.getData().getData();
                            if (imageUri != null) {
                                imgAvt.setImageURI(imageUri);
                            } else {
                                Toast.makeText(XemThongTinQLActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
    }
}
