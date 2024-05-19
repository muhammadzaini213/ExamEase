package com.mzopensource.examease.webexam;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mzopensource.examease.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WebExamDatePicker {

    Calendar calendar;
    boolean isDatePickerShowing;
    boolean isTimePickerShowing;

    public WebExamDatePicker(LinearLayout date_and_time, Context context) {
        TextView date_input = date_and_time.findViewById(R.id.date_input);
        TextView time_input = date_and_time.findViewById(R.id.time_input);

        calendar = Calendar.getInstance();
        isDatePickerShowing = false;
        isTimePickerShowing = false;

        //set date picker
        date_input.setOnClickListener(v -> {
            if (!isDatePickerShowing) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            calendar.set(Calendar.YEAR, selectedYear);
                            calendar.set(Calendar.MONTH, selectedMonth);
                            calendar.set(Calendar.DAY_OF_MONTH, selectedDay);

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.US);
                            date_input.setText(sdf.format(calendar.getTime()));
                            isDatePickerShowing = false;

                        }, year, month, day);
                datePickerDialog.setOnDismissListener(dialog -> isTimePickerShowing = false);
                isDatePickerShowing = true;
                datePickerDialog.show();
            }
        });

        //set time picker
        time_input.setOnClickListener(v -> {
            if (!isTimePickerShowing) {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                        (view, selectedHour, selectedMinute) -> {
                            calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                            calendar.set(Calendar.MINUTE, selectedMinute);
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
                            time_input.setText(sdf.format(calendar.getTime()));
                            isTimePickerShowing = false; // Reset the flag
                        }, hour, minute, true); // Set true for 24-hour view

                timePickerDialog.setOnDismissListener(dialog -> isTimePickerShowing = false); // Reset the flag
                isTimePickerShowing = true; // Set the flag
                timePickerDialog.show();
            }
        });
    }
}
