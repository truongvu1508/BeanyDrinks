package com.example.beanydrinks.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

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
