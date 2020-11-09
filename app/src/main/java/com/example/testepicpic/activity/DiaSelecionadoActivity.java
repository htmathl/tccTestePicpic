package com.example.testepicpic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testepicpic.R;

import java.time.Month;

public class DiaSelecionadoActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "shareData";
    private String strMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_selecionado);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int today = preferences.getInt("today", 0);
        int month = preferences.getInt("month", 0);
        int year = preferences.getInt("year", 0);

        TextView txtData = (TextView) findViewById(R.id.textView28);

        switch (month) {

            case 1:
                strMonth = "jan";
                break;
            case 2:
                strMonth = "fev";
                break;
            case 3:
                strMonth = "mar";
                break;
            case 4:
                strMonth = "abr";
                break;
            case 5:
                strMonth = "mai";
                break;
            case 6:
                strMonth = "jun";
                break;
            case 7:
                strMonth = "jul";
                break;
            case 8:
                strMonth = "ago";
                break;
            case 9:
                strMonth = "set";
                break;
            case 10:
                strMonth = "out";
                break;
            case 11:
                strMonth = "nov";
                break;
            case 12:
                strMonth = "dez";
                break;
        }

        String strData = String.valueOf(year) + "\n" + String.valueOf(today) + " " + strMonth;

        txtData.setText(strData);

        Toast.makeText(this, String.valueOf(today), Toast.LENGTH_SHORT).show();

    }
}