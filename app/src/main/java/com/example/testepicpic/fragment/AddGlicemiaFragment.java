package com.example.testepicpic.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.testepicpic.R;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddGlicemiaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddGlicemiaFragment extends Fragment {
    private Button btnHorarioGli, btnGliDia;

    private int Hour, min, hora;

    private EditText edtNivelGli;

    private ImageButton ibtnTerminar, ibtnProximo;

    private int pDay, pMonth, pYear;

    private DatabaseReference database;

    private String currentId, numGlicemia;

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

        ibtnProximo = view.findViewById(R.id.ibtnProxima);
        ibtnTerminar = view.findViewById(R.id.ibtnTerminar);

        Calendar c = Calendar.getInstance();
        Hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

        database = ConfigFirebase.getFirebase();

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

        ibtnTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsurario();

                numGlicemia = edtNivelGli.getText().toString();

                if(!edtNivelGli.getText().toString().equals("")){
                    if(!btnHorarioGli.getText().toString().equals("")){
                        if(btnGliDia.getText().equals("Hoje")){

                            pDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                            pMonth = (Calendar.getInstance().get(Calendar.MONTH)+1);
                            pYear = Calendar.getInstance().get(Calendar.YEAR);

                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder.setTitle("Deseja mesmo salvar?");

                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                database.child("users")
                                        .child(currentId)
                                        .child("inserção")
                                        .child("glicemia")
                                        .child(String.valueOf(pYear))
                                        .child(String.valueOf(pMonth))
                                        .child(String.valueOf(pDay))
                                        .child("Nível")
                                        .setValue(numGlicemia);

                                database.child("users")
                                        .child(currentId)
                                        .child("inserção")
                                        .child("glicemia")
                                        .child(String.valueOf(pYear))
                                        .child(String.valueOf(pMonth))
                                        .child(String.valueOf(pDay))
                                        .child("Horário")
                                        .setValue(hora);

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
}