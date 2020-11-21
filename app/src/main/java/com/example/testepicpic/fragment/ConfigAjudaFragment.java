package com.example.testepicpic.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testepicpic.R;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigAjudaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigAjudaFragment extends Fragment {
    private TextView txtDiabetes, txtComo, txtTipo1, txtTipo2, txtGesticional, txtPre, txtTrata,txtCura;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfigAjudaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigAjudaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigAjudaFragment newInstance(String param1, String param2) {
        ConfigAjudaFragment fragment = new ConfigAjudaFragment();
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
        View view = inflater.inflate(R.layout.fragment_config_ajuda, container, false);
        txtDiabetes = view.findViewById(R.id.textDiabetes);
        txtComo = view.findViewById(R.id.textComo);
        txtTipo1 = view.findViewById(R.id.textTipo1);
        txtTipo2 = view.findViewById(R.id.textTipo2);
        txtGesticional = view.findViewById(R.id.textGesticional);
        txtPre = view.findViewById(R.id.textPre);
        txtTrata = view.findViewById(R.id.textTrata);
        txtCura = view.findViewById(R.id.textCura);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            txtDiabetes.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            txtComo.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            txtTipo1.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            txtTipo2.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            txtGesticional.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            txtPre.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            txtTrata.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            txtCura.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        Toast.makeText(getActivity(), "AJUDA", Toast.LENGTH_LONG).show();

        return view;
    }
}