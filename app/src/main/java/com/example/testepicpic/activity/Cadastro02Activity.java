package com.example.testepicpic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testepicpic.R;

public class Cadastro02Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_02);
    }
    public void Pronto3(View view){
        Intent matheus  = new Intent(Cadastro02Activity.this , Cadastro04Activity.class);
        startActivity(matheus);
    }
}
