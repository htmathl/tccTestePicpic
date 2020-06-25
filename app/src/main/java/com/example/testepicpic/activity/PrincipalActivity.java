package com.example.testepicpic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testepicpic.R;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    public void Cadastro(View view){
        Intent cadastro  = new Intent(PrincipalActivity.this , Cadastro01Activity.class);
        startActivity(cadastro);
    }

    public void Login(View view){
        Intent login  = new Intent(PrincipalActivity.this , Cadastro13Activity.class);
        startActivity(login);
    }
}
