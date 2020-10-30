package com.example.testepicpic.fragment;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.testepicpic.R;
import com.example.testepicpic.activity.*;
import com.example.testepicpic.broadcast.DayChangedBroadcastReceiver;
import com.example.testepicpic.config.*;
import com.example.testepicpic.helper.Base64Custom;
import com.example.testepicpic.model.Usuario;
import com.example.testepicpic.service.MesmoDia;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends Fragment {

    private CheckBox copo1, copo2, copo3, copo4, copo5, copo6, copo7, copo8, copo9, copo10, copo11,
            copo12, copo13, copo14, copo15, copo16;

    private LinearLayout tbOutrosCopos, linearSalvar;

    private int qntd;

    private FirebaseAuth autenticacao;

    private TextView txtQntd;

    private DatabaseReference ref;

    private String currentId;

    private int pYear, pMonth, pDay;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment overviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OverviewFragment newInstance(String param1, String param2) {
        OverviewFragment fragment = new OverviewFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_overview, container, false);

        ref = ConfigFirebase.getFirebase();

        pYear = Calendar.getInstance().get(Calendar.YEAR);
        pMonth = (Calendar.getInstance().get(Calendar.MONTH)+1);
        pDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        Button btnSairProv = view.findViewById(R.id.btnExames);

        copo1 = view.findViewById(R.id.copo1);
        copo2 = view.findViewById(R.id.copo2);
        copo3 = view.findViewById(R.id.copo3);
        copo4 = view.findViewById(R.id.copo4);
        copo5 = view.findViewById(R.id.copo5);
        copo6 = view.findViewById(R.id.copo6);
        copo7 = view.findViewById(R.id.copo7);
        copo8 = view.findViewById(R.id.copo8);
        copo9 = view.findViewById(R.id.copo9);
        copo10 = view.findViewById(R.id.copo10);
        copo11 = view.findViewById(R.id.copo11);
        copo12 = view.findViewById(R.id.copo12);
        copo13 = view.findViewById(R.id.copo13);
        copo14 = view.findViewById(R.id.copo14);
        copo15 = view.findViewById(R.id.copo15);
        copo16 = view.findViewById(R.id.copo16);

        tbOutrosCopos = view.findViewById(R.id.tbrOutrosCopos);
        linearSalvar = view.findViewById(R.id.linearSalvar);

        txtQntd = view.findViewById(R.id.txtQntd);

        final CheckBox[] listaCopos = {copo1, copo2, copo3, copo4, copo5, copo6, copo7, copo8, copo9, copo10, copo11,
                copo12, copo13, copo14, copo15, copo16};

        recuperarUsuario();

        try {

            final DatabaseReference reference = ref.child("users")
                    .child(currentId)
                    .child("inserção")
                    .child("bem-estar")
                    .child(String.valueOf(pYear))
                    .child(String.valueOf(pMonth))
                    .child(String.valueOf(pDay))
                    .child("Água");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String agua = snapshot.getValue().toString();

                    switch (agua) {

                        case "0":
                            for(CheckBox copo : listaCopos)
                                copo.setChecked(false);
                            txtQntd.setText(0 + " ml");
                            tbOutrosCopos.setVisibility(View.GONE);
                            break;
                        case "1":
                            copo1.setChecked(true);
                            copo2.setChecked(true);
                            txtQntd.setText(250 + " ml");
                            tbOutrosCopos.setVisibility(View.GONE);
                            break;
                        case "2":
                            for(int i = 0; i < 2; i++)
                                listaCopos[i].setChecked(true);
                            txtQntd.setText(500 + " ml");
                            tbOutrosCopos.setVisibility(View.GONE);
                            break;
                        case "3":
                            for(int i = 0; i < 3; i++)
                                listaCopos[i].setChecked(true);
                            txtQntd.setText(750 + " ml");
                            tbOutrosCopos.setVisibility(View.GONE);
                            break;
                        case "4":
                            for(int i = 0; i < 4; i++)
                                listaCopos[i].setChecked(true);
                            txtQntd.setText(1000 + " ml");
                            tbOutrosCopos.setVisibility(View.GONE);
                            break;
                        case "5":
                            for(int i = 0; i < 5; i++)
                                listaCopos[i].setChecked(true);
                            txtQntd.setText(1250 + " ml");
                            tbOutrosCopos.setVisibility(View.GONE);
                            break;
                        case "6":
                            for(int i = 0; i < 6; i++)
                                listaCopos[i].setChecked(true);
                            txtQntd.setText(1500 + " ml");
                            tbOutrosCopos.setVisibility(View.GONE);
                            break;
                        case "7":
                            for(int i = 0; i < 7; i++)
                                listaCopos[i].setChecked(true);
                            txtQntd.setText(1750 + " ml");
                            tbOutrosCopos.setVisibility(View.GONE);
                            break;
                        case "8":
                            for(int i = 0; i < 8; i++)
                                listaCopos[i].setChecked(true);
                            txtQntd.setText(2000 + " ml");
                            tbOutrosCopos.setVisibility(View.VISIBLE);
                            break;
                        case "9":
                            for(int i = 0; i < 9; i++)
                                listaCopos[i].setChecked(true);
                            txtQntd.setText(2250 + " ml");
                            tbOutrosCopos.setVisibility(View.VISIBLE);
                            break;
                        case "10":
                            for(int i = 0; i < 10; i++)
                                listaCopos[i].setChecked(true);
                            txtQntd.setText(2500 + " ml");
                            tbOutrosCopos.setVisibility(View.VISIBLE);
                            break;
                        case "11":
                            for(int i = 0; i < 11; i++)
                                listaCopos[i].setChecked(true);
                            txtQntd.setText(2750 + " ml");
                            tbOutrosCopos.setVisibility(View.VISIBLE);
                            break;
                        case "12":
                            for(int i = 0; i < 12; i++)
                                listaCopos[i].setChecked(true);
                            txtQntd.setText(3000 + " ml");
                            tbOutrosCopos.setVisibility(View.VISIBLE);
                            break;
                        case "13":
                            for(int i = 0; i < 13; i++)
                                listaCopos[i].setChecked(true);
                            txtQntd.setText(3250 + " ml");
                            tbOutrosCopos.setVisibility(View.VISIBLE);
                            break;
                        case "14":
                            for(int i = 0; i < 14; i++)
                                listaCopos[i].setChecked(true);
                            txtQntd.setText(3500 + " ml");
                            tbOutrosCopos.setVisibility(View.VISIBLE);
                            break;
                        case "15":
                            for(int i = 0; i < 15; i++)
                                listaCopos[i].setChecked(true);
                            txtQntd.setText(3750 + " ml");
                            tbOutrosCopos.setVisibility(View.VISIBLE);
                            break;
                        case "16":
                            for(int i = 0; i < 16; i++)
                                listaCopos[i].setChecked(true);
                            txtQntd.setText(4000 + " ml");
                            tbOutrosCopos.setVisibility(View.VISIBLE);
                            break;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }});

        } catch (Exception e) {

            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();

        }

        if(getActivity() != null) {
            getActivity().startService(new Intent(getActivity(), MesmoDia.class));
            getActivity().registerReceiver(dayChangedBroadcastReceiver, DayChangedBroadcastReceiver.getIntentFilter());
        }

        copo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                if(!copo1.isChecked()) {

                   for(CheckBox copo : listaCopos)
                       copo.setChecked(false);

                    tbOutrosCopos.setVisibility(View.GONE);

                    txtQntd.setText(0 + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(0);

                } else {

                    qntd = 0;
                    qntd = qntd + 250;
                    txtQntd.setText(qntd + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(1);

                }

            }
        });

        copo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                if(!copo2.isChecked()) {
                    for(int i = 1; i < listaCopos.length; i++)
                        listaCopos[i].setChecked(false);

                    tbOutrosCopos.setVisibility(View.GONE);

                    txtQntd.setText(250 + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(1);

                } else {
                    copo1.setChecked(true);
                    qntd = 0;
                    qntd = qntd + 500;
                    txtQntd.setText(qntd + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(2);
                }

            }
        });

        copo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                if(!copo3.isChecked()) {
                    for(int i = 2; i < listaCopos.length; i++)
                        listaCopos[i].setChecked(false);

                    tbOutrosCopos.setVisibility(View.GONE);

                    txtQntd.setText(500 + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(2);

                } else {
                    for(int i = 0; i < 2; i++)
                        listaCopos[i].setChecked(true);
                    qntd = 0;
                    qntd = qntd + 750;
                    txtQntd.setText(qntd + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(3);
                }

            }
        });

        copo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                if(!copo4.isChecked()) {
                    for(int i = 3; i < listaCopos.length; i++)
                        listaCopos[i].setChecked(false);


                    tbOutrosCopos.setVisibility(View.GONE);

                    txtQntd.setText(750 + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(3);

                } else {
                    for(int i = 0; i < 3; i++)
                        listaCopos[i].setChecked(true);
                    qntd = 0;
                    qntd = qntd + 1000;
                    txtQntd.setText(qntd + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(4);
                }

                }


        });

        copo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                if(!copo5.isChecked()) {
                    for(int i = 4; i < listaCopos.length; i++)
                        listaCopos[i].setChecked(false);

                    tbOutrosCopos.setVisibility(View.GONE);
                    txtQntd.setText(1000 + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(4);

                } else {
                    for(int i = 0; i < 4; i++)
                        listaCopos[i].setChecked(true);
                    qntd = 0;
                    qntd = qntd + 1250;
                    txtQntd.setText(qntd + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(5);
                }

                }


        });

        copo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                if(!copo6.isChecked()) {
                    for(int i = 5; i < listaCopos.length; i++)
                        listaCopos[i].setChecked(false);

                    tbOutrosCopos.setVisibility(View.GONE);
                    txtQntd.setText(1250 + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(5);

                } else {
                    for(int i = 0; i < 5; i++)
                        listaCopos[i].setChecked(true);
                    qntd = 0;
                    qntd = qntd + 1500;
                    txtQntd.setText(qntd + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(6);
                }

                }

        });

        copo7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                if(!copo7.isChecked()) {
                    for(int i = 6; i < listaCopos.length; i++)
                        listaCopos[i].setChecked(false);


                    tbOutrosCopos.setVisibility(View.GONE);
                    txtQntd.setText(1500 + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(6);

                } else {
                    for(int i = 0; i < 6; i++)
                        listaCopos[i].setChecked(true);
                    qntd = 0;
                    qntd = qntd + 1750;
                    txtQntd.setText(qntd + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(7);
                }

                }

        });

        copo8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                if(!copo8.isChecked()) {
                    for(int i = 7; i < listaCopos.length; i++)
                        listaCopos[i].setChecked(false);

                    tbOutrosCopos.setVisibility(View.GONE);
                    txtQntd.setText(1750 + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(7);

                } else {
                    for(int i = 0; i < 7; i++)
                        listaCopos[i].setChecked(true);

                    tbOutrosCopos.setVisibility(View.VISIBLE);
                    qntd = 0;
                    qntd = qntd + 2000;
                    txtQntd.setText(qntd + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(8);

                }

            }


        });

        copo9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                if(!copo9.isChecked()) {
                    for(int i = 8; i < listaCopos.length; i++)
                        listaCopos[i].setChecked(false);
                    txtQntd.setText(2000 + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(8);

                } else {
                    for(int i = 0; i < 8; i++)
                        listaCopos[i].setChecked(true);
                    qntd = 0;
                    qntd = qntd + 2250;
                    txtQntd.setText(qntd + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(9);
                }

            }
        });

        copo10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                if(!copo10.isChecked()) {
                    for(int i = 9; i < listaCopos.length; i++)
                        listaCopos[i].setChecked(false);
                    txtQntd.setText(2250 + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(9);

                } else {
                    for(int i = 0; i < 9; i++)
                        listaCopos[i].setChecked(true);
                    qntd = 0;
                    qntd = qntd + 2500;
                    txtQntd.setText(qntd + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(10);
                }

            }


        });

        copo11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                if(!copo11.isChecked()) {
                    for(int i = 10; i < listaCopos.length; i++)
                        listaCopos[i].setChecked(false);
                    txtQntd.setText(2500 + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(10);

                } else {
                    for(int i = 0; i < 10; i++)
                        listaCopos[i].setChecked(true);
                    qntd = 0;
                    qntd = qntd + 2750;
                    txtQntd.setText(qntd + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(11);
                }

            }


        });

        copo12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                if(!copo12.isChecked()) {
                    for(int i = 11; i < listaCopos.length; i++)
                        listaCopos[i].setChecked(false);
                    txtQntd.setText(2750 + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(11);

                } else {
                    for(int i = 0; i < 11; i++)
                        listaCopos[i].setChecked(true);
                    qntd = 0;
                    qntd = qntd + 3000;
                    txtQntd.setText(qntd + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(12);
                }

            }


        });

        copo13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                if(!copo13.isChecked()) {
                    for(int i = 12; i < listaCopos.length; i++)
                        listaCopos[i].setChecked(false);
                    txtQntd.setText(3000 + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(12);

                } else {
                    for(int i = 0; i < 12; i++)
                        listaCopos[i].setChecked(true);
                    qntd = 0;
                    qntd = qntd + 3250;
                    txtQntd.setText(qntd + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(13);
                }

            }


        });

        copo14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                if(!copo14.isChecked()) {
                    for(int i = 13; i < listaCopos.length; i++)
                        listaCopos[i].setChecked(false);
                    txtQntd.setText(3250 + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(13);

                } else {
                    for(int i = 0; i < 13; i++)
                        listaCopos[i].setChecked(true);
                    qntd = 0;
                    qntd = qntd + 3500;
                    txtQntd.setText(qntd + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(14);
                }

            }


        });

        copo15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                if(!copo15.isChecked()) {
                    for(int i = 14; i < listaCopos.length; i++)
                        listaCopos[i].setChecked(false);
                    txtQntd.setText(3500 + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(14);

                } else {
                    for(int i = 0; i < 14; i++)
                        listaCopos[i].setChecked(true);
                    qntd = 0;
                    qntd = qntd + 3750;
                    txtQntd.setText(qntd + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(15);
                }

            }


        });

        copo16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsuario();

                if(!copo16.isChecked()) {
                    for(int i = 15; i < listaCopos.length; i++)
                        listaCopos[i].setChecked(false);
                    txtQntd.setText(3750 + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(15);

                } else {
                    for(int i = 0; i < 15; i++)
                        listaCopos[i].setChecked(true);
                    qntd = 0;
                    qntd = qntd + 4000;
                    txtQntd.setText(qntd + " ml");

                    ref.child("users")
                            .child(currentId)
                            .child("inserção")
                            .child("bem-estar")
                            .child(String.valueOf(pYear))
                            .child(String.valueOf(pMonth))
                            .child(String.valueOf(pDay))
                            .child("Água")
                            .setValue(16);
                }

            }


        });

        btnSairProv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticacao = ConfigFirebase.getFirebaseAutenticacao();
                autenticacao.signOut();
                startActivity(new Intent(getActivity(), SliderActivity.class));
                getActivity().finish();


            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        declarar();

        if (copo8.isChecked())
            tbOutrosCopos.setVisibility(View.VISIBLE);

        if (getActivity() != null)
            getActivity().registerReceiver(dayChangedBroadcastReceiver, DayChangedBroadcastReceiver.getIntentFilter());

        if (copo1.isChecked()) {
            txtQntd.setText(250 + " ml");

            if (copo2.isChecked())
                txtQntd.setText(500 + " ml");
            else
                txtQntd.setText(250 + " ml");

            if (copo3.isChecked())
                txtQntd.setText(750 + " ml");
            else
                txtQntd.setText(500 + " ml");

            if (copo4.isChecked())
                txtQntd.setText(1000 + " ml");
            else
                txtQntd.setText(750 + " ml");

            if (copo5.isChecked())
                txtQntd.setText(1250 + " ml");
            else
                txtQntd.setText(1000 + " ml");

            if (copo6.isChecked())
                txtQntd.setText(1500 + " ml");
            else
                txtQntd.setText(1250 + " ml");

            if (copo7.isChecked())
                txtQntd.setText(1750 + " ml");
            else
                txtQntd.setText(1500 + " ml");

            if (copo8.isChecked())
                txtQntd.setText(2000 + " ml");
            else
                txtQntd.setText(1750 + " ml");

            if (copo9.isChecked())
                txtQntd.setText(2250 + " ml");
            else
                txtQntd.setText(2000 + " ml");

            if (copo10.isChecked())
                txtQntd.setText(2500 + " ml");
            else
                txtQntd.setText(2250 + " ml");

            if (copo11.isChecked())
                txtQntd.setText(2750 + " ml");
            else
                txtQntd.setText(2500 + " ml");

            if (copo12.isChecked())
                txtQntd.setText(3000 + " ml");
            else
                txtQntd.setText(2750 + " ml");

            if (copo13.isChecked())
                txtQntd.setText(3250 + " ml");
            else
                txtQntd.setText(3000 + " ml");

            if (copo14.isChecked())
                txtQntd.setText(3500 + " ml");
            else
                txtQntd.setText(3250 + " ml");

            if (copo15.isChecked())
                txtQntd.setText(3750 + " ml");
            else
                txtQntd.setText(3500 + " ml");

            if (copo16.isChecked())
                txtQntd.setText(4000 + " ml");
            else
                txtQntd.setText(3750 + " ml");

        } else
            txtQntd.setText(0 + " ml");

    }

    private final DayChangedBroadcastReceiver dayChangedBroadcastReceiver = new DayChangedBroadcastReceiver() {
        @Override
        public void onDayChanged() {

            declarar();

            final CheckBox[] listaCopos = {copo1, copo2, copo3, copo4, copo5, copo6, copo7, copo8, copo9, copo10, copo11,
                    copo12, copo13, copo14, copo15, copo16};

            for(CheckBox copo : listaCopos)
                copo.setChecked(false);

            tbOutrosCopos.setVisibility(View.GONE);

        }
    };

    public void recuperarUsuario() {

        FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

        if(auth.getCurrentUser() != null) {

            String email = auth.getCurrentUser().getEmail();
            assert email != null;
            currentId = Base64Custom.codificarBase64(email);
        }

    }

    public void declarar() {

        copo1 = getView().findViewById(R.id.copo1);
        copo2 = getView().findViewById(R.id.copo2);
        copo3 = getView().findViewById(R.id.copo3);
        copo4 = getView().findViewById(R.id.copo4);
        copo5 = getView().findViewById(R.id.copo5);
        copo6 = getView().findViewById(R.id.copo6);
        copo7 = getView().findViewById(R.id.copo7);
        copo8 = getView().findViewById(R.id.copo8);
        copo9 = getView().findViewById(R.id.copo9);
        copo10 = getView().findViewById(R.id.copo10);
        copo11 = getView().findViewById(R.id.copo11);
        copo12 = getView().findViewById(R.id.copo12);
        copo13 = getView().findViewById(R.id.copo13);
        copo14 = getView().findViewById(R.id.copo14);
        copo15 = getView().findViewById(R.id.copo15);
        copo16 = getView().findViewById(R.id.copo16);
        tbOutrosCopos = getView().findViewById(R.id.tbrOutrosCopos);

    }

}