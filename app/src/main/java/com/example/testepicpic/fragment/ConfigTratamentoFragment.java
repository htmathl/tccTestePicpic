package com.example.testepicpic.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.testepicpic.R;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.helper.Base64Custom;
import com.example.testepicpic.model.Ajustes;
import com.example.testepicpic.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigTratamentoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigTratamentoFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private String currentId;
    private DatabaseReference ref;
    private String peso, altura, tipoDiabetes;
    private Spinner tipo, ta;
    private EditText epeso, ealtura, ano, hiper, normal, hipo, tipoi;


    public void recuperarUsurario() {
        FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

        if(auth.getCurrentUser() != null) {

            String email = auth.getCurrentUser().getEmail();
            assert email != null;
            currentId = Base64Custom.codificarBase64(email);

        }

        ref = ConfigFirebase.getFirebase();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recuperarUsurario();

        ta = getView().findViewById(R.id.spntra);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.tratamento_insulina, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ta.setAdapter(adapter);
        ta.setOnItemSelectedListener(this);

        epeso = getView().findViewById(R.id.edtPesoo);
        ealtura = getView().findViewById(R.id.edtAlt);
        tipo = getView().findViewById(R.id.spntipo);
        ano = getView().findViewById(R.id.edtano);
        hiper = getView().findViewById(R.id.eftHiper);
        hipo = getView().findViewById(R.id.edtHipo);
        normal = getView().findViewById(R.id.edtNormal);
        tipoi = getView().findViewById(R.id.edtTipo);


        int anoi = Integer.parseInt(ano.getText().toString());

        double hiperd, hipod, normald;

        String tratamento = ta.getSelectedItem().toString();

        hiperd = Double.parseDouble(hiper.getText().toString());
        hipod = Double.parseDouble(hipo.getText().toString());
        normald = Double.parseDouble(normal.getText().toString());




        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Deseja salvar as novas informações?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Ajustes ajustes = new Ajustes();
                ajustes.setAno(anoi);
                ajustes.setHiper(hiperd);
                ajustes.setHipo(hipod);
                ajustes.setNormal(normald);
                ajustes.setTipo(tipoi.getText().toString());
                ajustes.setTratamento(tratamento);
                ajustes.salvar();

                DatabaseReference reference = ref.child("users")
                        .child(currentId);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Usuario usuario = snapshot.getValue( Usuario.class );

                       usuario.setAltura(Integer.parseInt(ealtura.getText().toString()));
                        usuario.setPeso(Integer.parseInt(epeso.getText().toString()));
                        usuario.setTipoDiabetes(tipo.getSelectedItem().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });

    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfigTratamentoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigTratamentoFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static ConfigTratamentoFragment newInstance(String param1, String param2) {
        ConfigTratamentoFragment fragment = new ConfigTratamentoFragment();
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

    public void recuperarAll() {

        recuperarUsurario();

        DatabaseReference reference = ref.child("users")
                .child(currentId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Usuario usuario = snapshot.getValue(Usuario.class);

                assert usuario != null;

                peso = String.valueOf(usuario.getPeso());
                altura = String.valueOf(usuario.getAltura());
                tipoDiabetes = usuario.getTipoDiabetes();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config_tratamento, container, false);

        recuperarAll();

        epeso = view.findViewById(R.id.edtPesoo);
        ealtura = view.findViewById(R.id.edtAlt);
        tipo = view.findViewById(R.id.spntipo);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.tipos_diabetes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo.setAdapter(adapter);
        tipo.setOnItemSelectedListener(this);

        if (peso !=null){
            epeso.setText(peso);
            ealtura.setText(altura);
            switch (tipoDiabetes){
                case "Pré-diabetes":
                    tipo.setSelection(0);
                break;
                case "Tipo 1":
                    tipo.setSelection(1);
                    break;
                case "Tipo 2":
                    tipo.setSelection(2);
                    break;
                case "Gestacional":
                    tipo.setSelection(3);
                    break;

            }
        }



        return view;


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}