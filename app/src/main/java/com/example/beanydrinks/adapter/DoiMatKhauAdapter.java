package com.example.beanydrinks.adapter;

import com.google.android.material.textfield.TextInputEditText;

public class DoiMatKhauAdapter {
    private TextInputEditText currentPasswordEditText;
    private TextInputEditText newPasswordEditText;
    private TextInputEditText confirmPasswordEditText;

    // Constructor to bind views
    public DoiMatKhauAdapter(TextInputEditText currentPasswordEditText, TextInputEditText newPasswordEditText, TextInputEditText confirmPasswordEditText) {
        this.currentPasswordEditText = currentPasswordEditText;
        this.newPasswordEditText = newPasswordEditText;
        this.confirmPasswordEditText = confirmPasswordEditText;
    }

    // Get current password
    public String getCurrentPassword() {
        return currentPasswordEditText.getText().toString().trim();
    }

    // Get new password
    public String getNewPassword() {
        return newPasswordEditText.getText().toString().trim();
    }

    // Get confirm password
    public String getConfirmPassword() {
        return confirmPasswordEditText.getText().toString().trim();
    }

    // Validate input fields
    public boolean validateInput() {
        String currentPassword = getCurrentPassword();
        String newPassword = getNewPassword();
        String confirmPassword = getConfirmPassword();

        // Validate current password
        if (currentPassword.isEmpty()) {
            currentPasswordEditText.setError("Vui lòng nhập mật khẩu hiện tại!");
            return false;
        }

        // Validate new password
        if (newPassword.isEmpty()) {
            newPasswordEditText.setError("Vui lòng nhập mật khẩu mới!");
            return false;
        }

        // Validate confirm password
        if (confirmPassword.isEmpty()) {
            confirmPasswordEditText.setError("Vui lòng xác nhận mật khẩu mới!");
            return false;
        }

        // Check if new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Mật khẩu xác nhận không khớp!");
            return false;
        }

        return true;
    }

    // Clear error messages
    public void clearErrors() {
        currentPasswordEditText.setError(null);
        newPasswordEditText.setError(null);
        confirmPasswordEditText.setError(null);
    }
}
