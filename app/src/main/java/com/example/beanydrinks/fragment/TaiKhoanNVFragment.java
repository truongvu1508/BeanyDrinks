package com.example.beanydrinks.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.beanydrinks.R;
import com.example.beanydrinks.activity.GioiThieuActivity;
import com.example.beanydrinks.activity.UI_Login;
import com.example.beanydrinks.activity.DoiMatKhauActivity;
import com.example.beanydrinks.activity.ThongTinNVActivity;


public class TaiKhoanNVFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tai_khoan_n_v, container, false);
        Button btnXemThongTinQL = view.findViewById(R.id.btnthongtinNV);
        Button btnDoiMK = view.findViewById(R.id.btndoimk);
        Button btnDangXuat = view.findViewById(R.id.button_DangXuat);
        Button btnGioiThieu = view.findViewById(R.id.btnGioiThieu);
        btnXemThongTinQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThongTinNVActivity.class);
                startActivity(intent);
            }
        });
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DoiMatKhauActivity.class);
                startActivity(intent);
            }
        });
        btnGioiThieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GioiThieuActivity.class);
                startActivity(intent);
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View customView = inflater.inflate(R.layout.dialog_confirm_logout, null);

                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setView(customView)
                        .create();

                Button btnYes = customView.findViewById(R.id.btnYes);
                btnYes.setOnClickListener(view -> {
                    Intent intent = new Intent(getActivity(), UI_Login.class);
                    startActivity(intent);
                    getActivity().finish();
                    dialog.dismiss();
                });

                Button btnNo = customView.findViewById(R.id.btnNo);
                btnNo.setOnClickListener(view -> {
                    dialog.dismiss();
                });

                dialog.show();
            }
        });
        return view;
    }
}