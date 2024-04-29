package com.mzopensource.examease;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout main_title = findViewById(R.id.main_title);
        ImageView main_title_exit = main_title.findViewById(R.id.main_title_exit);
        TextView main_title_titletext = main_title.findViewById(R.id.main_title_titletext);
        ImageView main_title_profile = main_title.findViewById(R.id.main_title_profile);


    }
}
