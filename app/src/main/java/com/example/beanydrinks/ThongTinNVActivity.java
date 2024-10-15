package com.example.beanydrinks;
import android.Manifest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
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

import java.util.List;

public class ThongTinNVActivity extends AppCompatActivity {
    private Button btnSelectPhoto;
    private ImageView imgAvt;
    private ActivityResultLauncher<Intent> resultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_tin_nvactivity);

        btnSelectPhoto = findViewById(R.id.btn_select_img_avt);
        imgAvt = findViewById(R.id.image_avt);

        btnSelectPhoto.setOnClickListener(view -> pickImage());

    }

    private void pickImage() {
        Intent intent=new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            Uri image_avt_uri = result.getData().getData();
                            imgAvt.setImageURI(image_avt_uri);
                        } catch (Exception e){
                            Toast.makeText(ThongTinNVActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}