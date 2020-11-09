package com.example.testepicpic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testepicpic.R;
import com.example.testepicpic.helper.Base64Custom;
import com.example.testepicpic.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.testepicpic.config.ConfigFirebase;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


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
                        senha.setError(null);
                        senha.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.edt_cadastro_erro));
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Email não pode estar vazio", Toast.LENGTH_SHORT).show();
                    email.setError(null);
                    email.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.edt_cadastro_erro));
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

                            DatabaseReference ref = ConfigFirebase.getFirebase();

                            int pYear = Calendar.getInstance().get(Calendar.YEAR);
                            int pMonth = (Calendar.getInstance().get(Calendar.MONTH)+1);
                            int pDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                            String data = String.valueOf(pYear) + String.valueOf(pMonth) + String.valueOf(pDay);

                            FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

                            String email = auth.getCurrentUser().getEmail();
                            assert email != null;
                            String currentId = Base64Custom.codificarBase64(email);

                            DatabaseReference reference = ref.child("inserção")
                                    .child(currentId)
                                    .child("bem-estar");

                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                        DatabaseReference reference1 =  ref.child("inserção")
                                                                        .child(currentId)
                                                                        .child("bem-estar")
                                                                        .child(dataSnapshot.getKey())
                                                                        .child("Água");

                                        reference1.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                ref.child("inserção")
                                                        .child(currentId)
                                                        .child("bem-estar")
                                                        .child(dataSnapshot.getKey())
                                                        .child("Água")
                                                        .setValue(snapshot.getValue());

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

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