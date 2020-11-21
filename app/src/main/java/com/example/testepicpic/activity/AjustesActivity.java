package com.example.testepicpic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.testepicpic.R;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.fragment.ConfigAjudaFragment;
import com.example.testepicpic.fragment.ConfigNotificacaoFragment;
import com.example.testepicpic.fragment.ConfigPerfilFragment;
import com.example.testepicpic.fragment.ConfigTratamentoFragment;
import com.google.firebase.auth.FirebaseAuth;

public class AjustesActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    private ConfigPerfilFragment perfilFragment = new ConfigPerfilFragment();
    private ConfigTratamentoFragment tratamentoFragment = new ConfigTratamentoFragment();
    private ConfigNotificacaoFragment notificacaoFragment = new ConfigNotificacaoFragment();
    private ConfigAjudaFragment ajudaFragment = new ConfigAjudaFragment();

    private Button btnConfigPerfil, btnConfigTratamento, btnConfigNotificacaos, btnConfigAjuda, btnConfigSair;
    private ImageButton miaumiau;
    private ConstraintLayout constraintLayout, constraintPerfil;

    private FirebaseAuth autenticacao;

    private float x1, y1, x2, y2;
    private static int minDistance = 150;
    private GestureDetector gestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        btnConfigPerfil = findViewById(R.id.btnConfigPerfil);
        btnConfigTratamento = findViewById(R.id.btnConfigTratamento);
        btnConfigNotificacaos = findViewById(R.id.btnConfigNotificacaos);
        btnConfigAjuda = findViewById(R.id.btnConfigAjuda);
        btnConfigSair = findViewById(R.id.btnConfigSair);
        miaumiau = findViewById(R.id.miaumiua);


        constraintLayout = findViewById(R.id.conteudoConfig);
        constraintPerfil = findViewById(R.id.perfil);


        miaumiau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        btnConfigPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.VISIBLE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.to_down, R.anim.from_top,R.anim.to_down, R.anim.from_top);
                transaction.add(R.id.frameConteudoConfigs, perfilFragment);
                constraintPerfil.setVisibility(View.GONE);
                transaction.commit();
            }
        });
        btnConfigTratamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.VISIBLE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.to_down, R.anim.from_top,R.anim.to_down, R.anim.from_top);
                transaction.add(R.id.frameConteudoConfigs, tratamentoFragment);
                constraintPerfil.setVisibility(View.GONE);
                transaction.commit();
            }
        });
        btnConfigNotificacaos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.VISIBLE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.to_down, R.anim.from_top,R.anim.to_down, R.anim.from_top);
                transaction.add(R.id.frameConteudoConfigs, notificacaoFragment);
                constraintPerfil.setVisibility(View.GONE);
                transaction.commit();
            }
        });

        btnConfigAjuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.VISIBLE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.to_down, R.anim.from_top,R.anim.to_down, R.anim.from_top);
                transaction.add(R.id.frameConteudoConfigs, ajudaFragment);
                constraintPerfil.setVisibility(View.GONE);
                transaction.commit();
            }
        });

        btnConfigSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticacao = ConfigFirebase.getFirebaseAutenticacao();
                autenticacao.signOut();
                startActivity(new Intent(AjustesActivity.this, SliderActivity.class));
                finish();
                LocalBroadcastManager.getInstance(AjustesActivity.this).sendBroadcast(new Intent("fecharTelaMain"));

            }
        });

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gestureDetector.onTouchEvent(event);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                float valueY = y2 - y1;

                if(Math.abs(valueY) > minDistance) {
                    if (y2 > y1) {
                        constraintLayout.setVisibility(View.GONE);
                        constraintPerfil.setVisibility(View.VISIBLE);
                    }
                }
        }

        return super.onTouchEvent(event);

    }
}