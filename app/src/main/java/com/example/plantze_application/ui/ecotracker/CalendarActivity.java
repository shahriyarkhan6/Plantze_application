package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    private Calendar calendar = Calendar.getInstance();
    private TextView monthYearText;
    private LinearLayout firstRow, secondRow, thirdRow, fourthRow, fifthRow, currentRow;
    private String[] monthNames = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };
    private int month = calendar.get(Calendar.MONTH), year = calendar.get(Calendar.YEAR);
    private String s_month = monthNames[month];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_calendar);

        calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        monthYearText = findViewById(R.id.monthYearText);
        firstRow = findViewById(R.id.firstRow);
        secondRow = findViewById(R.id.secondRow);
        thirdRow = findViewById(R.id.thirdRow);
        fourthRow = findViewById(R.id.fourthRow);
        fifthRow = findViewById(R.id.fifthRow);

        updateCalendarView();

        Button prevMonthButton = findViewById(R.id.prevMonthButton);
        Button nextMonthButton = findViewById(R.id.nextMonthButton);

        prevMonthButton.setOnClickListener(v -> navigateMonth(false)); // Move to previous month
        nextMonthButton.setOnClickListener(v -> navigateMonth(true)); // Move to next month
    }
    private void navigateMonth(boolean forward) {
        if (forward) {
            month++;
            if (month > 11) {
                month = 0;
                year++;
            }
        } else {
            month--;
            if (month < 0) {
                month = 11;
                year--;
            }
        }
        updateCalendarView();
    }

    private void updateCalendarView() {
        s_month = monthNames[month];
        monthYearText.setText(s_month + " " + year);
        int numberOfDays = getDaysInMonth(year, month);
        setCalendarRows(numberOfDays);
    }
    private void setCalendarRows(int numberOfDays) {
        // Clear previous views
        firstRow.removeAllViews();
        secondRow.removeAllViews();
        thirdRow.removeAllViews();
        fourthRow.removeAllViews();
        fifthRow.removeAllViews();

        for (int i = 1; i <= numberOfDays; i++) {
            String currentDate = s_month + " " + i + ", " + year;

            Button button = new Button(this);
            button.setText(String.valueOf(i));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(140, 120);
            button.setLayoutParams(params);

            button.setOnClickListener(v -> {
                Intent intent = new Intent(CalendarActivity.this, DateActivity.class);
                intent.putExtra("date", currentDate);
                startActivity(intent);
            });

            if (i <= 7) currentRow = firstRow;
            else if (i <= 14) currentRow = secondRow;
            else if (i <= 21) currentRow = thirdRow;
            else if (i <= 28) currentRow = fourthRow;
            else currentRow = fifthRow;

            currentRow.addView(button);
        }
    }
    private int getDaysInMonth(int year, int month) {
        calendar.set(year, month, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
