package com.example.testepicpic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class tela_cadastro3 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro3);
    }
    public void Pronto3(View view){
        Intent matheus  = new Intent(tela_cadastro3.this , tela_cadastro4.class);
        startActivity(matheus);
    }
}
