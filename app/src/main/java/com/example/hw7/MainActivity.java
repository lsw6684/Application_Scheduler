package com.example.hw7;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    CalendarView cal;
    TextView outsider, insider;
    EditText et;
    Button reflect, change;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outsider = (TextView) findViewById(R.id.outsider);
        insider = (TextView) findViewById(R.id.insider);
        et = (EditText) findViewById(R.id.et);
        reflect = (Button) findViewById(R.id.reflect);
        change = (Button) findViewById(R.id.change);
        cal = (CalendarView) findViewById(R.id.cal);
        Calendar cal2 = Calendar.getInstance();
        int Year = cal2.get(Calendar.YEAR);
        int Month = cal2.get(Calendar.MONTH);
        int Day = cal2.get(Calendar.DAY_OF_MONTH);
        fileName = Integer.toString(Year) + "_"
                + Integer.toString(Month + 1) + "_"
                + Integer.toString(Day) + ".txt";
        String str = readDiary(fileName);
        outsider.setText(str);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(null);
                reflect.setVisibility(View.VISIBLE);
                et.setVisibility(View.VISIBLE);
                insider.setVisibility(View.VISIBLE);
                change.setVisibility(View.INVISIBLE);
            }
        });
        reflect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setVisibility(View.INVISIBLE);
                reflect.setVisibility(View.INVISIBLE);
                change.setVisibility(View.VISIBLE);
                try {
                    FileOutputStream outFs = openFileOutput(fileName,
                            Context.MODE_PRIVATE);
                    String str = et.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                } catch (IOException e) {
                }
                String str = readDiary(fileName);
                outsider.setText(str);
            }
        });
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int monthOfYear, int dayOfMonth) {
                fileName = Integer.toString(year) + "_"
                        + Integer.toString(monthOfYear + 1) + "_"
                        + Integer.toString(dayOfMonth) + ".txt";
                String str = readDiary(fileName);
                outsider.setText(str);
                change.setVisibility(View.VISIBLE);
                et.setVisibility(View.INVISIBLE);
                reflect.setVisibility(View.INVISIBLE);
            }
        });
    }

    String readDiary(String fName) {
        String diaryStr = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput(fName);
            byte[] txt = new byte[300];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();
        } catch (IOException e) {
            outsider.setHint("일정이 없습니다.");
        }
        return diaryStr;
    }
}