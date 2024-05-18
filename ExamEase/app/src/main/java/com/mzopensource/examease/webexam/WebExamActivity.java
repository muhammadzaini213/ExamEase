package com.mzopensource.examease.webexam;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.HalfFloat;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mzopensource.examease.R;

public class WebExamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_exam);

        showUrlDialog();


        ImageView btn_exit = findViewById(R.id.constraintLayout)
                .findViewById(R.id.button_exit);
        btn_exit.setOnClickListener(view -> {
            finish();
        });

        new Handler().postDelayed(() -> detect = true, 1000);
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

    boolean detect;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (detect) {
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
