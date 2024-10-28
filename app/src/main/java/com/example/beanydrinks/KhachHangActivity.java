package com.example.beanydrinks;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.beanydrinks.databinding.FragmentQLKHBinding;

public class KhachHangActivity extends AppCompatActivity {

    FragmentQLKHBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sử dụng View Binding để liên kết layout
        binding = FragmentQLKHBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Không gọi fragment, mà chỉ cần hiển thị layout chính của Activity
    }
}
