package com.example.testepicpic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testepicpic.R;
import com.example.testepicpic.ReminderBroadcast;
import com.example.testepicpic.fragment.AddAlimentacaoFragment;
import com.example.testepicpic.fragment.AddBemEstarFragment;
import com.example.testepicpic.fragment.AddExercicioFragment;
import com.example.testepicpic.fragment.AddInsulinaFragment;
import com.example.testepicpic.fragment.CalendarFragment;
import com.example.testepicpic.fragment.OverviewFragment;
import com.example.testepicpic.fragment.RelatorioFragment;
import com.example.testepicpic.helper.Base64Custom;
import com.github.mikephil.charting.charts.LineChart;
import com.example.testepicpic.config.ConfigFirebase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth autenticacao;

    private ImageButton fabButton, buttonPerfil;
    private OvershootInterpolator interpolator = new OvershootInterpolator();
    private boolean menuAberto = true;
    private int position;

    private BottomNavigationView bottomNavigationView;
    private static final String TAG = "MainActivity";
    private OverviewFragment overviewFragment = new OverviewFragment();
    private CalendarFragment calendarFragment = new CalendarFragment();
    private RelatorioFragment relatorioFragment = new RelatorioFragment();

    private ImageButton btnHumor,btnAlimento,btnInsulina,btnExercicio,btnGlicemia;

    private AddBemEstarFragment addBemEstarFragment = new AddBemEstarFragment();
    private AddAlimentacaoFragment addAlimentacaoFragment = new AddAlimentacaoFragment();
    private AddExercicioFragment addExercicioFragment = new AddExercicioFragment();
    //private AddGlicemiaFragment addGlicemiaFragment = new AddGlicemiaFragment();
    private AddInsulinaFragment addInsulinaFragment = new AddInsulinaFragment();

    private LineChart graficoGlicemia;

    private DatabaseReference ref;
    private String currentUser;
    private ArrayList<Integer> horas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miau();

        graficoGlicemia = (LineChart) findViewById(R.id.grafico_glicemia);


        fabButton = findViewById(R.id.fab_button);
        buttonPerfil = findViewById(R.id.btnPerfil);
        final ConstraintLayout c = findViewById(R.id.constrait);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.item_overview);

        btnHumor = findViewById(R.id.btnHumor);
        btnAlimento = findViewById(R.id.btnAlimento);
        btnExercicio = findViewById(R.id.btnExercicio);
        btnGlicemia = findViewById(R.id.btnGlicemia);
        btnInsulina = findViewById(R.id.bntInsulina);

        /*notificação
        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.DAY_OF_WEEK,2);

        int now = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        if(calendario.get(Calendar.DAY_OF_WEEK) == now){
            enviarNotificacao();
        }

        //setAlarm(this, calendario);


       NotifyMe.Builder notifyme = new NotifyMe.Builder(MainActivity.this);
        Intent intent2 = new Intent (this, MainActivity.class);

        notifyme.title("Pelo amor de deus")
                .content("n acaba nunca saporra")
                .color(107, 82, 175, 255)
                .large_icon(R.drawable.ic_stress)
                .time(calendario)
                .build();*/



        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuAberto) {
                    fabButton.animate().setInterpolator(interpolator).rotation(45f).setDuration(500).start();
                    Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.to_down);

                    c.startAnimation(animation);

                    menuAberto =! menuAberto;
                    c.setVisibility(View.VISIBLE);


                } else {
                    fabButton.animate().setInterpolator(interpolator).rotation(0f).setDuration(500).start();
                    Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.from_top);

                    c.startAnimation(animation);

                    menuAberto =! menuAberto;
                    c.setVisibility(View.GONE);

                }
            }
        });

        buttonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AjustesActivity.class));
            }
        });


        btnHumor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = 0;
                Intent intent = new Intent(MainActivity.this, AddInfosActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });

        btnAlimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = 1;
                Intent intent = new Intent(MainActivity.this, AddInfosActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });

        btnExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = 2;
                Intent intent = new Intent(MainActivity.this, AddInfosActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });

        btnInsulina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = 3;
                Intent intent = new Intent(MainActivity.this, AddInfosActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        btnGlicemia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 4;
                Intent intent = new Intent(MainActivity.this, AddInfosActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });


    }

    public void miau() {

        recuperarUser();

        DatabaseReference reference = ref.child("lembretes")
                .child(currentUser);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                    DatabaseReference reference = ref.child("lembretes")
                            .child(currentUser)
                            .child(dataSnapshot.getKey())
                            .child("dias");

                    String tipo = dataSnapshot.getKey();

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot1) {

                            for( DataSnapshot dataSnapshot1 : snapshot1.getChildren() ) {

                                DatabaseReference reference = ref.child("lembretes")
                                        .child(currentUser)
                                        .child(dataSnapshot.getKey())
                                        .child("dias")
                                        .child(dataSnapshot1.getKey());

                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot2) {

                                        for ( DataSnapshot dataSnapshot2 : snapshot2.getChildren() ) {

                                            horas.add(Integer.parseInt(dataSnapshot2.getValue().toString()));

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void recuperarUser() {

        ref = ConfigFirebase.getFirebase();

        FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

        String email = auth.getCurrentUser().getEmail();

        currentUser = Base64Custom.codificarBase64(email);

    }

    public void sair(View view){
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.signOut();
        startActivity(new Intent(this, SliderActivity.class));
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        TextView txtOndaPrincipal = (TextView) findViewById(R.id.txt_onda_principal);
        FrameLayout ondaPrincipal = (FrameLayout) findViewById(R.id.onda_principal);
        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        ImageButton fabButton = (ImageButton) findViewById(R.id.fab_button);

        switch(item.getItemId()) {
            case R.id.item_overview:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.substituicao, overviewFragment).commit();
                txtOndaPrincipal.setText("Visão geral");
                return true;

            case R.id.item_calendario:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.substituicao, calendarFragment).commit();
                txtOndaPrincipal.setText("Calendário");
                return true;

            case R.id.item_reltorio:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.substituicao, relatorioFragment).commit();
                txtOndaPrincipal.setText("Relatório");
                return true;
        }

        return false;
    }
    private void createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            NotificationChannel channel = new NotificationChannel("fcm_default_channel", "canal", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void setAlarm(Context context, Calendar targetCall){
        Intent intentN = new Intent(context, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,intentN,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,targetCall.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void enviarNotificacao(){

        createNotificationChannel();

        Uri uriSom = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Intent intent2 = new Intent (this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent2, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "fcm_default_channel")
                .setContentTitle("miau?")
                .setContentText("miau!")
                .setSmallIcon(R.drawable.ic_agua)
                .setSound(uriSom)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(0, notification.build());


    }
}