package com.mzopensource.examease;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        ImageView btn_exit = findViewById(R.id.constraintLayout)
                .findViewById(R.id.button_exit);
        btn_exit.setOnClickListener(view -> {
            finish();
        });
        Toast.makeText(this, "This feature is coming soon!", Toast.LENGTH_SHORT).show();

        finish();
    }
}
