package com.example.testepicpic.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.testepicpic.R;
import com.example.testepicpic.activity.CadastroActivity;
import com.example.testepicpic.activity.MainActivity;
import com.example.testepicpic.config.ConfigFirebase;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CadastroEmailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CadastroEmailFragment extends Fragment {

    private TextInputEditText email;
    private TextInputEditText senha;
    private TextInputEditText confSenha;
    private Button btnPronto2;

    FirebaseAuth autenticacao;

    private Usuario user = new Usuario();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CadastroEmailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CadastroEmailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CadastroEmailFragment newInstance(String param1, String param2) {
        CadastroEmailFragment fragment = new CadastroEmailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cadastro_email, container, false);

        email = view.findViewById(R.id.edtEmail);
        senha = view.findViewById(R.id.edtSenha);
        confSenha = view.findViewById(R.id.edtConfirmS);
        btnPronto2 = view.findViewById(R.id.btnCadastrar);

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

                                String nome = getArguments().getString("pNome");
                                String idade = getArguments().getString("pIdade");
                                String altura = getArguments().getString("pAltura");
                                String peso = getArguments().getString("pPeso");
                                String genero = getArguments().getString("pGenero");

                                String tipoDiabetes = getArguments().getString("tipoDiabetes");

                                user.setNome(nome);
                                user.setIdade(Integer.parseInt(idade));
                                user.setAltura(Double.parseDouble(altura));
                                user.setPeso(Double.parseDouble(peso));
                                user.setGenero(genero);
                                user.setTipoDiabetes(tipoDiabetes);
                                user.setEmail(textoEmail);
                                user.setSenha(textoSenha);

                                cadastrar();
                            } else  {
                                Toast.makeText(getActivity(), "As senhas não estão iguais", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getActivity(), "Confirme sua senha não poder estar vazio", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Senha não pode estar vazio", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Email não pode estar vazio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    /* lembrar de inserir mais tarde o ultimo botão "pronto" das telas de cadastro, para mão haver conlito de criação de conta */

    public void cadastrar() {
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                user.getEmail(), user.getSenha()
        ).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Bem vindo,  " + user.getNome(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("fecharTelaPrincipal"));

                    String idUser = Base64Custom.codificarBase64(user.getEmail());
                    user.setIdUser(idUser);
                    user.salvar();

                    getActivity().finish();
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

                    Toast.makeText(getActivity(), excessao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}