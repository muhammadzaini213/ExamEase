package com.mzopensource.examease.login;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mzopensource.examease.R;

public class CreateAccountActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextView create_account_message;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        auth = FirebaseAuth.getInstance();
        create_account_message = findViewById(R.id.create_account_message);

        findViewById(R.id.exit_create_account).setOnClickListener(view -> {
            finish();
        });

        // Get the input fields
        EditText email_input = findViewById(R.id.username_input);
        EditText password_input = findViewById(R.id.password_input);

        findViewById(R.id.btn_send_email).setOnClickListener(v -> {
            // Get the input values
            String email = email_input.getText().toString();
            String password = password_input.getText().toString();

            // Validate the input
            if (email.isEmpty() || password.isEmpty()) {
                create_account_message.setText("Please fill in all fields");
            } else {
                // if valid, create account
                createNewAccount(email, password);
            }
        });

    }


    private void createNewAccount(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Send verification email
                FirebaseUser user = auth.getCurrentUser();
                create_account_message.setTextColor(Color.GREEN);
                if (user != null) {
                    user.sendEmailVerification()
                            .addOnCompleteListener(verificationTask -> {
                                if (verificationTask.isSuccessful()) {
                                    // Verification email sent successfully
                                    create_account_message.setText("Account created successfully! Please check your email for verification.");
                                    create_account_message.setTextColor(Color.GREEN);
                                } else {
                                    // Failed to send verification email
                                    create_account_message.setText("Failed to send verification email.");
                                    create_account_message.setTextColor(Color.RED);
                                }
                            });
                }
            } else {
                // If sign in fails, display a message to the user.
                create_account_message.setText("Email already used");
                create_account_message.setTextColor(Color.RED);
            }
        });
    }
}
