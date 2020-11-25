package com.example.testepicpic.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.jar.Pack200;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigPerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigPerfilFragment extends Fragment {

    private Button btnSalvarPerfil;
    private TextView txtAlterarFoto;
    private TextInputEditText edtNomeUser;
    private CircleImageView circleImageViewPerfil;
    private StorageReference storageReference;
    private String currentId, nomeVelho, nomeNovo;
    private String IdentificadorUsuario;
    private DatabaseReference ref;


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
        edtNomeUser = view.findViewById(R.id.edtNomeUser);
        btnSalvarPerfil =  view.findViewById(R.id.btnSalvarPerfil);


        FirebaseUser usuario = getUsuarioAtual();
        Uri url = usuario.getPhotoUrl();

        if(url !=null){
            Glide.with(getActivity()).load(url).into(circleImageViewPerfil);
        }else{
            circleImageViewPerfil.setImageResource(R.drawable.perfil);
        }

        DatabaseReference reference = ref.child("users")
                .child(currentId);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Usuario usuario = snapshot.getValue( Usuario.class );
                edtNomeUser.setText(usuario.getNome());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        imgbtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getActivity().getPackageManager())!=null){
                startActivityForResult(intent, SELECAO_CAMERA);}
            }
        });

        imgbtnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if(intent.resolveActivity(getActivity().getPackageManager())!=null){
                    startActivityForResult(intent,SELECAO_GALERIA);
                }
            }
        });

        btnSalvarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeNovo = edtNomeUser.getText().toString().trim();
                reference.child("nome").setValue(nomeNovo);
                Toast.makeText(getActivity(),"Nome alterado com sucesso", Toast.LENGTH_LONG).show();


            }
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
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "deu bosta", Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(getActivity(),"não deu bosta", Toast.LENGTH_LONG).show();
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

}