package com.example.testepicpic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

import com.example.testepicpic.R;
import com.github.mikephil.charting.charts.LineChart;
import com.example.testepicpic.config.ConfigFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    LineChart graficoGlicemia;

    private FrameLayout fabButton;
    OvershootInterpolator interpolator = new OvershootInterpolator();
    private boolean menuAberto = true;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graficoGlicemia = (LineChart) findViewById(R.id.grafico_glicemia);

        fabButton = findViewById(R.id.fab_button);


        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuAberto) {
                    fabButton.animate().setInterpolator(interpolator).rotation(45f).setDuration(500).start();
                    menuAberto =! menuAberto;
                } else {
                    fabButton.animate().setInterpolator(interpolator).rotation(0f).setDuration(500).start();
                    menuAberto =! menuAberto;
                }
            }
        });
    }

    public void sair(View view){
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.signOut();
        startActivity(new Intent(this, SliderActivity.class));
        finish();
    }


}