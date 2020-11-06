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
import com.google.firebase.database.DatabaseReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
                                String tipoDiabetes = getArguments().getString("ptipoDiabetes");
                                boolean utilizaInsulina = getArguments().getBoolean("pUtilizaInsulina");
                                boolean utilizaMedicacoes = getArguments().getBoolean("pUtilizaMedicacoes");
                                //String[] medicacoes = getArguments().getStringArray("pMedicacoes");


                                user.setNome(nome);
                                user.setIdade(Integer.parseInt(idade));
                                user.setAltura(Double.parseDouble(altura));
                                user.setPeso(Double.parseDouble(peso));
                                user.setGenero(genero);
                                user.setTipoDiabetes(tipoDiabetes);
                                user.setUtilizoInsulina(utilizaInsulina);
                                user.setUtilizoMedicacao(utilizaMedicacoes);
                                //user.setMedicacao(medicacoes);
                                //user.setLembretes(lembretes);
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
                    Usuario.atualizarNomeUsuario(user.getNome());
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("fecharTelaPrincipal"));


                    String idUser = Base64Custom.codificarBase64(user.getEmail());
                    user.setIdUser(idUser);
                    user.salvar();



                    salvarLembretesMedicacoes();

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
    public void salvarLembretesMedicacoes() {

        assert getArguments() != null;
        boolean[] lembretes = getArguments().getBooleanArray("pLembretes");
        boolean utilizaMedicacoes = getArguments().getBoolean("pUtilizaMedicacoes");
        boolean[] diasGli = getArguments().getBooleanArray("pDiasGliA");
        boolean[] diasInsu = getArguments().getBooleanArray("pDiasInsuA");
        boolean[] diasAgu = getArguments().getBooleanArray("pDiasAguA");
        boolean[] diasReme = getArguments().getBooleanArray("pDiasRemeA");

        ArrayList<Integer> listaHorarioInsulina = getArguments().getIntegerArrayList("pListaHorarioInsulina");
        ArrayList<Integer> listaHorarioGlicemia = getArguments().getIntegerArrayList("pListaHorarioGlicemia");
        ArrayList<Integer> listaHorarioAgua = getArguments().getIntegerArrayList("pListaHorarioAgua");
        ArrayList<Integer> listaHorarioRemedios = getArguments().getIntegerArrayList("pListaHorarioRemedios");
        DatabaseReference firebase = ConfigFirebase.getFirebase();

        String[] diass = {"Domingo","Segunda", "Terça", "Quarta", "Quinta","Sexta","Sábado"};

        assert listaHorarioGlicemia != null;
        Integer[] ArrayGlicemia = new Integer[listaHorarioGlicemia.size()];
        ArrayGlicemia = listaHorarioGlicemia.toArray(ArrayGlicemia);
        assert listaHorarioInsulina != null;
        Integer[] ArrayInsulina = new Integer[listaHorarioInsulina.size()];
        ArrayInsulina = listaHorarioInsulina.toArray(ArrayInsulina);
        assert listaHorarioAgua != null;
        Integer[] ArrayAgua = new Integer[listaHorarioAgua.size()];
        ArrayAgua = listaHorarioAgua.toArray(ArrayAgua);
        assert listaHorarioRemedios != null;
        Integer[] ArrayRemedios = new Integer[listaHorarioRemedios.size()];
        ArrayRemedios = listaHorarioRemedios.toArray(ArrayRemedios);

        //salvar lembretes e medicacoes

        if (lembretes == null) {

            firebase.child("lembretes")
                    .child(user.getIdUser())
                    .setValue("miau");

        } else {

            for(int i = 0; i < lembretes.length; i++) {
                switch (i) {
                    case 0:
                        if(lembretes[i]) {
                            assert diasGli != null;
                            for(int j = 0; j < diasGli.length; j++){
                                for(int k = 0; k < ArrayGlicemia.length; k++) {
                                    if(diasGli[j]){
                                        firebase.child("users")
                                                .child(user.getIdUser())
                                                .child("lembretes")
                                                .child("Glicemia")
                                                .child("dias")
                                                .child(diass[j])
                                                .child(String.valueOf(k))
                                                .setValue(ArrayGlicemia[k]);
                                    }
                                }
                            }

                        } else {
                            firebase.child("users")
                                    .child(user.getIdUser())
                                    .child("lembretes")
                                    .child("Glicemia")
                                    .setValue(lembretes[i]);
                        }
                        break;
                    case 1:
                        if(lembretes[i]) {
                            assert diasInsu != null;
                            for(int j = 0; j < diasInsu.length; j++){
                                for(int k = 0; k < ArrayInsulina.length; k++) {
                                    if(diasInsu[j]){
                                        firebase.child("users")
                                                .child(user.getIdUser())
                                                .child("lembretes")
                                                .child("Insulina")
                                                .child("dias")
                                                .child(diass[j])
                                                .child(String.valueOf(k))
                                                .setValue(ArrayInsulina[k]);
                                    }
                                }
                            }

                        } else {
                            firebase.child("users")
                                    .child(user.getIdUser())
                                    .child("lembretes")
                                    .child("Insulina")
                                    .setValue(lembretes[i]);
                        }
                        break;
                    case 2:
                        if(lembretes[i]) {
                            assert diasAgu != null;
                            for(int j = 0; j < diasAgu.length; j++){
                                for(int k = 0; k < ArrayAgua.length; k++) {
                                    if(diasAgu[j]){
                                        firebase.child("users")
                                                .child(user.getIdUser())
                                                .child("lembretes")
                                                .child("Água")
                                                .child("dias")
                                                .child(diass[j])
                                                .child(String.valueOf(k))
                                                .setValue(ArrayAgua[k]);
                                    }
                                }
                            }

                        } else {
                            firebase.child("users")
                                    .child(user.getIdUser())
                                    .child("lembretes")
                                    .child("Água")
                                    .setValue(lembretes[i]);
                        }
                        break;
                    case 3:
                        if(lembretes[i]) {
                            assert diasReme != null;
                            for(int j = 0; j < diasReme.length; j++){
                                for(int k = 0; k < ArrayRemedios.length; k++) {
                                    if(diasReme[j]){
                                        firebase.child("users")
                                                .child(user.getIdUser())
                                                .child("lembretes")
                                                .child("Remédios")
                                                .child("dias")
                                                .child(diass[j])
                                                .child(String.valueOf(k))
                                                .setValue(ArrayRemedios[k]);
                                    }
                                }
                            }

                        } else {
                            firebase.child("users")
                                    .child(user.getIdUser())
                                    .child("lembretes")
                                    .child("Remédios")
                                    .setValue(lembretes[i]);
                        }
                        break;
                }

            }
        }

    }
}