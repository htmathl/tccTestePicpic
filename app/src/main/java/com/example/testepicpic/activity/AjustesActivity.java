package com.example.testepicpic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.testepicpic.R;
import com.example.testepicpic.fragment.ConfigAjudaFragment;
import com.example.testepicpic.fragment.ConfigBackupFragment;
import com.example.testepicpic.fragment.ConfigNotificacaoFragment;
import com.example.testepicpic.fragment.ConfigPerfilFragment;
import com.example.testepicpic.fragment.ConfigTratamentoFragment;

public class AjustesActivity extends AppCompatActivity {

    private ConfigPerfilFragment perfilFragment = new ConfigPerfilFragment();
    private ConfigTratamentoFragment tratamentoFragment = new ConfigTratamentoFragment();
    private ConfigNotificacaoFragment notificacaoFragment = new ConfigNotificacaoFragment();
    private ConfigBackupFragment backupFragment = new ConfigBackupFragment();
    private ConfigAjudaFragment ajudaFragment = new ConfigAjudaFragment();

    private Button btnConfigPerfil, btnConfigTratamento, btnConfigNotificacaos, btnConfigBackup, btnConfigAjuda, sla;
    private ImageButton miaumiau;
    private ConstraintLayout constraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        btnConfigPerfil = findViewById(R.id.btnConfigPerfil);
        btnConfigTratamento = findViewById(R.id.btnConfigTratamento);
        btnConfigNotificacaos = findViewById(R.id.btnConfigNotificacaos);
        btnConfigBackup = findViewById(R.id.btnConfigBackup);
        btnConfigAjuda = findViewById(R.id.btnConfigAjuda);
        sla = findViewById(R.id.sla);
        miaumiau = findViewById(R.id.miaumiua);


        constraintLayout = findViewById(R.id.conteudoConfig);


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
                transaction.add(R.id.frameConteudoConfigs, perfilFragment);
                transaction.commit();
            }
        });
        btnConfigTratamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.VISIBLE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frameConteudoConfigs, tratamentoFragment);
                transaction.commit();
            }
        });
        btnConfigNotificacaos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.VISIBLE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frameConteudoConfigs, notificacaoFragment);
                transaction.commit();
            }
        });
        btnConfigBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.VISIBLE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frameConteudoConfigs, backupFragment);
                transaction.commit();
            }
        });
        btnConfigAjuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.VISIBLE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frameConteudoConfigs, ajudaFragment);
                transaction.commit();
            }
        });
        sla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSupportFragmentManager().findFragmentById(R.id.frameConteudoConfigs) != null) {
                    constraintLayout.setVisibility(View.INVISIBLE);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.remove(getSupportFragmentManager().findFragmentById(R.id.frameConteudoConfigs));
                    transaction.commit();
                }
            }
        });

    }
}