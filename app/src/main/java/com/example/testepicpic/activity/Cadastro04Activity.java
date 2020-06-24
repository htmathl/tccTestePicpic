package com.example.testepicpic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testepicpic.R;

public class Cadastro04Activity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_03);
    }
    public void Pronto4(View view){
        Intent matheus  = new Intent(Cadastro04Activity.this , Cadastro05Activity.class);
        startActivity(matheus);
    }
}
