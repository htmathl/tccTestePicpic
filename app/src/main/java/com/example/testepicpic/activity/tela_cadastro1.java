package com.example.testepicpic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testepicpic.R;

public class tela_cadastro1 extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro1);
    }

    public void Proximo(View view){
        Intent matheus  = new Intent(tela_cadastro1.this , tela_cadastro2.class);
        startActivity(matheus);

    }
}
