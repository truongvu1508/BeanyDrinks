package com.example.beanydrinks.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.beanydrinks.fragment.DoanhThuNvFragment;
import com.example.beanydrinks.fragment.QLKHFragment;
import com.example.beanydrinks.fragment.QuanLyKhuVucNVFragment;
import com.example.beanydrinks.R;
import com.example.beanydrinks.fragment.TaiKhoanNVFragment;
import com.example.beanydrinks.databinding.ActivityNhanVienBinding;
import com.example.beanydrinks.fragment.ThucDonQLFragment;

public class NhanVienActivity extends AppCompatActivity {

    ActivityNhanVienBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityNhanVienBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.khuvuc) {
                selectedFragment = new QuanLyKhuVucNVFragment();
            } else if (id == R.id.thucdon) {
                selectedFragment = new ThucDonQLFragment();
            } else if (id == R.id.doanhthu) {
                selectedFragment = new DoanhThuNvFragment();
            } else if (id == R.id.taikhoan) {
                selectedFragment = new TaiKhoanNVFragment();
            }
            else if (id == R.id.khachhang) {
                selectedFragment = new QLKHFragment();
            }

            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
            }
            return true;
        });

        if (savedInstanceState == null) {
            replaceFragment(new QuanLyKhuVucNVFragment());
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}