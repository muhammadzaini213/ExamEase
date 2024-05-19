package com.mzopensource.examease.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mzopensource.examease.R;
import com.mzopensource.examease.userdata.UserData;
import com.mzopensource.examease.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    TextView login_message;
    FirebaseAuth auth;
    Button btn_login;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this); //initialize firebase
        auth = FirebaseAuth.getInstance(); //initialize firebase auth

        //get views
        EditText username_textfield = findViewById(R.id.username_textfield);
        EditText password_textfield = findViewById(R.id.password_textfield);

        login_message = findViewById(R.id.login_message);
        btn_login = findViewById(R.id.btn_login);

        TextView forgot_password = findViewById(R.id.forgot_password);
        TextView create_account = findViewById(R.id.create_account);

        //handle forgot password
        forgot_password.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        });

        //handle create account
        create_account.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
        });

        //handle login
        btn_login.setOnClickListener(view -> {
            //check if username and password are not empty
            if (username_textfield.getText().toString().isEmpty() || password_textfield.getText().toString().isEmpty()) {
                login_message.setText("Please fill all fields");
            } else {
                login(username_textfield, password_textfield); //start login if those fields are not empty
            }
        });

        //auto login if email and password are saved in shared preferences
        autologin(username_textfield, password_textfield);
    }

    private void autologin(EditText username_textfield, EditText password_textfield) {
        //set shared preferences
        sharedPreferences = getSharedPreferences("EXAM_EASE", MODE_PRIVATE);

        //get shared preferences values
        String saved_email = sharedPreferences.getString("Username", "");
        String saved_password = sharedPreferences.getString("Password", "");

        //check if email and password are not empty in shared preferences
        if(!saved_email.isEmpty() && !saved_password.isEmpty()) {
            username_textfield.setText(saved_email);
            password_textfield.setText(saved_password);
            login(username_textfield, password_textfield);
        }

    }

    private void login(EditText usernameTextfield, EditText passwordTextfield) {
        // get username and password from textfields
        String username = usernameTextfield.getText().toString();
        String password = passwordTextfield.getText().toString();

        btn_login.setText("Logging in..."); // change login button text to show loading
        btn_login.setEnabled(false); // disable login button for security and reducing spam

        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        login_message.setText("Login successful");

                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            if (user.isEmailVerified()) {
                                // Email is verified, proceed to next activity
                                String email = user.getEmail(); // get user email
                                String uid = user.getUid(); // get user uid

                                // save email and password in shared preferences
                                sharedPreferences.edit().putString("Username", username).apply();
                                sharedPreferences.edit().putString("Password", password).apply();

                                // set user data in UserData static class
                                UserData.setUserData(user);

                                // start main activity
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish(); // finish login activity
                            } else {
                                // Email is not verified, sign out the user and show a message
                                auth.signOut();
                                login_message.setText("Email not verified. Please check your email.");
                                login_message.setTextColor(Color.RED);
                            }

                        }
                    } else {
                        login_message.setText("Login failed");
                        login_message.setTextColor(Color.RED);
                        btn_login.setText("Log In"); // change login button text back to login
                        btn_login.setEnabled(true); // activate login button again if login failed
                    }
                }
        );

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showExitConfirmationDialog();
            }
        });
    }



    private void showExitConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }
}
