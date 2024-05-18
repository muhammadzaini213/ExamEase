package com.mzopensource.examease.main;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.view.ContentInfoCompat;

import com.mzopensource.examease.AnnouncementActivity;
import com.mzopensource.examease.AttendanceActivity;
import com.mzopensource.examease.ExamActivity;
import com.mzopensource.examease.PolicyActivity;
import com.mzopensource.examease.R;
import com.mzopensource.examease.SettingsActivity;
import com.mzopensource.examease.webexam.WebExamActivity;

public class MainMenu {

    public MainMenu(LinearLayout main_menu, Context context){

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
            context.startActivity(new Intent(context, ExamActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });

        main_menu_web_exam_button.setOnClickListener(view -> {
            context.startActivity(new Intent(context, WebExamActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });

        main_menu_announcement_button.setOnClickListener(view -> {
            context.startActivity(new Intent(context, AnnouncementActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });

        main_menu_attendance_button.setOnClickListener(view -> {
            context.startActivity(new Intent(context, AttendanceActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });

        main_menu_settings_button.setOnClickListener(view -> {
            context.startActivity(new Intent(context, SettingsActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });

        main_menu_policy_button.setOnClickListener(view -> {
            context.startActivity(new Intent(context, PolicyActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });

    }
}
