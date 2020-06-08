package com.example.testepicpic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LineChart graficoGlicemia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graficoGlicemia = (LineChart) findViewById(R.id.grafico_glicemia);


    }
}