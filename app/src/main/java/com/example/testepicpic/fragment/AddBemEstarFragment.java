package com.example.testepicpic.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.testepicpic.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddBemEstarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddBemEstarFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private RadioButton brabo, normal, triste, feliz;

    private int indice;

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

        brabo.setOnCheckedChangeListener(this);
        triste.setOnCheckedChangeListener(this);
        feliz.setOnCheckedChangeListener(this);
        normal.setOnCheckedChangeListener(this);



        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            if(buttonView.getId() == R.id.btnAddHumorBrabo){
                triste.setChecked(false);
                feliz.setChecked(false);
                normal.setChecked(false);
            }
            if(buttonView.getId() == R.id.btnAddHumorSad){
                brabo.setChecked(false);
                feliz.setChecked(false);
                normal.setChecked(false);
            }
            if(buttonView.getId() == R.id.btnAddHumorBem){
                triste.setChecked(false);
                brabo.setChecked(false);
                normal.setChecked(false);
            }
            if(buttonView.getId() == R.id.btnAddHumorNormal){
                triste.setChecked(false);
                feliz.setChecked(false);
                brabo.setChecked(false);
            }
        }
    }
}