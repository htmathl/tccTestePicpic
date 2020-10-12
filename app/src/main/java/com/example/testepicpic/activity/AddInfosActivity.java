package com.example.testepicpic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.testepicpic.R;
import com.example.testepicpic.fragment.AddAlimentacao;
import com.example.testepicpic.fragment.AddBemEstar;
import com.example.testepicpic.fragment.AddExercicio;
import com.example.testepicpic.fragment.AddGlicemia;
import com.example.testepicpic.fragment.AddInsulina;

public class AddInfosActivity extends AppCompatActivity {

    private AddGlicemia addGlicemia = new AddGlicemia();
    private AddInsulina addInsulina = new AddInsulina();
    private AddAlimentacao addAlimentacao = new AddAlimentacao();
    private AddBemEstar addBemEstar = new AddBemEstar();
    private AddExercicio addExercicio = new AddExercicio();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_infos);

        Bundle extras = getIntent().getExtras();

        assert extras != null;
        int position = extras.getInt("position");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (position) {
            case 0:
                transaction.replace(R.id.frameAddInfos , addBemEstar);
                transaction.commit();
                break;
            case 1:
                transaction.replace(R.id.frameAddInfos, addAlimentacao);
                transaction.commit();
                break;
            case 2:
                transaction.replace(R.id.frameAddInfos, addExercicio);
                transaction.commit();
                break;
            case 3:
                transaction.replace(R.id.frameAddInfos, addInsulina);
                transaction.commit();
                break;
            case 4:
                transaction.replace(R.id.frameAddInfos, addGlicemia);
                transaction.commit();
                break;
        }

    }


}