package com.example.testepicpic.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testepicpic.R;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAlimentacaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAlimentacaoFragment extends Fragment {

    private Button btnAddAliCafe, btnAddAliAlmoco, btnAddAliJanta, btnAddAliLanches, btnCancelar, btnAliDia, btnSalvar;
    private ConstraintLayout clAddAli;
    private CheckBox cVegetais, cFrutas, cLegumes, cGraos, cIntegrais, cBatata, cOvo, cLaticinios, cNozes, cPeixe, cCarne, cDoce, cAperitivos, cLanches, cAlcool, cAdocante, cSuplementos, cRefriDiet, cRefri;
    private EditText edtDescricaoAli;

    private boolean[] pComidasCafe = new boolean[19];
    private boolean[] pComidasAlmoco = new boolean[19];
    private boolean[] pComidasJanta = new boolean[19];
    private boolean[] pComidasLanches = new boolean[19];

    private DatabaseReference reference, ref;
    private Usuario user = new Usuario();

    private String strPComidasCafe, strpComidasAlmoco, strpComidasJanta, strpComidasLanches;

    private int pDay, pMonth, pYear;

    private int indice;

    private String id;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddAlimentacaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddAlimentacao.
     */
    // TODO: Rename and change types and number of parameters
    public static AddAlimentacaoFragment newInstance(String param1, String param2) {
        AddAlimentacaoFragment fragment = new AddAlimentacaoFragment();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_alimentacao, container, false);

        btnAliDia = view.findViewById(R.id.btnAliDia);

        btnAddAliAlmoco = view.findViewById(R.id.btnAddAliAlmoco);
        btnAddAliJanta = view.findViewById(R.id.btnAddAliJanta);
        btnAddAliCafe = view.findViewById(R.id.btnAddAliCafe);
        btnAddAliLanches = view.findViewById(R.id.btnAddAliLanches);

        btnSalvar = view.findViewById(R.id.btnSalvar);
        btnCancelar = view.findViewById(R.id.btnCancelar);

        cVegetais = view.findViewById(R.id.checkBox);
        cFrutas = view.findViewById(R.id.checkBox3);
        cLegumes = view.findViewById(R.id.checkBox4);
        cGraos = view.findViewById(R.id.checkBox6);
        cIntegrais = view.findViewById(R.id.checkBox7);
        cBatata = view.findViewById(R.id.checkBox2);
        cOvo = view.findViewById(R.id.checkBox8);
        cLaticinios = view.findViewById(R.id.checkBox9);
        cNozes = view.findViewById(R.id.checkBox10);
        cPeixe = view.findViewById(R.id.checkBox11);
        cCarne = view.findViewById(R.id.checkBox14);
        cDoce = view.findViewById(R.id.checkBox15);
        cAperitivos = view.findViewById(R.id.checkBox19);
        cLanches = view.findViewById(R.id.checkBox16);
        cAlcool = view.findViewById(R.id.checkBox18);
        cAdocante = view.findViewById(R.id.checkbox20);
        cSuplementos = view.findViewById(R.id.checkbox21);
        cRefriDiet = view.findViewById(R.id.checkbox22);
        cRefri = view.findViewById(R.id.checkbox23);

        edtDescricaoAli = view.findViewById(R.id.edtDescicaoAli);

        clAddAli = view.findViewById(R.id.clAddAli);

        final CheckBox[] comidas = {cVegetais, cFrutas, cLegumes, cGraos, cIntegrais, cBatata, cOvo, cLaticinios, cNozes, cPeixe, cCarne, cDoce, cAperitivos, cLanches, cAlcool, cAdocante, cSuplementos, cRefriDiet, cRefri};

        final String[] listaComidas = {"Vegetais", "Frutas", "Legumes", "Grãos", "Integrais",
                "Batata", "Ovo", "Laticínios", "Nozes", "Peixe", "Carne", "Doce", "Aperitivos",
                "Lanches", "Alcool", "Adocante", "Suplementos", "Refri Diet", "Refri"};

        reference = ConfigFirebase.getFirebase();

        ref = reference.child("users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("MIAU", snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnAliDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btnAliDia.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                        pDay = dayOfMonth;
                        pMonth = month;
                        pYear = year;
                    }
                }, year, month, day);

                datePickerDialog.show();

            }
        });

        btnAddAliCafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                indice = 0;

                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.to_down);

                clAddAli.startAnimation(animation);

                clAddAli.setVisibility(View.VISIBLE);

            }
        });

        btnAddAliAlmoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                indice = 1;

                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.to_down);

                clAddAli.startAnimation(animation);

                clAddAli.setVisibility(View.VISIBLE);

            }
        });

        btnAddAliJanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                indice = 2;

                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.to_down);

                clAddAli.startAnimation(animation);

                clAddAli.setVisibility(View.VISIBLE);


            }
        });

        btnAddAliLanches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                indice = 3;

                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.to_down);

                clAddAli.startAnimation(animation);

                clAddAli.setVisibility(View.VISIBLE);

            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strPComidasCafe = edtDescricaoAli.getText().toString();


                if(btnAliDia.getText().toString().equals("Hoje")) {
                    pDay = Calendar.DAY_OF_MONTH;
                    pMonth = Calendar.MONTH;
                    pYear = Calendar.YEAR;
                }

                for(int i = 0; i < comidas.length; i++) {
                    if(comidas[i].isChecked())
                        pComidasCafe[i] = true;
                }

                Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();

                switch(indice) {
                    case 0:

                        /*for(int i = 0; i < pComidasCafe.length; i++) {
                            reference.child("users")
                                    .child(user.getIdUser())
                                    .child("inserção")
                                    .child("alimentação")
                                    .child("café")
                                    .child(String.valueOf(pYear))
                                    .child(String.valueOf(pMonth))
                                    .child(String.valueOf(pDay))
                                    .child(listaComidas[i])
                                    .setValue(pComidasCafe[i]);
                        }

                        reference.child("users")
                                .child(user.getIdUser())
                                .child("inserção")
                                .child("alimentação")
                                .child("café")
                                .child(String.valueOf(pYear))
                                .child(String.valueOf(pMonth))
                                .child(String.valueOf(pDay))
                                .child("descrição")
                                .setValue(strPComidasCafe);

*/
                        break;
                    case 1:

                        break;
                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.from_top);

                clAddAli.startAnimation(animation);

                clAddAli.setVisibility(View.GONE);

                for(CheckBox comida : comidas) {
                    if(comida.isChecked())
                        comida.setChecked(false);
                }

                if(edtDescricaoAli.getText() != null)
                    edtDescricaoAli.setText(null);

            }
        });

        return view;
    }


}