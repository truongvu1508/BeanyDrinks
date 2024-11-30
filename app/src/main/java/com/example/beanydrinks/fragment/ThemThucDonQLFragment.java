package com.example.beanydrinks.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.beanydrinks.R;

public class ThemThucDonQLFragment extends Fragment {

    private ImageView imageMon; // ImageView để hiển thị ảnh món
    private ActivityResultLauncher<Intent> resultLauncher; // Launcher để nhận kết quả từ Intent

    public ThemThucDonQLFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_thuc_don_q_l, container, false);

        ImageButton btnBack = view.findViewById(R.id.btnbackthemttkhach);
        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        Button btnSelectImage = view.findViewById(R.id.btn_select_img_mon); // Nút chọn ảnh
        imageMon = view.findViewById(R.id.image_mon); // ImageView hiển thị ảnh

        // Đăng ký launcher để xử lý kết quả chọn ảnh
        registerResult();

        // Thêm sự kiện click cho nút chọn ảnh
        btnSelectImage.setOnClickListener(v -> pickImage());

        return view;
    }

    private void pickImage() {
        // Tạo intent chọn ảnh
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }

    private void registerResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                            Uri imageUri = result.getData().getData();
                            if (imageUri != null) {
                                imageMon.setImageURI(imageUri); // Hiển thị ảnh đã chọn
                            } else {
                                Toast.makeText(getContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
    }
}
