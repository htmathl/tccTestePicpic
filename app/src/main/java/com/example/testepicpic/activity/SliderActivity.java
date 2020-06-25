package com.example.testepicpic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testepicpic.R;
import com.example.testepicpic.config.ConfigFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class SliderActivity extends IntroActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_slider);

        verificarUserLogado();

        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(R.color.branco)
                .fragment(R.layout.intro_1)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(R.color.branco)
                .fragment(R.layout.intro_2)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(R.color.branco)
                .fragment(R.layout.intro_final)
                .build()
        );

    }
    public void btnEntrar(View v) {
        startActivity(new Intent(this, PrincipalActivity.class));
    }
    public void verificarUserLogado() {
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser() != null) {
            abrirTelaPrincipal();
        }
    }
    public void abrirTelaPrincipal() {
        startActivity(new Intent(this, MainActivity.class));
    }
}