package com.mzopensource.examease.login;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.mzopensource.examease.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    FirebaseAuth auth;
    TextView forgot_password_message;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        auth = FirebaseAuth.getInstance();
        forgot_password_message = findViewById(R.id.forgot_password_message);
        EditText user_account = findViewById(R.id.user_account);

        findViewById(R.id.exit_forgot_password).setOnClickListener(view -> {
            finish();
        });

        //handle send_email button
        findViewById(R.id.send_email).setOnClickListener(view -> {
            String account = user_account.getText().toString(); // get user account

            // check if account is not empty
            if (!account.isEmpty()) {
                sendPasswordResetEmail(account); // send password reset email
            } else {
                // if account is empty show error message
                forgot_password_message.setText("Account not found");
                forgot_password_message.setTextColor(Color.RED);
            }

        });

    }

    private void sendPasswordResetEmail(String email) {
        // send password reset email
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // if email is sent show success message
                forgot_password_message.setText("Email has been sent");
                forgot_password_message.setTextColor(Color.GREEN);
            } else {
                // if email cannot be sent show error message
                forgot_password_message.setText("Please enter an valid email address");
                forgot_password_message.setTextColor(Color.RED);
            }
        });
    }
}
