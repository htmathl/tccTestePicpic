package com.example.testepicpic.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.testepicpic.R;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigPerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigPerfilFragment extends Fragment {

    private Button btnSalvarPerfil;
    private TextView txtAlterarFoto;
    private EditText edtNomeUser;
    private CircleImageView circleImageViewPerfil;
    private StorageReference storageReference;
    private String IdentificadorUsuario;

    private ImageButton imgbtnCamera, imgbtnGaleria;

   // private StorageReference storageReference;
   // private String identificadorUsuario;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfigPerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigPerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigPerfilFragment newInstance(String param1, String param2) {
        ConfigPerfilFragment fragment = new ConfigPerfilFragment();
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
        View view = inflater.inflate(R.layout.fragment_config_perfil,container,false);

        /*btnSalvarPerfil= view.findViewById(R.id.btnSalvarPerfil);
        txtAlterarFoto = view.findViewById(R.id.txtAlterarFoto);
        edtNomeUser= view.findViewById(R.id.edtNomeUser);

        storageReference= ConfigFirebase.getFirebaseStorage();*/

        imgbtnCamera = view.findViewById(R.id.imgBtnCamera);
        imgbtnGaleria = view.findViewById(R.id.imgBtnGaleria);

        imgbtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        imgbtnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }
}