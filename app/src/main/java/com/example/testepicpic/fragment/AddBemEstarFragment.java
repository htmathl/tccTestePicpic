package com.example.testepicpic.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.testepicpic.R;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.helper.Base64Custom;
import com.example.testepicpic.model.BemEstar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddBemEstarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddBemEstarFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private RadioButton brabo, normal, triste, feliz, cansado, animado, relaxado, estressado;

    private AddInsulinaFragment addInsulinaFragment = new AddInsulinaFragment();

    private CheckBox cbFraqueza, cbNauseas, cbDoresRins, cbMudancaHumor, cbPerdaPeso, cbFormigamento,
            cbFome, cbCoceira, cbVisaoEmbacada, cbFadiga, cbUninarMuito, cbDorCabeca;

    private int pDay, pMonth, pYear;

    private EditText edtDecricao;

    private Button btnHumorDia;

    private ImageButton ibtnSalvar, ibtnProximo;

    private ArrayList<String> pSintomas = new ArrayList<>();

    private String currentId, humor;

    private DatabaseReference ref;

    private BemEstar bemEstar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddBemEstarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddBemEstar.
     */
    // TODO: Rename and change types and number of parameters
    public static AddBemEstarFragment newInstance(String param1, String param2) {
        AddBemEstarFragment fragment = new AddBemEstarFragment();
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
        View view =  inflater.inflate(R.layout.fragment_add_bem_estar, container, false);

        brabo = view.findViewById(R.id.btnAddHumorBrabo);
        triste = view.findViewById(R.id.btnAddHumorSad);
        feliz = view.findViewById(R.id.btnAddHumorBem);
        normal = view.findViewById(R.id.btnAddHumorNormal);
        cansado = view.findViewById(R.id.btnAddHumorCansado);
        relaxado = view.findViewById(R.id.btnAddHumorRelax);
        animado = view.findViewById(R.id.btnAddHumorAnim);
        estressado = view.findViewById(R.id.btnAddHumorStress);

        brabo.setOnCheckedChangeListener(this);
        triste.setOnCheckedChangeListener(this);
        feliz.setOnCheckedChangeListener(this);
        normal.setOnCheckedChangeListener(this);
        cansado.setOnCheckedChangeListener(this);
        relaxado.setOnCheckedChangeListener(this);
        animado.setOnCheckedChangeListener(this);
        estressado.setOnCheckedChangeListener(this);

        btnHumorDia = view.findViewById(R.id.btnInsulinaDia);

        edtDecricao = view.findViewById(R.id.editTextDescriçãoHumor);

        cbFraqueza =  view.findViewById(R.id.checkFraqueza);
        cbNauseas =  view.findViewById(R.id.checkVomito);
        cbDoresRins =  view.findViewById(R.id.checkRins);
        cbMudancaHumor =  view.findViewById(R.id.checkBipolar);
        cbPerdaPeso =  view.findViewById(R.id.checkPPeso);
        cbFormigamento =  view.findViewById(R.id.checkPezinho);
        cbFome =  view.findViewById(R.id.checkFome);
        cbCoceira =  view.findViewById(R.id.checkCoceira);
        cbVisaoEmbacada =  view.findViewById(R.id.checkVisao);
        cbFadiga =  view.findViewById(R.id.checkFadiga);
        cbUninarMuito =  view.findViewById(R.id.checkUrina);
        cbDorCabeca =  view.findViewById(R.id.checkCabeca);

        ibtnSalvar = view.findViewById(R.id.btnTerminar);
        ibtnProximo = view.findViewById(R.id.btnProximo1);

        final CheckBox[] listaSintomas = {
                cbFraqueza, cbNauseas, cbDoresRins, cbMudancaHumor, cbPerdaPeso, cbFormigamento,
                cbFome, cbCoceira, cbVisaoEmbacada, cbFadiga, cbUninarMuito, cbDorCabeca,
        };

        final RadioButton[] listaHumor = {
                brabo, normal, triste, feliz, cansado, animado, relaxado, estressado,
        };

        final String[] strListaSintomas = {
                "Fraqueza", "Náuseas", "Dores Rins", "Mudanças de Húmor", "Perda de Peso", "Formigamento",
                "Fome", "Coceira", "Visão Embacada", "Fadiga", "Unrinar Muito", "Dor Cabeça",
        };

        btnHumorDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btnHumorDia.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                        pDay = dayOfMonth;
                        pMonth = (month+1);
                        pYear = year;
                    }
                }, year, month, day);

                datePickerDialog.show();

            }
        });


        ibtnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                ref = ConfigFirebase.getFirebase();

                for(RadioButton pHumor : listaHumor) {
                    if(pHumor.isChecked())
                        humor = pHumor.getText().toString();
                }

                final String descricao = edtDecricao.getText().toString();

                for(int i = 0; i < 12; i++) {
                    if(listaSintomas[i].isChecked())
                        pSintomas.add(strListaSintomas[i]);
                }

                if(btnHumorDia.getText().toString().equals("Hoje")) {
                    pYear = Calendar.getInstance().get(Calendar.YEAR);
                    pMonth = (Calendar.getInstance().get(Calendar.MONTH)+1);
                    pDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                }

                if(!edtDecricao.getText().toString().equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Deseja mesmo salvar?");

                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            try {

                                bemEstar = new BemEstar();

                                bemEstar.setSintomas(humor);
                                bemEstar.setDescicao(descricao);
                                bemEstar.setSintomas(pSintomas.toString());
                                bemEstar.setDia(pDay);
                                bemEstar.setMes(pMonth);
                                bemEstar.setAno(pYear);

                                bemEstar.salvar(String.valueOf(pDay), String.valueOf(pMonth), String.valueOf(pYear));

                                Toast.makeText(getActivity(), "Pronto, já salvamos sua anotação :)", Toast.LENGTH_SHORT).show();
                                getActivity().finish();

                            } catch (Exception e) {
                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}});

                    builder.create();
                    builder.show();

                } else {
                    Toast.makeText(getActivity(), "Por favor, preencha o campo descrição", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ibtnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations( R.anim.to_left, R.anim.from_right, R.anim.to_left, R.anim.from_right);
                transaction.replace(R.id.frameAddInfos, addInsulinaFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            if(buttonView.getId() == R.id.btnAddHumorBrabo){
                triste.setChecked(false);
                feliz.setChecked(false);
                normal.setChecked(false);
                cansado.setChecked(false);
                animado.setChecked(false);
                relaxado.setChecked(false);
                estressado.setChecked(false);
            }
            if(buttonView.getId() == R.id.btnAddHumorSad){
                brabo.setChecked(false);
                feliz.setChecked(false);
                normal.setChecked(false);
                cansado.setChecked(false);
                animado.setChecked(false);
                relaxado.setChecked(false);
                estressado.setChecked(false);
            }
            if(buttonView.getId() == R.id.btnAddHumorBem){
                triste.setChecked(false);
                brabo.setChecked(false);
                normal.setChecked(false);
                cansado.setChecked(false);
                animado.setChecked(false);
                relaxado.setChecked(false);
                estressado.setChecked(false);
            }
            if(buttonView.getId() == R.id.btnAddHumorNormal){
                triste.setChecked(false);
                feliz.setChecked(false);
                brabo.setChecked(false);
                cansado.setChecked(false);
                animado.setChecked(false);
                relaxado.setChecked(false);
                estressado.setChecked(false);
            }
            if(buttonView.getId() == R.id.btnAddHumorCansado){
                triste.setChecked(false);
                feliz.setChecked(false);
                brabo.setChecked(false);
                normal.setChecked(false);
                animado.setChecked(false);
                relaxado.setChecked(false);
                estressado.setChecked(false);
            }
            if(buttonView.getId() == R.id.btnAddHumorAnim){
                triste.setChecked(false);
                feliz.setChecked(false);
                brabo.setChecked(false);
                cansado.setChecked(false);
                normal.setChecked(false);
                relaxado.setChecked(false);
                estressado.setChecked(false);
            }
            if(buttonView.getId() == R.id.btnAddHumorRelax){
                triste.setChecked(false);
                feliz.setChecked(false);
                brabo.setChecked(false);
                cansado.setChecked(false);
                animado.setChecked(false);
                normal.setChecked(false);
                estressado.setChecked(false);
            }
            if(buttonView.getId() == R.id.btnAddHumorStress){
                triste.setChecked(false);
                feliz.setChecked(false);
                brabo.setChecked(false);
                cansado.setChecked(false);
                animado.setChecked(false);
                relaxado.setChecked(false);
                normal.setChecked(false);
            }
        }
    }

    public void recuperarUsuario() {

        FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

        if(auth.getCurrentUser() != null) {

            String email = auth.getCurrentUser().getEmail();
            assert email != null;
            currentId = Base64Custom.codificarBase64(email);

        }

    }

}