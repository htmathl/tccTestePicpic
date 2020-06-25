package com.example.testepicpic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testepicpic.R;
import com.github.mikephil.charting.charts.LineChart;
import com.example.testepicpic.config.ConfigFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    LineChart graficoGlicemia;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graficoGlicemia = (LineChart) findViewById(R.id.grafico_glicemia);
    }



    public void sair(View view){
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.signOut();
        finish();
    }

}