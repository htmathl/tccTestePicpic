package com.example.testepicpic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class tela_cadastro4 extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro4);
    }
    public void Pronto4(View view){
        Intent matheus  = new Intent(tela_cadastro4.this , tela_cadastro5.class);
        startActivity(matheus);
    }
}
