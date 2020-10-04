package com.example.testepicpic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testepicpic.R;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.fragment.CadastroHorarioFragment;
import com.example.testepicpic.fragment.CadastroNomeFragment;
import com.example.testepicpic.helper.Base64Custom;
import com.example.testepicpic.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


public class CadastroActivity extends AppCompatActivity {

    private CadastroNomeFragment cadastroNomeFragment = new CadastroNomeFragment();

    private CadastroHorarioFragment cadastroHorarioFragment = new CadastroHorarioFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameConteudoCad, cadastroNomeFragment);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else if (getFragmentManager().getBackStackEntryCount() == 4) {
            getSupportFragmentManager().beginTransaction().remove(cadastroHorarioFragment).commit();
        }
        else {
            getFragmentManager().popBackStack();
        }
    }

}