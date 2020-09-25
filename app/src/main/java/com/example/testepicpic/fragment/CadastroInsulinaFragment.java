package com.example.testepicpic.fragment;

import android.os.Bundle;

import androidx.constraintlayout.solver.widgets.ConstraintTableLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.testepicpic.R;

import static android.view.View.INVISIBLE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CadastroInsulinaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CadastroInsulinaFragment extends Fragment {

    private RadioGroup radioGroup, radioGroup2;
    private RadioButton rbUtiliza1, rbNUtiliza1, rbUtiliza2, rbNUtiliza2;
    private ConstraintLayout clInsulina, clOutrasMed;
    private LinearLayout linearLayout;
    private Button btn;
    private boolean utilizaInsulina, utilizaMedicacoes;
    private String[] medicacoes;

    private CadastroLembretesFragment cadastroLembretesFragment = new CadastroLembretesFragment();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CadastroInsulinaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CadastroInsulinaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CadastroInsulinaFragment newInstance(String param1, String param2) {
        CadastroInsulinaFragment fragment = new CadastroInsulinaFragment();
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
        final View view = inflater.inflate(R.layout.fragment_cadastro_insulina, container, false);

        radioGroup = view.findViewById(R.id.rgInsulina);
        radioGroup2 = view.findViewById(R.id.rgOutrasMedicacoes);

        rbUtiliza1 = view.findViewById(R.id.rdbUtiliza1);
        rbNUtiliza1 = view.findViewById(R.id.rdbNUtiliza1);
        rbUtiliza2 = view.findViewById(R.id.rdbUtiliza2);
        rbNUtiliza2 = view.findViewById(R.id.rdbNUtiliza2);

        clInsulina = view.findViewById(R.id.clInsulina);
        clOutrasMed = view.findViewById(R.id.clOutrasMed);
        linearLayout = view.findViewById(R.id.linear2);

        btn = view.findViewById(R.id.btn_login);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                assert getArguments() != null;
                String pNome = getArguments().getString("pNome");
                String pIdade = getArguments().getString("pIdade");
                String pAltura = getArguments().getString("pAltura");
                String pPeso = getArguments().getString("pPeso");
                String pGenero = getArguments().getString("pGenero");
                String pTipoDiabetes = getArguments().getString("ptipoDiabetes");

                Bundle argsInsu = new Bundle();
                argsInsu.putString("pNome", pNome);
                argsInsu.putString("pIdade", pIdade);
                argsInsu.putString("pAltura", pAltura);
                argsInsu.putString("pPeso", pPeso);
                argsInsu.putString("pGenero", pGenero);
                argsInsu.putString("ptipoDiabetes", pTipoDiabetes);

                if(rbUtiliza1.isChecked()) {
                    utilizaInsulina = true;

                    if(rbUtiliza2.isChecked()) {
                        utilizaMedicacoes = true;

                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        argsInsu.putBoolean("pUtilizaInsulina", utilizaInsulina);
                        argsInsu.putBoolean("pUtilizaMedicacoes", utilizaMedicacoes);
                        cadastroLembretesFragment.setArguments(argsInsu);
                        transaction.setCustomAnimations( R.anim.to_left, R.anim.from_right, R.anim.to_left, R.anim.from_right);
                        transaction.replace(R.id.frameConteudoCad, cadastroLembretesFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    } else if (rbNUtiliza2.isChecked()) {
                        utilizaMedicacoes = false;
                        medicacoes = null;

                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        argsInsu.putBoolean("pUtilizaInsulina", utilizaInsulina);
                        argsInsu.putBoolean("pUtilizaMedicacoes", utilizaMedicacoes);
                        argsInsu.putStringArray("pMedicacoes", medicacoes);
                        cadastroLembretesFragment.setArguments(argsInsu);
                        transaction.setCustomAnimations( R.anim.to_left, R.anim.from_right, R.anim.to_left, R.anim.from_right);
                        transaction.replace(R.id.frameConteudoCad, cadastroLembretesFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    } else {
                        Toast.makeText(getActivity(), "Selecione uma opção para uso de medicação", Toast.LENGTH_LONG).show();
                    }

                } else if(rbNUtiliza1.isChecked()) {
                    utilizaInsulina = false;

                    if(rbUtiliza2.isChecked()) {
                        utilizaMedicacoes = true;

                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        argsInsu.putBoolean("pUtilizaInsulina", utilizaInsulina);
                        argsInsu.putBoolean("pUtilizaMedicacoes", utilizaMedicacoes);
                        cadastroLembretesFragment.setArguments(argsInsu);
                        transaction.setCustomAnimations( R.anim.to_left, R.anim.from_right, R.anim.to_left, R.anim.from_right);
                        transaction.replace(R.id.frameConteudoCad, cadastroLembretesFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    } else if (rbNUtiliza2.isChecked()) {
                        utilizaMedicacoes = false;
                        medicacoes = null;

                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        argsInsu.putBoolean("pUtilizaInsulina", utilizaInsulina);
                        argsInsu.putBoolean("pUtilizaMedicacoes", utilizaMedicacoes);
                        argsInsu.putStringArray("pMedicacoes", medicacoes);
                        cadastroLembretesFragment.setArguments(argsInsu);
                        transaction.setCustomAnimations( R.anim.to_left, R.anim.from_right, R.anim.to_left, R.anim.from_right);
                        transaction.replace(R.id.frameConteudoCad, cadastroLembretesFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else {
                        Toast.makeText(getActivity(), "Selecione uma opção para uso de medicação", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Selecione uma opção para uso de Insulina", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}