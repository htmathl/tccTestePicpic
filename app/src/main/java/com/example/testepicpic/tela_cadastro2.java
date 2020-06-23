package com.example.testepicpic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class tela_cadastro2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro2);
    }
    public void Pronto2(View view){
        Intent matheus  = new Intent(tela_cadastro2.this , tela_cadastro3.class);
        startActivity(matheus);
    }
}