package com.example.testepicpic.fragment;

import android.app.TimePickerDialog;
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.testepicpic.R;
import com.example.testepicpic.activity.MainActivity;

import java.lang.reflect.Array;
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

    private ImageButton btnAddHoras, btnAddHoras1, btnAddHoras2, btnAddHoras3, btnAddHoras4;
    private Button btnPronto;
    private TextView txtGlicemia2, txtInsulina2, txtAgua2, txtMedicamento2;
    private Spinner spLembretes;
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


        View view = inflater.inflate(R.layout.fragment_cadastro_horario, container, false);

        btnAddHoras = view.findViewById(R.id.btnAddHora);
        btnAddHoras1 = view.findViewById(R.id.btnAddHora1);
        btnAddHoras2 = view.findViewById(R.id.btnAddHora2);
        btnAddHoras3 = view.findViewById(R.id.btnAddHora3);
        btnAddHoras4 = view.findViewById(R.id.btnAddHora4);

        btnPronto = view.findViewById(R.id.btnPronto6);

        txtGlicemia2 = view.findViewById(R.id.txtGlicemia2);
        txtInsulina2 = view.findViewById(R.id.txtInsulina2);
        txtAgua2 = view.findViewById(R.id.txtAgua2);
        txtMedicamento2 = view.findViewById(R.id.txtMedicamento2);

        Calendar c = Calendar.getInstance();
        Hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

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

        Bundle argsHour = new Bundle();
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spLembretes.setAdapter(adapter);

        btnPronto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!lemre.isEmpty()) {

                    String select = spLembretes.getSelectedItem().toString();

                    if(select.equals("Glicemia")) {

                        Toast.makeText(getActivity(), "miales", Toast.LENGTH_LONG).show();

                    } else if(select.equals("Insulina")) {

                    } else if(select.equals("Água")) {

                    } else if(select.equals("Remédios")) {

                    }
                }
            }
        });

        btnAddHoras.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {



                    }
                }, Hour, min,true);

                timePickerDialog.show();

            }
        });

        btnAddHoras1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtGlicemia2.setText(hourOfDay + ":" + minute);
                    }
                }, Hour, min,true);

                timePickerDialog.show();
            }
        });

        btnAddHoras2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtInsulina2.setText(hourOfDay + ":"+minute);
                    }
                }, Hour, min,true);

                timePickerDialog.show();
            }
        });

        btnAddHoras3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        txtAgua2.setText(hourOfDay + ":"+minute);
                    }
                }, Hour, min,true);

                timePickerDialog.show();
            }
        });

        btnAddHoras4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        txtMedicamento2.setText(hourOfDay + ":"+minute);
                    }
                }, Hour, min,true);

                timePickerDialog.show();
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