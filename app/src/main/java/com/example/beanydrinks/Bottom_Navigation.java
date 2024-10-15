package com.example.beanydrinks;

import static com.example.beanydrinks.R.id.doanhthu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.beanydrinks.databinding.ActivityBottomNavigationBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Bottom_Navigation extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ActivityBottomNavigationBinding binding;
    KhuvucFragment khuvucFragment = new KhuvucFragment();
    DoanhthuFragment doanhthuFragment = new DoanhthuFragment();
    TaiKhoanNV taiKhoanNV = new TaiKhoanNV();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bottom_navigation);
        binding = ActivityBottomNavigationBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,khuvucFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
//                    switch (item.getItemId()){
//                        case R.id.khuvuc :
//                            getSupportFragmentManager().beginTransaction().replace(R.id.container,khuvucFragment).commit();
//                            return true;
//                        case R.id.doanhthu:
//                            getSupportFragmentManager().beginTransaction().replace(R.id.container,doanhthuFragment).commit();
//                            return true;
//                        case R.id.taikhoan:
//                            getSupportFragmentManager().beginTransaction().replace(R.id.container,taikhoanFragment).commit();
//                            return true;
//                    }

                if(item.getItemId() == R.id.khuvuc){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,khuvucFragment).commit();
                    return true;
                }else if(item.getItemId() ==R.id.doanhthu){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,doanhthuFragment).commit();
                    return true;
                }else if (item.getItemId() ==R.id.taikhoan){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,taiKhoanNV).commit();
//                            return true;
                }
                return false;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}