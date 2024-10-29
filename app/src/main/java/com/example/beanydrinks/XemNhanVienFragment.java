package com.example.beanydrinks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link XemNhanVienFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XemNhanVienFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    final Calendar myCalender = Calendar.getInstance();
    EditText edtDate;

    public XemNhanVienFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment XemNhanVienFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static XemNhanVienFragment newInstance(String param1, String param2) {
        XemNhanVienFragment fragment = new XemNhanVienFragment();
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

        View view = inflater.inflate(R.layout.fragment_xem_nhan_vien, container, false);

        ImageButton btnBack = view.findViewById(R.id.btnbackthemttkh);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, new StaffFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        edtDate = view.findViewById(R.id.ngaySinh);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return view;
    }

}