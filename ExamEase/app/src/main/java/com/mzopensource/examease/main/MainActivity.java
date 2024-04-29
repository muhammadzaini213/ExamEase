package com.mzopensource.examease;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //title bar
        ConstraintLayout main_title = findViewById(R.id.main_title);
        ImageView main_title_exit = main_title.findViewById(R.id.main_title_exit);
        TextView main_title_titletext = main_title.findViewById(R.id.main_title_titletext);
        ImageView main_title_profile = main_title.findViewById(R.id.main_title_profile);

        LinearLayout main_title_info = main_title.findViewById(R.id.main_title_info);
        TextView main_title_info_school = main_title_info.findViewById(R.id.main_title_info_school);
        TextView main_title_info_identity = main_title_info.findViewById(R.id.main_title_info_identity);

        //main menu
        LinearLayout main_menu = findViewById(R.id.main_menu);
        LinearLayout main_menu_row1 = main_menu.findViewById(R.id.main_menu_row1);
        LinearLayout main_menu_row2 = main_menu.findViewById(R.id.main_menu_row2);
        LinearLayout main_menu_row3 = main_menu.findViewById(R.id.main_menu_row3);

        LinearLayout main_menu_exam = main_menu_row1.findViewById(R.id.main_menu_exam);
        ImageView main_menu_exam_button = main_menu_row1.findViewById(R.id.main_menu_exam_button);

        LinearLayout main_menu_web_exam = main_menu_row1.findViewById(R.id.main_menu_web_exam);
        ImageView main_menu_web_exam_button = main_menu_row1.findViewById(R.id.main_menu_web_exam_button);

        LinearLayout main_menu_announcement = main_menu_row2.findViewById(R.id.main_menu_announcement);
        ImageView main_menu_announcement_button = main_menu_row2.findViewById(R.id.main_menu_announcement_button);

        LinearLayout main_menu_attendance = main_menu_row2.findViewById(R.id.main_menu_attendance);
        ImageView main_menu_attendance_button = main_menu_row2.findViewById(R.id.main_menu_attendance_button);

        LinearLayout main_menu_settings = main_menu_row3.findViewById(R.id.main_menu_settings);
        ImageView main_menu_settings_button = main_menu_row3.findViewById(R.id.main_menu_settings_button);

        LinearLayout main_menu_policy = main_menu_row3.findViewById(R.id.main_menu_policy);
        ImageView main_menu_policy_button = main_menu_row3.findViewById(R.id.main_menu_policy_button);

        main_menu_exam_button.setOnClickListener(view -> {
        });

        main_menu_web_exam_button.setOnClickListener(view -> {
        });

        main_menu_announcement_button.setOnClickListener(view -> {
        });

        main_menu_attendance_button.setOnClickListener(view -> {
        });

        main_menu_settings_button.setOnClickListener(view -> {
        });

        main_menu_policy_button.setOnClickListener(view -> {
        });

    }
}
