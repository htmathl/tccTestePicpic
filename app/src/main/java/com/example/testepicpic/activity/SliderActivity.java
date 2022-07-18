package com.example.testepicpic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.testepicpic.R;
import com.example.testepicpic.adapter.SlideAdapter;
import com.example.testepicpic.config.ConfigFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.scwang.wave.MultiWaveHeader;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SliderActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    private ViewPager viewPager;
    private LinearLayout linearLayout;

    private SlideAdapter slideAdapter;

    public TextView[] bolinhas;

    private Button btnSeguinte;

    private int pagina;

    MultiWaveHeader waveHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        waveHeader = findViewById(R.id.wave);

        waveHeader.setVelocity(2);
        waveHeader.setProgress(1);
        //waveHeader.isRunning();
        waveHeader.setWaveHeight(50);
        waveHeader.setStartColor(Color.rgb(107, 82, 175));
        waveHeader.setCloseColor(Color.rgb(107, 82, 175));


        viewPager = findViewById(R.id.vpSlider);
        linearLayout = findViewById(R.id.linearSlider);

        btnSeguinte = findViewById(R.id.btnSeguinte);

        slideAdapter = new SlideAdapter(this);

        viewPager.setAdapter(slideAdapter);

        addBolinhas(0);

        viewPager.addOnPageChangeListener(viewListener);

        btnSeguinte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SliderActivity.this, PrincipalActivity.class));
                finish();
            }
        });

    }

    public void addBolinhas(int j) {

        bolinhas = new TextView[4];
        linearLayout.removeAllViews();

        for(int i = 0; i < bolinhas.length; i++) {

            bolinhas[i] = new TextView(this);
            bolinhas[i].setText(Html.fromHtml("&#8226"));
            bolinhas[i].setTextSize(45);
            bolinhas[i].setTextColor(getResources().getColor(R.color.colorSlider));

            linearLayout.addView(bolinhas[i]);
        }

        if(bolinhas.length > 0)
            bolinhas[j].setTextColor(getResources().getColor(R.color.colorPrimary));

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addBolinhas(position);

            pagina = position;

            if(position == 3) {
                btnSeguinte.setEnabled(true);
                btnSeguinte.setVisibility(View.VISIBLE);

            } else {
                btnSeguinte.setEnabled(false);
                btnSeguinte.setVisibility(View.GONE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        verificarUserLogado();
    }

    public void verificarUserLogado() {
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null) {
            abrirTelaPrincipal();
        }
    }

    public void abrirTelaPrincipal() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}