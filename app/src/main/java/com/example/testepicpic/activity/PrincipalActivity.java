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

    public void Proximo(View view){
        Intent matheus  = new Intent(PrincipalActivity.this , Cadastro01Activity.class);
        startActivity(matheus);

    }
}
