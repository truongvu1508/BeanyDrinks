package com.example.beanydrinks;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class thong_ke_mat_hang_qlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_mat_hang_ql);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new thong_ke_mat_hang_ql())
                    .commit();
        }
    }
}