package com.example.testepicpic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.testepicpic.R;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.fragment.ConfigAjudaFragment;
import com.example.testepicpic.fragment.ConfigNotificacaoFragment;
import com.example.testepicpic.fragment.ConfigPerfilFragment;
import com.example.testepicpic.fragment.ConfigTratamentoFragment;
import com.example.testepicpic.helper.Base64Custom;
import com.example.testepicpic.model.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class AjustesActivity extends AppCompatActivity{

    private FirebaseAuth autenticacao;

    private int position;

    private DatabaseReference ref;
    private String currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        recuperarUser();

        Button btnConfigPerfil = findViewById(R.id.btnConfigPerfil);
        Button btnConfigTratamento = findViewById(R.id.btnConfigTratamento);
        Button btnConfigNotificacaos = findViewById(R.id.btnConfigNotificacaos);
        Button btnConfigAjuda = findViewById(R.id.btnConfigAjuda);
        Button btnConfigSair = findViewById(R.id.btnConfigSair);
        ImageButton miaumiau = findViewById(R.id.miaumiua);
        CircleImageView imageView = findViewById(R.id.profile_image);

        Intent intent = new Intent(AjustesActivity.this, TransPerfilActivity.class);

        StorageReference storage = ConfigFirebase.getFirebaseStorage();
        final StorageReference imagemRef = storage
                .child("imagens")
                .child("perfil")
                .child(currentId + ".jpeg");

        imagemRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with( AjustesActivity.this ).load( uri ).into( imageView );

            }
        });

        DatabaseReference reference = ref.child("users")
                .child(currentId);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Usuario usuario = snapshot.getValue( Usuario.class );

                btnConfigPerfil.setText( usuario.getNome() );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        miaumiau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        btnConfigPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 0;
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });
        btnConfigTratamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 1;
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });
        btnConfigNotificacaos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 2;
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        btnConfigAjuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 3;
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });

        btnConfigSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticacao = ConfigFirebase.getFirebaseAutenticacao();
                autenticacao.signOut();
                startActivity(new Intent(AjustesActivity.this, SliderActivity.class));
                finish();
                LocalBroadcastManager.getInstance(AjustesActivity.this).sendBroadcast(new Intent("fecharTelaMain"));

            }
        });

    }
    public void recuperarUser() {

        ref = ConfigFirebase.getFirebase();

        FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

        String email = auth.getCurrentUser().getEmail();

        currentId = Base64Custom.codificarBase64(email);

    }

}