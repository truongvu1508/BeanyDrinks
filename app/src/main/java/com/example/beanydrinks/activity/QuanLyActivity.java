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

import com.example.beanydrinks.fragment.HomeFragment;
import com.example.beanydrinks.R;
import com.example.beanydrinks.fragment.QuanLyKhuVucNVFragment;
import com.example.beanydrinks.fragment.QuanLyKhuVucQLFragment;
import com.example.beanydrinks.fragment.StaffFragment;
import com.example.beanydrinks.fragment.TaiKhoanQLFragment;
import com.example.beanydrinks.fragment.ThucDonQLFragment;
import com.example.beanydrinks.databinding.ActivityQuanLyBinding;
import com.example.beanydrinks.fragment.thong_ke_mat_hang_qlFragment;


public class QuanLyActivity extends AppCompatActivity {

    ActivityQuanLyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityQuanLyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.home) {
                selectedFragment = new HomeFragment();
            } else if (id == R.id.khuvuc) {
                selectedFragment = new QuanLyKhuVucQLFragment();
            } else if (id == R.id.staff) {
                selectedFragment = new StaffFragment();
            } else if (id == R.id.charts) {
                selectedFragment = new thong_ke_mat_hang_qlFragment();
            } else if (id == R.id.user) {
                selectedFragment = new TaiKhoanQLFragment();
            }

            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
            }
            return true;
        });

        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}