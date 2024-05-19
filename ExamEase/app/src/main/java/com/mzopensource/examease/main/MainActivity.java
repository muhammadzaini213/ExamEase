package com.mzopensource.examease.main;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.mzopensource.examease.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //title bar
        ConstraintLayout main_title = findViewById(R.id.main_title);
        MainTitle title = new MainTitle();
        title.init(main_title, this, this);

        new Handler().postDelayed(() -> {
            // handle menu input after five second
            LinearLayout main_menu = findViewById(R.id.main_menu);
            MainMenu menu = new MainMenu(main_menu, this);
        }, 5000);


    }
}
