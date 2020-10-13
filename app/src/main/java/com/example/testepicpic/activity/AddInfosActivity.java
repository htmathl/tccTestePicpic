package com.example.testepicpic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.testepicpic.R;
import com.example.testepicpic.fragment.AddAlimentacaoFragment;
import com.example.testepicpic.fragment.AddBemEstarFragment;
import com.example.testepicpic.fragment.AddExercicioFragment;
import com.example.testepicpic.fragment.AddGlicemiaFragment;
import com.example.testepicpic.fragment.AddInsulinaFragment;

public class AddInfosActivity extends AppCompatActivity {

    private AddGlicemiaFragment addGlicemiaFragment = new AddGlicemiaFragment();
    private AddInsulinaFragment addInsulinaFragment = new AddInsulinaFragment();
    private AddAlimentacaoFragment addAlimentacaoFragment = new AddAlimentacaoFragment();
    private AddBemEstarFragment addBemEstarFragment = new AddBemEstarFragment();
    private AddExercicioFragment addExercicioFragment = new AddExercicioFragment();

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
                transaction.replace(R.id.frameAddInfos, addBemEstarFragment);
                transaction.commit();
                break;
            case 1:
                transaction.replace(R.id.frameAddInfos, addAlimentacaoFragment);
                transaction.commit();
                break;
            case 2:
                transaction.replace(R.id.frameAddInfos, addExercicioFragment);
                transaction.commit();
                break;
            case 3:
                transaction.replace(R.id.frameAddInfos, addInsulinaFragment);
                transaction.commit();
                break;
            case 4:
                transaction.replace(R.id.frameAddInfos, addGlicemiaFragment);
                transaction.commit();
                break;
        }

    }


}