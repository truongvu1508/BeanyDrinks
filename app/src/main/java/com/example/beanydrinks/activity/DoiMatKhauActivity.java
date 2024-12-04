package com.example.beanydrinks.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.beanydrinks.R;
import com.example.beanydrinks.model.UserSession;
import com.example.beanydrinks.model.NhanVien;
import com.example.beanydrinks.ultil.Server;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class DoiMatKhauActivity extends AppCompatActivity {

    private TextInputEditText edtCurrentPassword, edtNewPassword, edtConfirmPassword;
    private Button btnUpdatePassword, btnCancel;
    private ImageButton btnBack;
    private UserSession userSession;
    TextInputLayout textInputLayout1, textInputLayout2, textInputLayout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        // Initialize UserSession with context
        userSession = UserSession.getInstance(this);

        bindUIComponents();
        textInputLayout1 = findViewById(R.id.textInputLayout1);
        textInputLayout2 = findViewById(R.id.textInputLayout2);
        textInputLayout3 = findViewById(R.id.textInputLayout3);

        edtCurrentPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = charSequence.toString();
                if (password.length() >= 2) {
                    if (password.matches("\\d+")) { // Check if password contains only numbers
                        textInputLayout1.setHelperText("Mật khẩu hợp lệ");
                        textInputLayout1.setError("");
                    } else {
                        textInputLayout1.setError("Mật khẩu phải là số");
                    }
                } else {
                    textInputLayout1.setHelperText("Mật khẩu phải gồm ít nhất 2 chữ số");
                    textInputLayout1.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        edtNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = charSequence.toString();
                if (password.length() >= 2) {
                    if (password.matches("\\d+")) { // Check if password contains only numbers
                        textInputLayout2.setHelperText("Mật khẩu hợp lệ");
                        textInputLayout2.setError("");
                    } else {
                        textInputLayout2.setError("Mật khẩu phải là số");
                    }
                } else {
                    textInputLayout2.setHelperText("Mật khẩu phải gồm ít nhất 2 chữ số");
                    textInputLayout2.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        edtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = charSequence.toString();
                if (password.length() >= 2) {
                    if (password.matches("\\d+")) { // Check if password contains only numbers
                        textInputLayout3.setHelperText("Mật khẩu hợp lệ");
                        textInputLayout3.setError("");
                    } else {
                        textInputLayout3.setError("Mật khẩu phải là số");
                    }
                } else {
                    textInputLayout3.setHelperText("Mật khẩu phải gồm ít nhất 2 chữ số");
                    textInputLayout3.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        setupListeners();
        loadCurrentPassword(); // Load the current password
    }

    private void loadCurrentPassword() {
        // Get user info from UserSession
        NhanVien currentUser = userSession.getCurrentUser();
        if (currentUser != null) {
            String currentPassword = currentUser.getMatKhau();
            if (currentPassword != null) {
                edtCurrentPassword.setText(currentPassword);
            } else {
                Toast.makeText(this, "Current password not available!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User information not found!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updatePassword() {
        String newPassword = edtNewPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        if (newPassword.isEmpty()) {
            Toast.makeText(this, "Please enter a new password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        int idNhanVien = userSession.getCurrentUser().getIdNhanVien();

        sendUpdatePasswordRequest(idNhanVien, newPassword);
    }

    private void sendUpdatePasswordRequest(int idNhanVien, String newPassword) {
        String url = Server.DuongDanUpdatePassword;

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("idNhanVien", idNhanVien);
            jsonBody.put("matKhau", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error while creating data to send!", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        String message = response.getString("message");
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        if (success) {
                            NhanVien currentUser = userSession.getCurrentUser();
                            if (currentUser != null) {
                                currentUser.setMatKhau(newPassword);
                                userSession.saveUser(currentUser);
                            }
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Invalid response!", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    if (error.networkResponse != null) {
                        String errorData = new String(error.networkResponse.data);
                        Toast.makeText(this, "Server Error: " + errorData, Toast.LENGTH_LONG).show();
                        Log.e("VOLLEY_ERROR", errorData);
                    } else {
                        Toast.makeText(this, "Network or server error!", Toast.LENGTH_SHORT).show();
                        Log.e("VOLLEY_ERROR", error.toString());
                    }
                }
        );

        queue.add(jsonObjectRequest);
    }

    private void bindUIComponents() {
        edtCurrentPassword = findViewById(R.id.editTextCurrentPassword); // Correct ID
        edtNewPassword = findViewById(R.id.editTextNewPassword); // Correct ID
        edtConfirmPassword = findViewById(R.id.editTextConfirmPassword); // Correct ID
        btnUpdatePassword = findViewById(R.id.button_DoiMK);
        btnCancel = findViewById(R.id.button_Cancel);
        btnBack = findViewById(R.id.btnbackthemttkhach);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnCancel.setOnClickListener(v -> finish());
        btnUpdatePassword.setOnClickListener(v -> updatePassword());
    }

    // Function to toggle between password visibility and hidden
    private void togglePasswordVisibility(TextInputEditText editText) {
        int inputType = editText.getInputType();

        // If the password is hidden, show it
        if (inputType == android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            // If the password is visible, hide it
            editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

        // Ensure the cursor stays at the correct position after changing inputType
        editText.setSelection(editText.getText().length());
    }
}
