package com.example.testepicpic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.testepicpic.R;
import com.example.testepicpic.fragment.CalendarFragment;
import com.example.testepicpic.fragment.OverviewFragment;
import com.example.testepicpic.fragment.PerfilFragment;
import com.example.testepicpic.fragment.PosClickMaisFragment;
import com.example.testepicpic.fragment.RelatorioFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.example.testepicpic.config.ConfigFirebase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth autenticacao;

    private ImageButton fabButton;
    private OvershootInterpolator interpolator = new OvershootInterpolator();
    private boolean menuAberto = true;

    private BottomNavigationView bottomNavigationView;
    private static final String TAG = "MainActivity";
    private OverviewFragment overviewFragment = new OverviewFragment();
    private CalendarFragment calendarFragment = new CalendarFragment();
    private RelatorioFragment relatorioFragment = new RelatorioFragment();
    private PerfilFragment perfilFragment = new PerfilFragment();

    private LineChart graficoGlicemia;
    private PosClickMaisFragment clickMaisFragment=  new PosClickMaisFragment();

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
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace(R.id.substituicao,clickMaisFragment).commit();

                } else {
                    fabButton.animate().setInterpolator(interpolator).rotation(0f).setDuration(500).start();
                    menuAberto =! menuAberto;
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace(R.id.substituicao,overviewFragment).commit();


                }
            }
        });


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.item_overview);

    }

    public void sair(View view){
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.signOut();
        startActivity(new Intent(this, SliderActivity.class));
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        TextView txtOndaPrincipal = (TextView) findViewById(R.id.txt_onda_principal);
        FrameLayout ondaPrincipal = (FrameLayout) findViewById(R.id.onda_principal);
        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        ImageButton fabButton = (ImageButton) findViewById(R.id.fab_button);

        switch(item.getItemId()) {
            case R.id.item_overview:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.substituicao, overviewFragment).commit();
                txtOndaPrincipal.setText("Visão geral");
                return true;

            case R.id.item_calendario:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.substituicao, calendarFragment).commit();
                txtOndaPrincipal.setText("Calendário");
                return true;

            case R.id.item_reltorio:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.substituicao, relatorioFragment).commit();
                txtOndaPrincipal.setText("Relatório");
                return true;

            case R.id.item_perfil:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.substituicao, perfilFragment).commit();
                txtOndaPrincipal.setText("Perfil");
                return true;
        }

        return false;
    }
}