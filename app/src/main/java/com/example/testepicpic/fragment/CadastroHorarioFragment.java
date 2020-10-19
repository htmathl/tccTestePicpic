package com.example.testepicpic.fragment;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.testepicpic.R;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CadastroHorarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CadastroHorarioFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private CadastroEmailFragment cadastroEmailFragment = new CadastroEmailFragment();

    private Button btnAddHoras;
    private Button btnPronto;
    private TextView txtGlicemia2, txtInsulina2, txtAgua2, txtmiau;
    private Spinner spLembretes;

    private LinearLayout linearGlicemia;
    private LinearLayout linearDom, linearSeg, linearTer, linearQua, linearQui, linearSex, linearSab;
    private LinearLayout linearPriDom;

    private int[] listahora;
    private int[] listaaaa;

    private TextView txtGliDomPri;

    private FrameLayout frameLembretesDias;

    private CheckBox cbDom, cbSeg, cbTer, cbQua, cbQui, cbSex, cbSab;

    boolean[] pDiasGli = new boolean[7];
    boolean[] pdiasInsu = new boolean[7];
    boolean[] pdiasAgu = new boolean[7], pDiasReme = new boolean[7];

    private ArrayList<String> pSelectLista = new ArrayList<String>();

    ArrayList<Integer> pListaHorarioGlicemia = new ArrayList<Integer>();
    ArrayList<Integer> pListaHorarioInsulina = new ArrayList<Integer>();
    ArrayList<Integer> pListaHorarioAgua = new ArrayList<Integer>();
    ArrayList<Integer> pListaHorarioRemedios = new ArrayList<Integer>();



    int Hour, min;

    private List<String> lemre = new ArrayList<String>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CadastroHorarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CadastroHorarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CadastroHorarioFragment newInstance(String param1, String param2) {
        CadastroHorarioFragment fragment = new CadastroHorarioFragment();
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


        final View view = inflater.inflate(R.layout.fragment_cadastro_horario, container, false);

        btnAddHoras = view.findViewById(R.id.btnAddHora);
        btnPronto = view.findViewById(R.id.btnPronto6);

        linearGlicemia = view.findViewById(R.id.linearGlicemia);

        txtmiau = view.findViewById(R.id.txtmiau);

        cbDom = view.findViewById(R.id.cbDom);
        cbSeg = view.findViewById(R.id.cbSeg);
        cbTer = view.findViewById(R.id.cbTer);
        cbQua = view.findViewById(R.id.cbQua);
        cbQui = view.findViewById(R.id.cbQui);
        cbSex = view.findViewById(R.id.cbSex);
        cbSab = view.findViewById(R.id.cbSab);

        Calendar c = Calendar.getInstance();
        Hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

        txtmiau.setText("Adicionar Horário");

        final boolean[] pLembretes = getArguments().getBooleanArray("pLembretes");
        assert getArguments() != null;
        final String pNome = getArguments().getString("pNome");
        final String pIdade = getArguments().getString("pIdade");
        final String pAltura = getArguments().getString("pAltura");
        final String pPeso = getArguments().getString("pPeso");
        final String pGenero = getArguments().getString("pGenero");
        final String pTipoDiabetes = getArguments().getString("ptipoDiabetes");
        final boolean pUtilizaInsulina = getArguments().getBoolean("pUtilizaInsulina");
        final String[] pMedicacoes = getArguments().getStringArray("pMedicacoes");
        final boolean pUtilizaMedicacoes = getArguments().getBoolean("pUtilizaMedicacoes");

        final Bundle argsHour = new Bundle();
        argsHour.putString("pNome", pNome);
        argsHour.putString("pIdade", pIdade);
        argsHour.putString("pAltura", pAltura);
        argsHour.putString("pPeso", pPeso);
        argsHour.putString("pGenero", pGenero);
        argsHour.putString("ptipoDiabetes", pTipoDiabetes);
        argsHour.putBoolean("pUtilizaInsulina", pUtilizaInsulina);
        argsHour.putBoolean("pUtilizaMedicacoes", pUtilizaMedicacoes);
        argsHour.putStringArray("pMedicacoes", pMedicacoes);
        argsHour.putBooleanArray("pLembretes", pLembretes);

        if(!lemre.isEmpty())
            lemre.clear();

        assert pLembretes != null;
        if(pLembretes[0])
            lemre.add("Glicemia");
        if(pLembretes[1])
            lemre.add("Insulina");
        if(pLembretes[2])
            lemre.add("Água");
        if(pLembretes[3])
            lemre.add("Remédios");

        if(lemre.isEmpty()) {

            //Pesquisar como fazer pra voltar 2 fragments.

            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.popBackStack();
            FragmentTransaction transaction = manager.beginTransaction();
            cadastroEmailFragment.setArguments(argsHour);
            transaction.setCustomAnimations( R.anim.to_left, R.anim.from_right, R.anim.to_left, R.anim.from_right);
            transaction.replace(R.id.frameConteudoCad, cadastroEmailFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }

        final String[] lembrefi = lemre.toArray(new String[0]);

        spLembretes = view.findViewById(R.id.spLembretes);
        List<String> list = new ArrayList<>(Arrays.asList(lembrefi));

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spLembretes.setAdapter(adapter);

        btnPronto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtmiau.getText().equals("Adicionar Horário")){
                    Toast.makeText(getActivity(), "Adicone algum horario ou não selecione nenhum lembrete",
                            Toast.LENGTH_LONG).show();
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Quer mesmo Continuar?");

                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            argsHour.putBooleanArray("pDiasGliA", pDiasGli);
                            argsHour.putBooleanArray("pDiasInsuA", pdiasInsu);
                            argsHour.putBooleanArray("pDiasAguA", pdiasAgu);
                            argsHour.putBooleanArray("pDiasRemeA", pDiasReme);
                            argsHour.putIntegerArrayList("pListaHorarioInsulina",pListaHorarioInsulina);
                            argsHour.putIntegerArrayList("pListaHorarioGlicemia",pListaHorarioGlicemia);
                            argsHour.putIntegerArrayList("pListaHorarioAgua",pListaHorarioAgua);
                            argsHour.putIntegerArrayList("pListaHorarioRemedios",pListaHorarioRemedios);

                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            cadastroEmailFragment.setArguments(argsHour);
                            transaction.setCustomAnimations( R.anim.to_left, R.anim.from_right, R.anim.to_left, R.anim.from_right);
                            transaction.replace(R.id.frameConteudoCad, cadastroEmailFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();

                        }
                    });

                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });

                    builder.create();
                    builder.show();

                }

            }
        });

        btnAddHoras.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(txtmiau.getText().equals("Adicionar Horário")){
                    Toast.makeText(getActivity(), "Algo de errado não esta certo", Toast.LENGTH_LONG).show();
                }
                else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Deseja mesmo adicionar este horario?");

                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String pSelect = spLembretes.getSelectedItem().toString();

                            CheckBox[] dias = {cbDom, cbSeg, cbTer, cbQua, cbQui, cbSex, cbSab};

                            switch (pSelect) {
                                case "Glicemia":
                                    if(!pSelectLista.contains("Glicemia"))
                                        pSelectLista.add("Glicemia");

                                    for(int i= 0; i < pDiasGli.length; i++) {
                                        if(dias[i].isChecked())
                                            pDiasGli[i] = true;
                                    }

                                    break;
                                case "Insulina":
                                    if(!pSelectLista.contains("Insulina"))
                                        pSelectLista.add("Insulina");

                                    for(int i= 0; i < pdiasInsu.length; i++) {
                                        if(dias[i].isChecked())
                                            pdiasInsu[i] = true;
                                    }

                                    break;
                                case "Água":
                                    if(!pSelectLista.contains("Água"))
                                        pSelectLista.add("Água");

                                    for(int i= 0; i < pdiasAgu.length; i++) {
                                        if(dias[i].isChecked())
                                            pdiasAgu[i] = true;
                                    }

                                    break;
                                case "Remédios":
                                    if(!pSelectLista.contains("Remédios"))
                                        pSelectLista.add("Remédios");

                                    for(int i= 0; i < pDiasReme.length; i++) {
                                        if(dias[i].isChecked())
                                            pDiasReme[i] = true;
                                    }
                                    break;
                            }

                            Toast.makeText(getActivity(), "Horário adicionado", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });

                    builder.create();
                    builder.show();

                }

            }
        });

        txtmiau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog1 = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String select = spLembretes.getSelectedItem().toString();

                        switch (select) {
                            case "Glicemia":

                                pListaHorarioGlicemia.add(hourOfDay * 60 + minute);

                                txtmiau.setText(String.format("%02d:%02d", hourOfDay, minute));

                                break;
                            case "Insulina":

                                pListaHorarioInsulina.add(hourOfDay * 60 + minute);

                                txtmiau.setText(String.format("%02d:%02d", hourOfDay, minute));

                                break;
                            case "Água":
                                pListaHorarioAgua.add(hourOfDay * 60 + minute);

                                txtmiau.setText(String.format("%02d:%02d", hourOfDay, minute));
                                break;
                            case "Remédios":
                                pListaHorarioRemedios.add(hourOfDay * 60 + minute);

                                txtmiau.setText(String.format("%02d:%02d", hourOfDay, minute));
                                break;
                        }


                    }
                }, Hour, min, true);

                timePickerDialog1.show();

            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}