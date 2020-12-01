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
import android.widget.Button;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config_tratamento, container, false);

        recuperarUsurario();

        epeso = view.findViewById(R.id.edtPesoo);
        ealtura = view.findViewById(R.id.edtAlt);
        tipo = view.findViewById(R.id.spntipo);
        ano = view.findViewById(R.id.edtano);
        hiper = view.findViewById(R.id.eftHiper);
        hipo = view.findViewById(R.id.edtHipo);
        normal = view.findViewById(R.id.edtNormal);
        tipoi = view.findViewById(R.id.edtTipo);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.tipos_diabetes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo.setAdapter(adapter);
        tipo.setOnItemSelectedListener(this);

        ta = view.findViewById(R.id.spntra);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),R.array.tratamento_insulina, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ta.setAdapter(adapter1);
        ta.setOnItemSelectedListener(this);

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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference1 = ref.child("ajustes")
                .child(currentId);

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if( snapshot.exists() ) {

                    Ajustes ajustes = snapshot.getValue( Ajustes.class );

                    assert ajustes != null;
                    ano.setText( String.valueOf( ajustes.getAno() ) );

                    switch ( ajustes.getTipo() ) {
                        case "Seringa":
                            ta.setSelection(0);
                            break;
                        case "Caneta":
                            ta.setSelection(1);
                            break;
                        case "Sistema de infusão continua":
                            ta.setSelection(2);
                            break;
                    }

                    tipoi.setText( ajustes.getTipo() );
                    hiper.setText( String.valueOf( ajustes.getHiper() ) );
                    normal.setText( String.valueOf( ajustes.getNormal() ) );
                    hipo.setText( String.valueOf( ajustes.getHipo() ) );

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button btnSalvarConfig = view.findViewById(R.id.btnSalvarConfig);

        btnSalvarConfig.setOnClickListener(v -> {

            String tratamento = ta.getSelectedItem().toString();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Deseja salvar as novas informações?");
            builder.setPositiveButton("Sim", (dialog, which) -> {

                try {

                    if( ano.getText() != null && hiper.getText() != null && hipo.getText() != null && normal.getText() != null && tipoi.getText() != null && ealtura.getText() != null && epeso.getText() != null ) {

                        Ajustes ajustes = new Ajustes();
                        ajustes.setAno( Integer.parseInt(ano.getText().toString()));
                        ajustes.setHiper(Double.parseDouble(hiper.getText().toString()));
                        ajustes.setHipo(Double.parseDouble(hipo.getText().toString()));
                        ajustes.setNormal(Double.parseDouble(normal.getText().toString()));
                        ajustes.setTipo(tipoi.getText().toString());
                        ajustes.setTratamento(tratamento);
                        ajustes.salvar();

                        ref.child("users")
                                .child(currentId)
                                .child("tipoDiabetes").setValue(tipo.getSelectedItem().toString());

                        ref.child("users")
                                .child(currentId)
                                .child("altura").setValue(Double.parseDouble( ealtura.getText().toString() ));

                        ref.child("users")
                                .child(currentId)
                                .child("peso").setValue(Double.parseDouble( epeso.getText().toString() ));

                        Toast.makeText(getActivity(), "Pronto :)", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), "" + e, Toast.LENGTH_LONG).show();
                }


            });

            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(getActivity(), "Deu errado :(", Toast.LENGTH_SHORT).show();

                }
            });

            builder.create();
            builder.show();

        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}