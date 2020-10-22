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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
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
 * Use the {@link AddInsulinaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddInsulinaFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Button btnInsulinaDia, btnTimeInsulina;

    private EditText edtNumInsulina;

    private Spinner spLocal;

    private RadioButton rdbRapida, rdbDevagar;

    private ImageButton ibtnTermiar;

    private int pDay, pMonth, pYear;

    private int Hour, min, hora;

    private DatabaseReference database;

    private String currentId;

    private String numInsulina;

    private String local, categoria;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddInsulinaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddInsulina.
     */
    // TODO: Rename and change types and number of parameters
    public static AddInsulinaFragment newInstance(String param1, String param2) {
        AddInsulinaFragment fragment = new AddInsulinaFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_insulina, container, false);

        btnInsulinaDia = view.findViewById(R.id.btnInsulinaDia);
        btnTimeInsulina = view.findViewById(R.id.btnTimeInsulina);

        edtNumInsulina = view.findViewById(R.id.edtNumInsulina);

        spLocal = view.findViewById(R.id.spLocal);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.localAplicacaoInsulina, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocal.setAdapter(adapter);

        spLocal.setOnItemSelectedListener(this);

        ibtnTermiar = view.findViewById(R.id.ibtnTerminar);

        rdbDevagar = view.findViewById(R.id.rdbDevagar);
        rdbRapida = view.findViewById(R.id.rdbRapida);

        Calendar c = Calendar.getInstance();
        Hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

        btnInsulinaDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btnInsulinaDia.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                        pDay = dayOfMonth;
                        pMonth = (month+1);
                        pYear = year;
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });


        btnTimeInsulina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        hora = (hourOfDay * 60 + minute);

                        btnTimeInsulina.setText(String.format("%02d:%02d", hourOfDay, minute));

                    }
                }, Hour, min, true);

                timePickerDialog.show();
            }
        });


        ibtnTermiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsurario();

                database = ConfigFirebase.getFirebase();

                numInsulina = edtNumInsulina.getText().toString();

                local = spLocal.getSelectedItem().toString();

                if(!edtNumInsulina.getText().toString().equals("")) {
                    if (!btnTimeInsulina.getText().toString().equals("")) {

                        if(btnInsulinaDia.getText().equals("Hoje")) {

                            pDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                            pMonth = (Calendar.getInstance().get(Calendar.MONTH)+1);
                            pYear = Calendar.getInstance().get(Calendar.YEAR);

                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder.setTitle("Deseja mesmo salvar?");

                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(rdbDevagar.isChecked()) {
                                    categoria = "Devagar";

                                    database.child("users")
                                            .child(currentId)
                                            .child("inserção")
                                            .child("insulina")
                                            .child(categoria)
                                            .child(String.valueOf(pYear))
                                            .child(String.valueOf(pMonth))
                                            .child(String.valueOf(pDay))
                                            .child("nível")
                                            .setValue(numInsulina);

                                    database.child("users")
                                            .child(currentId)
                                            .child("inserção")
                                            .child("insulina")
                                            .child(categoria)
                                            .child(String.valueOf(pYear))
                                            .child(String.valueOf(pMonth))
                                            .child(String.valueOf(pDay))
                                            .child("horário")
                                            .setValue(hora);

                                    database.child("users")
                                            .child(currentId)
                                            .child("inserção")
                                            .child("insulina")
                                            .child(categoria)
                                            .child(String.valueOf(pYear))
                                            .child(String.valueOf(pMonth))
                                            .child(String.valueOf(pDay))
                                            .child("local da aplicação")
                                            .setValue(local);
                                }
                                if(rdbRapida.isChecked()) {

                                    categoria = "Rápida";

                                    database.child("users")
                                            .child(currentId)
                                            .child("inserção")
                                            .child("insulina")
                                            .child(categoria)
                                            .child(String.valueOf(pYear))
                                            .child(String.valueOf(pMonth))
                                            .child(String.valueOf(pDay))
                                            .child("nível")
                                            .setValue(numInsulina);

                                    database.child("users")
                                            .child(currentId)
                                            .child("inserção")
                                            .child("insulina")
                                            .child(categoria)
                                            .child(String.valueOf(pYear))
                                            .child(String.valueOf(pMonth))
                                            .child(String.valueOf(pDay))
                                            .child("horário")
                                            .setValue(hora);

                                    database.child("users")
                                            .child(currentId)
                                            .child("inserção")
                                            .child("insulina")
                                            .child(categoria)
                                            .child(String.valueOf(pYear))
                                            .child(String.valueOf(pMonth))
                                            .child(String.valueOf(pDay))
                                            .child("local da aplicação")
                                            .setValue(local);
                                }

                                Toast.makeText(getActivity(), "Pronto, já salvamos :)", Toast.LENGTH_SHORT).show();

                                getActivity().finish();

                            }
                        });

                        builder.setNegativeButton("não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        builder.create();
                        builder.show();


                    } else {
                        Toast.makeText(getActivity(), "Por favor, preencha o campo horário", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Por favor, preencha o campo Nível", Toast.LENGTH_SHORT).show();
                }

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

    public void recuperarUsurario() {
        FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

        String email = auth.getCurrentUser().getEmail();
        assert email != null;
        currentId = Base64Custom.codificarBase64(email);

    }

}