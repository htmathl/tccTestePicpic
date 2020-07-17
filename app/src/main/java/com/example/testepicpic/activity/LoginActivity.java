package com.example.testepicpic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testepicpic.R;
import com.example.testepicpic.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.testepicpic.config.ConfigFirebase;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;


public class LoginActivity extends AppCompatActivity {

    private TextInputEditText email;
    private TextInputEditText senha;
    private Button btnLogar;

    FirebaseAuth autenticacao;

    private Usuario user = new Usuario();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.edtEmail3);
        senha = findViewById(R.id.edtSenha3);
        btnLogar = findViewById(R.id.btn_login);

        btnLogar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String textoEmail = email.getText().toString();
                String textoSenha = senha.getText().toString();

                if(!textoEmail.isEmpty()) {

                    if(!textoSenha.isEmpty()) {

                        user.setEmail(textoEmail);
                        user.setSenha(textoSenha);
                        validarLogin();

                    } else {
                        Toast.makeText(LoginActivity.this, "Senha não pode estar vazio", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Email não pode estar vazio", Toast.LENGTH_SHORT).show();
                }

            }

            public void validarLogin() {

                autenticacao = ConfigFirebase.getFirebaseAutenticacao();
                autenticacao.signInWithEmailAndPassword(
                        user.getEmail(),
                        user.getSenha()
                ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            autenticacao = ConfigFirebase.getFirebaseAutenticacao();
                            Toast.makeText(LoginActivity.this, "Bem vindo, " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            LocalBroadcastManager.getInstance(LoginActivity.this).sendBroadcast(new Intent("fecharTelaPrincipal"));
                            verificarUserLogado();
                        } else {

                            String excessao = "";

                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                excessao = "Este usuário não existe";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                excessao = "Email e(ou) senha estão errados";
                            } catch (Exception e) {
                                excessao = "Erro ao cadastrar usuário" + e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(LoginActivity.this, excessao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            public void verificarUserLogado() {
                autenticacao = ConfigFirebase.getFirebaseAutenticacao();
                if(autenticacao.getCurrentUser() != null) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        });

    }
}