package com.example.testepicpic.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.testepicpic.R;
import com.example.testepicpic.activity.AddInfosActivity;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.helper.Base64Custom;
import com.example.testepicpic.model.Glicemia;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.BadPaddingException;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddGlicemiaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddGlicemiaFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Button btnHorarioGli, btnGliDia;

    private int Hour, min, hora;

    private EditText edtNivelGli;

    private RadioButton rdbEsquerda, rdbDireita;

    private Spinner spLocalGli;

    private ArrayList<String> pCat = new ArrayList<String>();

    private int pDay, pMonth, pYear;

    private DatabaseReference database;

    private String currentId, numGlicemia;

    private ConstraintLayout clDedoGli;

    private Glicemia glicemia;

    private CalendarFragment calendarFragment;

    public static final String PREFS_NAME = "shareData";

    SharedPreferences preferences;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddGlicemiaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddGlicemia.
     */
    // TODO: Rename and change types and number of parameters
    public static AddGlicemiaFragment newInstance(String param1, String param2) {
        AddGlicemiaFragment fragment = new AddGlicemiaFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_glicemia, container, false);

        btnHorarioGli = view.findViewById(R.id.btnHorarioGli);
        edtNivelGli = view.findViewById(R.id.edtNumGlicemia);
        btnGliDia = view.findViewById(R.id.btnGliDia);

        rdbDireita = view.findViewById(R.id.rdbDireita);
        rdbEsquerda = view.findViewById(R.id.rdbEsquerda);

        spLocalGli = view.findViewById(R.id.spLocalGli);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.localAplicacaoGlicemia, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocalGli.setAdapter(adapter);

        spLocalGli.setOnItemSelectedListener(this);

        Button btnProntoGli = view.findViewById(R.id.btnProntoGli);

        ImageButton ibtnTerminar = view.findViewById(R.id.ibtnTerminar);

        ImageButton ibtnDedoGlicemia = view.findViewById(R.id.btnDedoGlicemia);

        CheckBox cbCafe = view.findViewById(R.id.checkBox39);
        CheckBox cbAlmoco = view.findViewById(R.id.checkBox27);
        CheckBox cbJantar = view.findViewById(R.id.checkBox24);
        CheckBox cbBesteirinhas = view.findViewById(R.id.checkBox28);
        CheckBox cbHiper = view.findViewById(R.id.checkBox25);
        CheckBox cbHipo = view.findViewById(R.id.checkBox29);
        CheckBox cbJejum = view.findViewById(R.id.checkBox26);
        CheckBox cbEsportes = view.findViewById(R.id.checkBox30);
        CheckBox cbdormir = view.findViewById(R.id.checkBox5);
        CheckBox cbMadrugada = view.findViewById(R.id.checkBox31);
        CheckBox cbEscritorio = view.findViewById(R.id.checkBox12);
        CheckBox cbCasa = view.findViewById(R.id.checkBox32);
        CheckBox cbManual = view.findViewById(R.id.checkBox13);
        CheckBox cbTurno = view.findViewById(R.id.checkBox33);
        CheckBox cbFesta = view.findViewById(R.id.checkBox17);
        CheckBox cbRessaca = view.findViewById(R.id.checkBox34);
        CheckBox cbDoente = view.findViewById(R.id.checkBox20);
        CheckBox cbAlergia = view.findViewById(R.id.checkBox35);
        CheckBox cbMentruacao = view.findViewById(R.id.checkBox21);
        CheckBox cbdor = view.findViewById(R.id.checkBox36);
        CheckBox cbDirigindo = view.findViewById(R.id.checkBox22);
        CheckBox cbViajando = view.findViewById(R.id.checkBox37);
        CheckBox cbCorrecao = view.findViewById(R.id.checkBox23);
        CheckBox cbAgulha = view.findViewById(R.id.checkBox38);

        clDedoGli = view.findViewById(R.id.clGlicemiaDedo);

        Calendar c = Calendar.getInstance();
        Hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

        database = ConfigFirebase.getFirebase();

        final CheckBox[] categoria = {cbCafe, cbAlmoco, cbJantar, cbBesteirinhas, cbHiper, cbHipo, cbJejum,
                cbEsportes, cbdormir, cbMadrugada, cbEscritorio, cbCasa, cbManual, cbTurno, cbFesta,
                cbRessaca, cbDoente, cbAlergia, cbMentruacao, cbdor, cbDirigindo, cbViajando,
                cbCorrecao, cbAgulha};

        final String[] strCat = { "Café", "Almoço", "Jantar", "Besteirinhas", "Hiper", "Hipo", "Jejum",
                "Esportes", "Dormir", "Madrugada", "Escritorio", "Casa", "Manual", "Turno", "Festa",
                "Ressaca", "Doente", "Alergia", "Menstruaçao", "Dor", "Dirigindo", "Viajando",
                "Correcao", "Agulha"};


        btnGliDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btnGliDia.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                        pDay = dayOfMonth;
                        pMonth = (month+1);
                        pYear = year;
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });


        btnHorarioGli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timepicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        hora = (hourOfDay * 60 + minute);

                        btnHorarioGli.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, Hour, min, true);
                timepicker.show();
            }
        });

        ibtnDedoGlicemia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.to_down);

                clDedoGli.startAnimation(animation);

                clDedoGli.setVisibility(View.VISIBLE);

            }
        });

        clDedoGli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.from_top);

                clDedoGli.startAnimation(animation);

                clDedoGli.setVisibility(View.GONE);

            }
        });

        btnProntoGli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.from_top);

                clDedoGli.startAnimation(animation);

                clDedoGli.setVisibility(View.GONE);

            }
        });

        ibtnTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsurario();

                numGlicemia = edtNivelGli.getText().toString();

                final String strSlcLocal = spLocalGli.getSelectedItem().toString();

                String lado;

                if(!edtNivelGli.getText().toString().equals("")){
                    if(!btnHorarioGli.getText().toString().equals("")){
                        if(btnGliDia.getText().equals("Hoje")){

                            pDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                            pMonth = (Calendar.getInstance().get(Calendar.MONTH)+1);
                            pYear = Calendar.getInstance().get(Calendar.YEAR);

                        }

                        for(int i = 0; i < categoria.length; i++) {
                            if(categoria[i].isChecked())
                                pCat.add(strCat[i]);
                        }

                        if(rdbDireita.isChecked())
                            lado = "direito";
                        else
                            lado = "esquerdo";


                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder.setTitle("Deseja mesmo salvar?");

                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                glicemia = new Glicemia();

                                calendarFragment = new CalendarFragment();

                                glicemia.setNivel(Double.parseDouble(numGlicemia));
                                glicemia.setLado(lado);
                                glicemia.setLocal(strSlcLocal);
                                glicemia.setCategoria(pCat.toString());
                                glicemia.setAno(pYear);
                                glicemia.setMes(pMonth);
                                glicemia.setDia(pDay);
                                glicemia.setHora(hora);

                                glicemia.salvar(String.valueOf(pDay), String.valueOf(pMonth), String.valueOf(pYear));

                                SharedPreferences.Editor preferences1 = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
                                preferences1.clear();
                                preferences1.apply();

                                preferences = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                                preferences.edit().putInt("dia", pDay).apply();
                                preferences.edit().putInt("mes", pMonth).apply();
                                preferences.edit().putInt("ano", pYear).apply();
                                preferences.edit().putInt("hora", hora).apply();

                                Toast.makeText(getActivity(), "Pronto, já salvamos :)", Toast.LENGTH_SHORT).show();

                                getActivity().finish();

                            }
                        });

                        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        builder.create();
                        builder.show();
                    } else {
                        Toast.makeText(getActivity(), "Por favor, preencha o campo Horário", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Por favor, preencha o campo Nível", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }

    public void recuperarUsurario() {
        FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

        String email = auth.getCurrentUser().getEmail();
        assert email != null;
        currentId = Base64Custom.codificarBase64(email);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}