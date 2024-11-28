package com.example.beanydrinks.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beanydrinks.R;
import com.example.beanydrinks.fragment.ThemThucDonQLFragment;

public class ThemThucDonQLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_them_thuc_don_q_l); // Đảm bảo đây là layout chính cho Activity

        if (savedInstanceState == null) {
            // Khởi tạo FragmentThemThucDonQL và thay thế vào layout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new ThemThucDonQLFragment())
                    .commit();
        }
    }
}
