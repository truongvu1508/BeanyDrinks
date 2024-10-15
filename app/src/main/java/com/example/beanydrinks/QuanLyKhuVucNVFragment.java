package com.example.beanydrinks;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class QuanLyKhuVucNVFragment extends Fragment {

    public QuanLyKhuVucNVFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quan_ly_khu_vuc_nv, container, false);

        // Find the TextViews and Button by their IDs
        TextView banVip01 = view.findViewById(R.id.banVip01);
        TextView suaban = view.findViewById(R.id.suaban); // Assuming you have an ID for this
        TextView xoaBan = view.findViewById(R.id.xoaban); // ID for the delete action

        // Set an OnClickListener to open OrderBanBV activity
        banVip01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start OrderBanBV activity
                Intent intent = new Intent(getActivity(), orderban_nv.class);
                startActivity(intent);
            }
        });

        // Set an OnClickListener for the "Sửa Bàn" TextView to show the edit dialog
        suaban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        // Set an OnClickListener for the "Xóa Bàn" TextView to show the delete dialog
        xoaBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

        return view;
    }

    private void showEditDialog() {
        // Inflate the dialog layout
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.layout_dialog_suaban, null);

        // Initialize the EditTexts and Buttons from the dialog layout
        EditText editTableName = dialogView.findViewById(R.id.edit_table_name); // Matches with your dialog XML
        EditText editArea = dialogView.findViewById(R.id.edit_area); // Matches with your dialog XML
        Button cancelButton = dialogView.findViewById(R.id.button_huy); // Matches with your dialog XML
        Button editButton = dialogView.findViewById(R.id.button_sua); // Matches with your dialog XML

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView)
                .setCancelable(true);

        // Create the AlertDialog
        AlertDialog alertDialog = builder.create();

        // Set cancel button click listener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog
                alertDialog.dismiss();
            }
        });

        // Set edit button click listener
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the edit action
                String tableName = editTableName.getText().toString();
                String area = editArea.getText().toString();
                // Perform your edit operation here (e.g., update data in the database)
                // ...
                alertDialog.dismiss();
            }
        });

        // Show the dialog
        alertDialog.show();
    }

    private void showDeleteDialog() {
        // Inflate the custom dialog layout
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.layout_dialog_xoaban, null);

        // Initialize the buttons from the custom layout
        Button cancelButton = dialogView.findViewById(R.id.button_cancel);
        Button confirmButton = dialogView.findViewById(R.id.button_confirm);

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView)
                .setCancelable(true);

        // Create the AlertDialog
        AlertDialog alertDialog = builder.create();

        // Set cancel button click listener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog
                alertDialog.dismiss();
            }
        });

        // Set confirm button click listener
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the delete action here
                // For example, delete from database
                // ...
                Toast.makeText(getActivity(), "Bàn đã được xóa.", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss(); // Dismiss the dialog after confirmation
            }
        });

        // Show the custom dialog
        alertDialog.show();
    }

}