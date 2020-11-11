package com.example.testepicpic.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.util.Base64;
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
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.testepicpic.R;
import com.example.testepicpic.activity.AddInfosActivity;
import com.example.testepicpic.activity.MainActivity;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.helper.Base64Custom;
import com.example.testepicpic.model.Alimentacao;
import com.example.testepicpic.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAlimentacaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAlimentacaoFragment extends Fragment {

    private Button btnAliDia;
    private ImageButton ibtnProximo;
    private ConstraintLayout clAddAli;
    private EditText edtDescricaoAli;

    private AddExercicioFragment addExercicioFragment = new AddExercicioFragment();

    private ArrayList<String> pComidasCafe = new ArrayList<>();
    private ArrayList<String> pComidasAlmoco = new ArrayList<>();
    private ArrayList<String> pComidasJanta = new ArrayList<>();
    private ArrayList<String> pComidasLanches = new ArrayList<>();

    private DatabaseReference ref;

    private String strPComidasCafe;

    private int pDay, pMonth, pYear;

    private int indice;

    private String currentId;

    private Alimentacao alimentacao = new Alimentacao();

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

        ref = ConfigFirebase.getFirebase();

        btnAliDia = view.findViewById(R.id.btnAliDia);

        Button btnAddAliAlmoco = view.findViewById(R.id.btnAddAliAlmoco);
        Button btnAddAliJanta = view.findViewById(R.id.btnAddAliJanta);
        Button btnAddAliCafe = view.findViewById(R.id.btnAddAliCafe);
        Button btnAddAliLanches = view.findViewById(R.id.btnAddAliLanches);

        Button btnSalvar = view.findViewById(R.id.btnSalvar);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);

        CheckBox cVegetais = view.findViewById(R.id.checkBox);
        CheckBox cFrutas = view.findViewById(R.id.checkBox3);
        CheckBox cLegumes = view.findViewById(R.id.checkBox4);
        CheckBox cGraos = view.findViewById(R.id.checkBox6);
        CheckBox cIntegrais = view.findViewById(R.id.checkBox7);
        CheckBox cBatata = view.findViewById(R.id.checkBox2);
        CheckBox cOvo = view.findViewById(R.id.checkBox8);
        CheckBox cLaticinios = view.findViewById(R.id.checkBox9);
        CheckBox cNozes = view.findViewById(R.id.checkBox10);
        CheckBox cPeixe = view.findViewById(R.id.checkBox11);
        CheckBox cCarne = view.findViewById(R.id.checkBox14);
        CheckBox cDoce = view.findViewById(R.id.checkBox15);
        CheckBox cAperitivos = view.findViewById(R.id.checkBox19);
        CheckBox cLanches = view.findViewById(R.id.checkBox16);
        CheckBox cAlcool = view.findViewById(R.id.checkBox18);
        CheckBox cAdocante = view.findViewById(R.id.checkbox20);
        CheckBox cSuplementos = view.findViewById(R.id.checkbox21);
        CheckBox cRefriDiet = view.findViewById(R.id.checkbox22);
        CheckBox cRefri = view.findViewById(R.id.checkbox23);

        ImageButton ibtnTerminar = view.findViewById(R.id.btnTerminar);
        ibtnProximo = view.findViewById(R.id.btnProximo);

        edtDescricaoAli = view.findViewById(R.id.edtDescicaoAli);

        clAddAli = view.findViewById(R.id.clAddAli);

        final CheckBox[] comidas = {cVegetais, cFrutas, cLegumes, cGraos, cIntegrais, cBatata, cOvo, cLaticinios, cNozes, cPeixe, cCarne, cDoce, cAperitivos, cLanches, cAlcool, cAdocante, cSuplementos, cRefriDiet, cRefri};

        final String[] listaComidas = {"Vegetais", "Frutas", "Legumes", "Grãos", "Integrais",
                "Batata", "Ovo", "Laticínios", "Nozes", "Peixe", "Carne", "Doce", "Aperitivos",
                "Lanches", "Alcool", "Adoçante", "Suplementos", "Refri Diet", "Refri"};

        //verificar se ja add
        recuperarUsurario();
        ref = ConfigFirebase.getFirebase();

        if(btnAliDia.getText().toString().equals("Hoje")) {
            pYear = Calendar.getInstance().get(Calendar.YEAR);
            pMonth = (Calendar.getInstance().get(Calendar.MONTH)+1);
            pDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }

        String data = String.valueOf(pYear) + String.valueOf(pMonth) + String.valueOf(pDay);
        DatabaseReference reference = ref.child("inserção")
                .child(currentId)
                .child("alimentação");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                    if(dataSnapshot.getKey().equals(data)) {

                        DatabaseReference reference1 = ref.child("inserção").child(currentId).child("alimentação").child(data);

                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                for( DataSnapshot dataSnapshot1 : snapshot1.getChildren() ) {

                                    switch (dataSnapshot1.getKey()) {

                                        case "café":
                                            btnAddAliCafe.setEnabled(false);
                                            btnAddAliCafe.setText("Adicionado");
                                            btnAddAliCafe.setTextColor(getResources().getColor(R.color.colorPrimary));
                                            break;
                                        case "almoço":
                                            btnAddAliAlmoco.setEnabled(false);
                                            btnAddAliAlmoco.setText("Adicionado");
                                            btnAddAliAlmoco.setTextColor(getResources().getColor(R.color.colorPrimary));
                                            break;
                                        case "janta":
                                            btnAddAliJanta.setEnabled(false);
                                            btnAddAliJanta.setText("Adicionado");
                                            btnAddAliJanta.setTextColor(getResources().getColor(R.color.colorPrimary));
                                            break;
                                        case "lanches":
                                            btnAddAliLanches.setEnabled(false);
                                            btnAddAliLanches.setText("Adicionado");
                                            btnAddAliLanches.setTextColor(getResources().getColor(R.color.colorPrimary));
                                            break;
                                    }

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });




                    }

                }

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
                        pMonth = (month+1);
                        pYear = year;

                        //verificar se ja add, trocar dia
                        recuperarUsurario();
                        ref = ConfigFirebase.getFirebase();

                        if(btnAliDia.getText().toString().equals("Hoje")) {
                            pYear = Calendar.getInstance().get(Calendar.YEAR);
                            pMonth = (Calendar.getInstance().get(Calendar.MONTH)+1);
                            pDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                        }

                        String data = String.valueOf(pYear) + String.valueOf(pMonth) + String.valueOf(pDay);
                        DatabaseReference reference = ref.child("inserção")
                                .child(currentId)
                                .child("alimentação");

                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                                    if(dataSnapshot.getKey().equals(data)) {

                                        DatabaseReference reference1 = ref.child("inserção").child(currentId).child("alimentação").child(data);

                                        reference1.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                                for( DataSnapshot dataSnapshot1 : snapshot1.getChildren() ) {

                                                    switch (dataSnapshot1.getKey()) {

                                                        case "café":
                                                            btnAddAliCafe.setEnabled(false);
                                                            btnAddAliCafe.setText("Adicionado");
                                                            btnAddAliCafe.setTextColor(getResources().getColor(R.color.colorPrimary));
                                                            break;
                                                        case "almoço":
                                                            btnAddAliAlmoco.setEnabled(false);
                                                            btnAddAliAlmoco.setText("Adicionado");
                                                            btnAddAliAlmoco.setTextColor(getResources().getColor(R.color.colorPrimary));
                                                            break;
                                                        case "janta":
                                                            btnAddAliJanta.setEnabled(false);
                                                            btnAddAliJanta.setText("Adicionado");
                                                            btnAddAliJanta.setTextColor(getResources().getColor(R.color.colorPrimary));
                                                            break;
                                                        case "lanches":
                                                            btnAddAliLanches.setEnabled(false);
                                                            btnAddAliLanches.setText("Adicionado");
                                                            btnAddAliLanches.setTextColor(getResources().getColor(R.color.colorPrimary));
                                                            break;
                                                    }

                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });




                                    } else {

                                        btnAddAliCafe.setEnabled(true);
                                        btnAddAliCafe.setText("Adicionar");
                                        btnAddAliCafe.setTextColor(getResources().getColor(R.color.preto));

                                        btnAddAliAlmoco.setEnabled(true);
                                        btnAddAliAlmoco.setText("Adicionar");
                                        btnAddAliAlmoco.setTextColor(getResources().getColor(R.color.preto));

                                        btnAddAliJanta.setEnabled(true);
                                        btnAddAliJanta.setText("Adicionar");
                                        btnAddAliJanta.setTextColor(getResources().getColor(R.color.preto));

                                        btnAddAliLanches.setEnabled(true);
                                        btnAddAliLanches.setText("Adicionar");
                                        btnAddAliLanches.setTextColor(getResources().getColor(R.color.preto));

                                    }

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

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

                recuperarUsurario();

                strPComidasCafe = edtDescricaoAli.getText().toString();

                ref = ConfigFirebase.getFirebase();

                if(btnAliDia.getText().toString().equals("Hoje")) {
                    pYear = Calendar.getInstance().get(Calendar.YEAR);
                    pMonth = (Calendar.getInstance().get(Calendar.MONTH)+1);
                    pDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                }

                switch(indice) {
                    case 0:
                        if(!strPComidasCafe.equals("")) {

                            AlertDialog.Builder dialogC = new AlertDialog.Builder(getActivity());

                            dialogC.setTitle("Deseja mesmo salvar?");

                            dialogC.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    try {

                                        pComidasCafe.clear();

                                        for(int i = 0; i < comidas.length; i++) {
                                            if(comidas[i].isChecked())
                                                pComidasCafe.add(listaComidas[i]);
                                        }

                                        alimentacao.setAlimentos(pComidasCafe.toString());
                                        alimentacao.setDescricao(strPComidasCafe);
                                        alimentacao.setTipo( "Café" );
                                        alimentacao.setAno(pYear);
                                        alimentacao.setMes(pMonth);
                                        alimentacao.setDia(pDay);

                                        alimentacao.salvarCafe(String.valueOf(pDay), String.valueOf(pMonth), String.valueOf(pYear));

                                        btnAddAliCafe.setEnabled(false);
                                        btnAddAliCafe.setText("Adicionado");
                                        btnAddAliCafe.setTextColor(getResources().getColor(R.color.colorPrimary));

                                        Toast.makeText(getActivity(), "Já salvamos :)", Toast.LENGTH_SHORT).show();

                                        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.from_top);

                                        clAddAli.startAnimation(animation);

                                        clAddAli.setVisibility(View.GONE);

                                        for(CheckBox comida : comidas) {
                                            if(comida.isChecked())
                                                comida.setChecked(false);
                                        }

                                        if(edtDescricaoAli.getText() != null)
                                            edtDescricaoAli.setText(null);

                                    } catch (Exception e) {
                                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            dialogC.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}});

                            dialogC.create();
                            dialogC.show();

                        } else {
                            Toast.makeText(getActivity(), "Por favor, preencha a descrição da refeição", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 1:
                        if(!strPComidasCafe.equals("")) {
                            AlertDialog.Builder dialogC = new AlertDialog.Builder(getActivity());

                            dialogC.setTitle("Deseja mesmo salvar?");

                            dialogC.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    try {

                                        pComidasAlmoco.clear();

                                        for(int i = 0; i < comidas.length; i++) {
                                            if(comidas[i].isChecked())
                                                pComidasAlmoco.add(listaComidas[i]);
                                        }

                                        alimentacao.setAlimentos(pComidasAlmoco.toString());
                                        alimentacao.setDescricao(strPComidasCafe);
                                        alimentacao.setTipo( "Almoço" );
                                        alimentacao.setDia(pDay);
                                        alimentacao.setMes(pMonth);
                                        alimentacao.setAno(pYear);

                                        alimentacao.salvarAlmoco(String.valueOf(pDay), String.valueOf(pMonth), String.valueOf(pYear));

                                        btnAddAliAlmoco.setEnabled(false);
                                        btnAddAliAlmoco.setText("Adicionado");
                                        btnAddAliAlmoco.setTextColor(getResources().getColor(R.color.colorPrimary));

                                        Toast.makeText(getActivity(), "Já salvamos :)", Toast.LENGTH_SHORT).show();

                                        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.from_top);

                                        clAddAli.startAnimation(animation);

                                        clAddAli.setVisibility(View.GONE);

                                        for(CheckBox comida : comidas) {
                                            if(comida.isChecked())
                                                comida.setChecked(false);
                                        }

                                        if(edtDescricaoAli.getText() != null)
                                            edtDescricaoAli.setText(null);

                                    } catch (Exception e) {
                                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            dialogC.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) { }});

                            dialogC.create();
                            dialogC.show();

                        } else {
                            Toast.makeText(getActivity(), "Por favor, preencha a descrição da refeição", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 2:
                        if(!strPComidasCafe.equals("")) {

                            AlertDialog.Builder dialogC = new AlertDialog.Builder(getActivity());

                            dialogC.setTitle("Deseja mesmo salvar?");

                            dialogC.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {

                                        pComidasJanta.clear();

                                        for(int i = 0; i < comidas.length; i++) {
                                            if(comidas[i].isChecked())
                                                pComidasJanta.add(listaComidas[i]);
                                        }

                                        alimentacao.setAlimentos(pComidasJanta.toString());
                                        alimentacao.setDescricao(strPComidasCafe);
                                        alimentacao.setTipo( "Janta" );
                                        alimentacao.setDia(pDay);
                                        alimentacao.setMes(pMonth);
                                        alimentacao.setAno(pYear);

                                        alimentacao.salvarJanta(String.valueOf(pDay), String.valueOf(pMonth), String.valueOf(pYear));

                                        btnAddAliJanta.setEnabled(false);
                                        btnAddAliJanta.setText("Adicionado");
                                        btnAddAliJanta.setTextColor(getResources().getColor(R.color.colorPrimary));

                                        Toast.makeText(getActivity(), "Já salvamos :)", Toast.LENGTH_SHORT).show();

                                        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.from_top);

                                        clAddAli.startAnimation(animation);

                                        clAddAli.setVisibility(View.GONE);

                                        for(CheckBox comida : comidas) {
                                            if(comida.isChecked())
                                                comida.setChecked(false);
                                        }

                                        if(edtDescricaoAli.getText() != null)
                                            edtDescricaoAli.setText(null);

                                    } catch (Exception e) {
                                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            dialogC.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}});

                            dialogC.create();
                            dialogC.show();

                        } else {
                            Toast.makeText(getActivity(), "Por favor, preencha a descrição da refeição", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 3:
                        if(!strPComidasCafe.equals("")) {
                            AlertDialog.Builder dialogC = new AlertDialog.Builder(getActivity());

                            dialogC.setTitle("Deseja mesmo salvar?");

                            dialogC.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    try {

                                        pComidasLanches.clear();

                                        for(int i = 0; i < comidas.length; i++) {
                                            if(comidas[i].isChecked())
                                                pComidasLanches.add(listaComidas[i]);
                                        }

                                        alimentacao.setAlimentos(pComidasLanches.toString());
                                        alimentacao.setDescricao(strPComidasCafe);
                                        alimentacao.setTipo( "Janta" );
                                        alimentacao.setDia(pDay);
                                        alimentacao.setMes(pMonth);
                                        alimentacao.setAno(pYear);

                                        alimentacao.salvarLanches(String.valueOf(pDay), String.valueOf(pMonth), String.valueOf(pYear));

                                        btnAddAliLanches.setEnabled(false);
                                        btnAddAliLanches.setText("Adicionado");
                                        btnAddAliLanches.setTextColor(getResources().getColor(R.color.colorPrimary));

                                        Toast.makeText(getActivity(), "Já salvamos :)", Toast.LENGTH_SHORT).show();

                                        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.from_top);

                                        clAddAli.startAnimation(animation);

                                        clAddAli.setVisibility(View.GONE);

                                        for(CheckBox comida : comidas) {
                                            if(comida.isChecked())
                                                comida.setChecked(false);
                                        }

                                        if(edtDescricaoAli.getText() != null)
                                            edtDescricaoAli.setText(null);

                                    } catch (Exception e) {
                                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            dialogC.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()  {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}});

                            dialogC.create();
                            dialogC.show();

                        } else {
                            Toast.makeText(getActivity(), "Por favor, preencha a descrição da refeição", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder cancelar = new AlertDialog.Builder(getActivity());
                cancelar.setTitle("Deseja apagar os itens selecionados?");
                cancelar.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                cancelar.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                cancelar.create();
                cancelar.show();

            }
        });

        ibtnTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getActivity() != null) {
                    getActivity().finish();
                }

            }
        });

        ibtnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations( R.anim.to_left, R.anim.from_right, R.anim.to_left, R.anim.from_right);
                transaction.replace(R.id.frameAddInfos, addExercicioFragment);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });

        return view;
    }

    public void recuperarUsurario() {
        FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

        if(auth.getCurrentUser() != null) {

            String email = auth.getCurrentUser().getEmail();
            assert email != null;
            currentId = Base64Custom.codificarBase64(email);

        }

    }

}