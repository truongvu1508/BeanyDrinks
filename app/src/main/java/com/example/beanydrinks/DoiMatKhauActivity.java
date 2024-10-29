package com.example.beanydrinks;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DoiMatKhauActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doi_mat_khau);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.frameLayout3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btnbackthemttkh);
        Button buttonCancel = findViewById(R.id.button_Cancel);

        btnBack.setOnClickListener(v -> finish());
        buttonCancel.setOnClickListener(v -> finish());
    }
}