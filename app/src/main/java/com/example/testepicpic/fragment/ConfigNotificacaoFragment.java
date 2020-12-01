package com.example.testepicpic.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.testepicpic.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigNotificacaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigNotificacaoFragment extends Fragment {
    private Switch switchagua, switchgli, switchinsulina, switchMedica;
    private CheckBox cdom2, cseg2, cter2,cqua2,cqui2,csex2,csab2;
    private Button btnAddHora, btnAddHora3, btnAddHora4;
    private TableRow linha1, linha2,linha5, linha6,linha7, linha8;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfigNotificacaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigNotificacaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigNotificacaoFragment newInstance(String param1, String param2) {
        ConfigNotificacaoFragment fragment = new ConfigNotificacaoFragment();
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
        View view = inflater.inflate(R.layout.fragment_config_notificacao, container, false);

        switchagua = view.findViewById(R.id.switch22);
        switchgli = view.findViewById(R.id.switch2);
        switchinsulina = view.findViewById(R.id.switch3);
        switchMedica = view.findViewById(R.id.switch4);

        cdom2 = view.findViewById(R.id.cbDom2);
        cseg2 = view.findViewById(R.id.cbSeg2);
        cter2 = view.findViewById(R.id.cbTer2);
        cqua2 = view.findViewById(R.id.cbQua2);
        cqui2 = view.findViewById(R.id.cbQui2);
        csex2 = view.findViewById(R.id.cbSex2);
        csab2 = view.findViewById(R.id.cbSab2);

        btnAddHora = view.findViewById(R.id.btnAddHora);
        btnAddHora3 = view.findViewById(R.id.btnAddHora3);
        btnAddHora4 = view.findViewById(R.id.btnAddHora4);

        linha1 = view.findViewById(R.id.linha1);
        linha2 = view.findViewById(R.id.linha2);
        linha5 = view.findViewById(R.id.linha5);
        linha6 = view.findViewById(R.id.linha6);
        linha7 = view.findViewById(R.id.linha7);
        linha8 = view.findViewById(R.id.linha8);

        switchagua.setChecked(true);

        cdom2.setChecked(true);
        cseg2.setChecked(true);
        cter2.setChecked(true);
        cqua2.setChecked(true);
        cqui2.setChecked(true);
        csex2.setChecked(true);
        csab2.setChecked(true);

        /*if (switchgli.isChecked() == false){
            btnAddHora.setVisibility(View.GONE);
            linha2.setVisibility(View.GONE);
            linha1.setVisibility(View.GONE);
        }
        else {

            btnAddHora.setVisibility(View.VISIBLE);
            linha2.setVisibility(View.VISIBLE);
            linha1.setVisibility(View.VISIBLE);

        }

        if (switchinsulina.isChecked() == false){
            btnAddHora3.setVisibility(View.GONE);
            linha5.setVisibility(View.GONE);
            linha6.setVisibility(View.GONE);
        }
        else {

            btnAddHora3.setVisibility(View.VISIBLE);
            linha5.setVisibility(View.VISIBLE);
            linha6.setVisibility(View.VISIBLE);

        }
        if (switchMedica.isChecked() == false){
            btnAddHora4.setVisibility(View.GONE);
            linha7.setVisibility(View.GONE);
            linha8.setVisibility(View.GONE);
        }
        else {

            btnAddHora4.setVisibility(View.VISIBLE);
            linha7.setVisibility(View.VISIBLE);
            linha8.setVisibility(View.VISIBLE);

        }*/

        return view;
    }
}