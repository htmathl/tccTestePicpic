package com.example.testepicpic.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.testepicpic.R;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.helper.Base64Custom;
import com.example.testepicpic.helper.Permissao;
import com.example.testepicpic.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigPerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigPerfilFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    private CircleImageView circleImageViewPerfil;
    private StorageReference storageReference;
    private String currentId, nome, idade, genero;
    private String IdentificadorUsuario;
    private DatabaseReference ref;
    private TextInputEditText edtNome, edtIdade;
    private Spinner spGenero;

    private Button btnSalvar;

    private ImageButton imgbtnCamera, imgbtnGaleria;

    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA
    };
    private static int SELECAO_CAMERA = 100;
    private static int SELECAO_GALERIA = 200;



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

        storageReference= ConfigFirebase.getFirebaseStorage();
        recuperarUsurario();

        Permissao.validarpermisoes(permissoesNecessarias, getActivity(),1);

        imgbtnCamera = view.findViewById(R.id.imgBtnCamera);
        imgbtnGaleria = view.findViewById(R.id.imgBtnGaleria);
        circleImageViewPerfil = view.findViewById(R.id.circleImageViewFotoPerfil);
        edtNome  = view.findViewById(R.id.edtNome);
        edtIdade = view.findViewById(R.id.edtIdade);
        spGenero = view.findViewById(R.id.spinnergenero2);
        btnSalvar = view.findViewById(R.id.btnSalvar);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.genero, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenero.setAdapter(adapter);
        spGenero.setOnItemSelectedListener(this);

        DatabaseReference reference = ref.child("users")
                .child(currentId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Usuario usuario = snapshot.getValue( Usuario.class );

                assert usuario != null;
                nome = usuario.getNome();
                idade = String.valueOf( usuario.getIdade() );
                genero = usuario.getGenero();

                edtNome.setText( nome );
                edtIdade.setText( idade );

                switch ( genero ) {
                    case "Feminino":
                        spGenero.setSelection(0);
                        break;
                    case "Masculino":
                        spGenero.setSelection(1);
                        break;
                    case "Outro":
                        spGenero.setSelection(2);
                        break;
                    case "Não informar":
                        spGenero.setSelection(3);
                        break;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseUser usuario = getUsuarioAtual();
        Uri url = usuario.getPhotoUrl();

        if(url !=null){
            Glide.with(getActivity()).load(url).into(circleImageViewPerfil);
        }else{
            circleImageViewPerfil.setImageResource(R.drawable.perfil);
        }

        imgbtnCamera.setOnClickListener(v -> {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getActivity().getPackageManager())!=null){
            startActivityForResult(intent, SELECAO_CAMERA);}
        });

        imgbtnGaleria.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if(intent.resolveActivity(getActivity().getPackageManager())!=null){
                startActivityForResult(intent,SELECAO_GALERIA);
            }
        });

        btnSalvar.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );

            builder.setTitle("Deseja salvar as alterações?");

            builder.setPositiveButton("Sim", (dialog, which) -> {

                try {

                    ref.child("users")
                            .child(currentId)
                            .child("nome")
                            .setValue( edtNome.getText().toString() );

                    ref.child("users")
                            .child(currentId)
                            .child("idade")
                            .setValue( Integer.parseInt( edtIdade.getText().toString() ) );

                    ref.child("users")
                            .child(currentId)
                            .child("genero")
                            .setValue( spGenero.getSelectedItem().toString() );

                    Toast.makeText(getActivity(), "Informações salvas com sucesso :)", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }


            });

            builder.setNegativeButton("Não", (dialog, which) -> {

            });

            builder.create();
            builder.show();

        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            Bitmap imagem = null;
            try{
                switch (requestCode){
                    case 100:
                        imagem = (Bitmap) data.getExtras().get("data");
                        break;
                    case 200:
                        Uri localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),localImagemSelecionada);
                        break;
                }
                if(imagem != null){
                    circleImageViewPerfil.setImageBitmap(imagem);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    final StorageReference imagemRef = storageReference
                            .child("imagens")
                            .child("perfil")
                            .child(currentId + ".jpeg");

                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
                    uploadTask.addOnFailureListener(e -> Toast.makeText(getActivity(), "Ops! ocorreu um erro, "  + e , Toast.LENGTH_LONG).show()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(getActivity(),"Upload realizado com sucesso", Toast.LENGTH_LONG).show();
                            imagemRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                   Uri url =  task.getResult();
                                   atualizaFotoUsuario(url);
                                }
                            });

                        }
                    });
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        for(int permissaoResultado : grantResults){
            if(permissaoResultado== PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

    public void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o aplicativo é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void recuperarUsurario() {
        ref = ConfigFirebase.getFirebase();

        FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

        if(auth.getCurrentUser() != null) {

            String email = auth.getCurrentUser().getEmail();
            assert email != null;
            currentId = Base64Custom.codificarBase64(email);
        }
    }

    public static FirebaseUser getUsuarioAtual(){
        FirebaseAuth usuario = ConfigFirebase.getFirebaseAutenticacao();
        return usuario.getCurrentUser();
    }

    public void atualizaFotoUsuario(Uri url){
        atualizarFotoUsuario(url);

    }

    public static boolean atualizarFotoUsuario(Uri url){

        try{
            FirebaseUser user = getUsuarioAtual();

            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setPhotoUri(url).build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(!task.isSuccessful()){
                        Log.d("Perfil", "Erro ao atualizar a foto");
                    }
                }
            });
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}