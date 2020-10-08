package com.example.testepicpic.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.testepicpic.R;
import com.example.testepicpic.activity.*;
import com.example.testepicpic.config.*;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends Fragment {

    private Button btnSairProv, copo1, copo2, copo3, copo4, copo5, copo6, copo7, copo8;
    private boolean copo = false;
    private FirebaseAuth autenticacao;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        btnSairProv = view.findViewById(R.id.btnExames);
        copo1 = view.findViewById(R.id.copo1);
        copo2 = view.findViewById(R.id.copo2);
        copo3 = view.findViewById(R.id.copo3);
        copo4 = view.findViewById(R.id.copo4);
        copo5 = view.findViewById(R.id.copo5);
        copo6 = view.findViewById(R.id.copo6);
        copo7 = view.findViewById(R.id.copo7);
        copo8 = view.findViewById(R.id.copo8);

        copo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(copo=false) {
                    copo1.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo2.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo3.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo4.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo5.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo6.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo7.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo8.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo = true;
                }
                if (copo=true){
                    copo1.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo2.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo3.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo4.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo5.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo6.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo7.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo8.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo = false;

                }

            }
        });

        copo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    copo1.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo2.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo3.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo4.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo5.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo6.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo7.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo8.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                }



        });

        copo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    copo1.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo2.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo3.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo4.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo5.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo6.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo7.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo8.setBackground(getResources().getDrawable(R.drawable.ic_agua));


            }
        });

        copo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    copo1.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo2.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo3.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo4.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo5.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo6.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo7.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo8.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                }


        });

        copo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    copo1.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo2.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo3.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo4.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo5.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo6.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo7.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo8.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                }


        });

        copo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    copo1.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo2.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo3.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo4.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo5.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo6.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo7.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                    copo8.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                }

        });

        copo7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    copo1.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo2.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo3.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo4.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo5.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo6.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo7.setBackground(getResources().getDrawable(R.drawable.ic_agua2));

                    copo8.setBackground(getResources().getDrawable(R.drawable.ic_agua));
                }

        });

        copo8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    copo1.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo2.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo3.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo4.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo5.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo6.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo7.setBackground(getResources().getDrawable(R.drawable.ic_agua2));
                    copo8.setBackground(getResources().getDrawable(R.drawable.ic_agua2));

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
}