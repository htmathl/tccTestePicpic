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

public class AjustesActivity extends AppCompatActivity{

    private Button btnConfigPerfil, btnConfigTratamento, btnConfigNotificacaos, btnConfigAjuda, btnConfigSair;
    private ImageButton miaumiau;

    private FirebaseAuth autenticacao;

    private float x1, y1, x2, y2;
    private static int minDistance = 150;
    private GestureDetector gestureDetector;

    private int position;


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

        Intent intent = new Intent(AjustesActivity.this, TransPerfilActivity.class);


        miaumiau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        btnConfigPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 0;
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });
        btnConfigTratamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 1;
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });
        btnConfigNotificacaos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 2;
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        btnConfigAjuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 3;
                intent.putExtra("position", position);
                startActivity(intent);

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

}