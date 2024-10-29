package com.example.beanydrinks;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaiKhoanQLFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaiKhoanQLFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaiKhoanQLFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaiKhoanQLFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaiKhoanQLFragment newInstance(String param1, String param2) {
        TaiKhoanQLFragment fragment = new TaiKhoanQLFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tai_khoan_q_l, container, false);
        Button btnXemThongTinQL = view.findViewById(R.id.btnthongtinQL);
        Button btnDoiMK = view.findViewById(R.id.btndoimk);
        Button btnDangXuat = view.findViewById(R.id.button_DangXuat);
        btnXemThongTinQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), XemThongTinQLActivity.class);
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