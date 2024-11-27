package com.example.beanydrinks;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

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
