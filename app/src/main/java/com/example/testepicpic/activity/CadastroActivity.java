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
import com.example.testepicpic.config.ConfigFirebase;
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

    private TextInputEditText email;
    private TextInputEditText senha;
    private TextInputEditText confSenha;
    private Button btnPronto2;

    FirebaseAuth autenticacao;

    private Usuario user = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        email = findViewById(R.id.edtEmail);
        senha = findViewById(R.id.edtSenha);
        confSenha = findViewById(R.id.edtConfirmS);
        btnPronto2 = findViewById(R.id.btn_login);

        btnPronto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoEmail = email.getText().toString();
                String textoSenha = senha.getText().toString();
                String textoConfSenha = confSenha.getText().toString();

                if(!textoEmail.isEmpty()) {

                    if(!textoSenha.isEmpty()) {

                        if(!textoConfSenha.isEmpty()) {

                            if(textoSenha.equals(textoConfSenha)) {
                                user.setEmail(textoEmail);
                                user.setSenha(textoSenha);

                                cadastrar();
                            } else  {
                                Toast.makeText(CadastroActivity.this, "As senhas não estão iguais", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(CadastroActivity.this, "Confirme sua senha não poder estar vazio", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(CadastroActivity.this, "Senha não pode estar vazio", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(CadastroActivity.this, "Email não pode estar vazio", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /* lembrar de inserir mais tarde o ultimo botão "pronto" das telas de cadastro, para mão haver conlito de criação de conta */

    public void cadastrar() {
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                user.getEmail(), user.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(CadastroActivity.this, "Bem vindo,  " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CadastroActivity.this, MainActivity.class));
                    LocalBroadcastManager.getInstance(CadastroActivity.this).sendBroadcast(new Intent("fecharTelaPrincipal"));
                    finish();
                } else {

                String excessao = "";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excessao = "Digite uma senha mais forte";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excessao = "Digite um email válido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        excessao = "Esta cinta já foi cadstrada";
                    } catch (Exception e) {
                        excessao = "Erro ao cadastrar: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this, excessao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}