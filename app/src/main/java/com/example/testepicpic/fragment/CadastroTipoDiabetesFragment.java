package com.example.testepicpic.fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.testepicpic.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CadastroTipoDiabetesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CadastroTipoDiabetesFragment extends Fragment {

    private Button btnPronto02;
    private RadioGroup radioGroup;
    private RadioButton prediabetes, tipo1, tipo2, gestacional;
    private String tipoDiabetes;

    private CadastroInsulinaFragment cadastroInsulinaFragment = new CadastroInsulinaFragment();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CadastroTipoDiabetesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CadastroTipoDiabetesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CadastroTipoDiabetesFragment newInstance(String param1, String param2) {
        CadastroTipoDiabetesFragment fragment = new CadastroTipoDiabetesFragment();
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
        View view = inflater.inflate(R.layout.fragment_cadastro_tipo_diabetes, container, false);

        btnPronto02 = view.findViewById(R.id.btnPronto02);
        radioGroup = view.findViewById(R.id.radioGroup2);
        prediabetes = view.findViewById(R.id.rdbPrediabetes);
        tipo1 = view.findViewById(R.id.rdbTipo1);
        tipo2 = view.findViewById(R.id.rdbTipo2);
        gestacional = view.findViewById(R.id.rdbGest);

        btnPronto02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pNome = getArguments().getString("pNome");
                String pIdade = getArguments().getString("pIdade");
                String pAltura = getArguments().getString("pAltura");
                String pPeso = getArguments().getString("pPeso");
                String pGenero = getArguments().getString("pGenero");

                Bundle argsTipo = new Bundle();
                argsTipo.putString("pNome", pNome);
                argsTipo.putString("pIdade", pIdade);
                argsTipo.putString("pAltura", pAltura);
                argsTipo.putString("pPeso", pPeso);
                argsTipo.putString("pGenero", pGenero);

                if(prediabetes.isChecked()) {
                    tipoDiabetes = "Pré-diabetes";

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    argsTipo.putString("ptipoDiabetes", tipoDiabetes);
                    cadastroInsulinaFragment.setArguments(argsTipo);
                    transaction.setCustomAnimations( R.anim.to_left, R.anim.from_right, R.anim.to_left, R.anim.from_right);
                    transaction.replace(R.id.frameConteudoCad, cadastroInsulinaFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else if(tipo1.isChecked()){
                    tipoDiabetes = "Tipo 1";

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    argsTipo.putString("ptipoDiabetes", tipoDiabetes);
                    cadastroInsulinaFragment.setArguments(argsTipo);
                    transaction.setCustomAnimations( R.anim.to_left, R.anim.from_right, R.anim.to_left, R.anim.from_right);
                    transaction.replace(R.id.frameConteudoCad, cadastroInsulinaFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }
                else if(tipo2.isChecked()){
                    tipoDiabetes = "Tipo 2";

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    argsTipo.putString("ptipoDiabetes", tipoDiabetes);
                    cadastroInsulinaFragment.setArguments(argsTipo);
                    transaction.setCustomAnimations( R.anim.to_left, R.anim.from_right, R.anim.to_left, R.anim.from_right);
                    transaction.replace(R.id.frameConteudoCad, cadastroInsulinaFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }
                else if (gestacional.isChecked()){
                    tipoDiabetes = "Gestacional";

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    argsTipo.putString("ptipoDiabetes", tipoDiabetes);
                    cadastroInsulinaFragment.setArguments(argsTipo);
                    transaction.setCustomAnimations( R.anim.to_left, R.anim.from_right, R.anim.to_left, R.anim.from_right);
                    transaction.replace(R.id.frameConteudoCad, cadastroInsulinaFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } else {
                    Toast.makeText(getActivity(), "Selecione um tipo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}