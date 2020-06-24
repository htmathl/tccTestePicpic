package com.example.testepicpic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testepicpic.R;
import com.github.mikephil.charting.charts.LineChart;

public class MainActivity extends AppCompatActivity {

    LineChart graficoGlicemia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graficoGlicemia = (LineChart) findViewById(R.id.grafico_glicemia);
    }

    public void onClickMiau(View view){
        Intent matheus  = new Intent(MainActivity.this , tela_cadastro1.class);
        startActivity(matheus);

    }
}