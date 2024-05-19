package com.mzopensource.examease.webexam;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mzopensource.examease.R;
import com.mzopensource.examease.userdata.UserData;

public class WebExamActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser user;
    boolean detect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase instances
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = UserData.getUserData();

        if (user != null) {
            // Set the content view for logged-in users
            setContentView(R.layout.activity_web_exam_admin);
            ConstraintLayout container = findViewById(R.id.container);

            // Initialize input fields
            EditText test_name_input = container.findViewById(R.id.test_name_input);
            EditText website_input = container.findViewById(R.id.website_input);

            //input fields for date and time picker
            LinearLayout date_and_time = container.findViewById(R.id.date_and_time);
            TextView date_input = date_and_time.findViewById(R.id.date_input);
            TextView time_input = date_and_time.findViewById(R.id.time_input);
            new WebExamDatePicker(date_and_time, this);


            new WebExamUpload(container, this);

            // Initialize buttons
            Button advanced_options = container.findViewById(R.id.advanced_options);
            Button save = container.findViewById(R.id.save_btn);
            Button save_and_publish = findViewById(R.id.save_and_publish);

            String uid = user.getUid();
            DocumentReference usersDocRef = db.collection("users").document(uid);

            usersDocRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Document exists, read the data
                        // readUserData(); // Uncomment and implement this method
                    } else {
                        // Document does not exist, create a new one
                        // inputSchoolNameDialog(usersDocRef); // Uncomment and implement this method
                    }
                } else {
                    Toast.makeText(this, "Please verify your email to access this feature.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Set the content view for users who are not logged in
            setContentView(R.layout.activity_web_exam);
            ImageView btn_exit = findViewById(R.id.constraintLayout).findViewById(R.id.button_exit);
            btn_exit.setOnClickListener(view -> finish());
            // Show URL dialog
            showUrlDialog(); // Ensure this method is implemented
        }


        ImageView btn_exit = findViewById(R.id.button_exit);
        btn_exit.setOnClickListener(view -> finish());


        // Use a handler to set detect to true after a delay
        new Handler().postDelayed(() -> detect = true, 1000); // Ensure detect is declared and used properly
    }


    private void readUserData() {
        String uid = user.getUid();
        // Reference to the user's document
        DocumentReference userDocRef = db.collection("users").document(uid);
        userDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Document exists, handle the retrieved data
                    String schoolId = document.getString("School ID");


                } else {
                    Toast.makeText(this, "No user data found.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Failed to retrieve data.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showUrlDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_web_exam);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

        final EditText input = dialog.findViewById(R.id.url_input);

        Button okButton = dialog.findViewById(R.id.enter_button);
        okButton.setOnClickListener(v -> {
            String inputText = input.getText().toString();
            WebView webView = findViewById(R.id.web_exam_webview);
            setWebView(webView, inputText);
            dialog.dismiss();
        });
        dialog.show();
    }


    private void setWebView(WebView webView, String url) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowContentAccess(true);
        webView.setKeepScreenOn(true);
        webView.loadUrl(url);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (detect && user == null) {
            if (!hasFocus) {
                detect = false;
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WebExamActivity.this);
                alertDialogBuilder.setTitle("Keluar");
                alertDialogBuilder.setTitle("Deteksi!")
                        .setMessage("Yakin keluar dari aplikasi ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", (dialog, id) -> System.exit(0))
                        .setNegativeButton("Tidak", (dialog, id) -> {
                            dialog.cancel();
                            detect = true;
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
                new CountDownTimer(5000, 1000) {
                    @Override
                    public void onTick(long l) {
                        alertDialog.setMessage("Waktu tersisa: " + (int) (l / 1000));
                    }

                    @Override
                    public void onFinish() {
                        alertDialog.setMessage("Waktu habis!");
                    }
                }.start();

            }
        }

        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
