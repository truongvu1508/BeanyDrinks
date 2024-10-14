package com.example.beanydrinks;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

public class orderban_nv extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.orderban_nv, container, false);

        // Tìm button8 và thiết lập sự kiện nhấn
        Button button8 = view.findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để mở them_mon_cho_ban Activity
                Intent intent = new Intent(getActivity(), them_mon_cho_ban.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
