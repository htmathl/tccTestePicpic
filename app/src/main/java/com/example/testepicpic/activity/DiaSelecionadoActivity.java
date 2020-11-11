package com.example.testepicpic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.testepicpic.R;
import com.example.testepicpic.adapter.ExerciciosAdapter;
import com.example.testepicpic.adapter.GlicemiasAdapter;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.helper.Base64Custom;
import com.example.testepicpic.model.Exercicio;
import com.example.testepicpic.model.Glicemia;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.rockerhieu.rvadapter.states.StatesRecyclerViewAdapter;

import java.util.ArrayList;

public class DiaSelecionadoActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    public static final String PREFS_NAME = "shareData";
    private String strMonth;

    private static final String TAG = "swipe position";
    private float x1, y1, x2, y2;
    private static int minDistance = 150;
    private GestureDetector gestureDetector;

    private DatabaseReference ref, referenceGli, referenceEx;
    private String currentId;

    private ArrayList<Double>  nivelGli     = new ArrayList<>();
    private ArrayList<String>  categoriaGli = new ArrayList<>();
    private ArrayList<String>  localGli     = new ArrayList<>();
    private ArrayList<String>  ladoGli      = new ArrayList<>();
    private ArrayList<Integer> horaGli      = new ArrayList<>();

    private RecyclerView recyclerView, recyclerView2;
    private ArrayList<Glicemia> listaGlicemia = new ArrayList<>();
    private ArrayList<Exercicio> listaExercicio = new ArrayList<>();

    private int today, month, year;

    private GlicemiasAdapter adapter;
    private ExerciciosAdapter adapter2;
    private ValueEventListener valueEventListenerGli, valueEventListenerEx;

    private TextView txtNoDataGli, txtNoDataEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_selecionado);

        //declarar textviews
        txtNoDataGli = findViewById(R.id.txtNoDataGli);
        txtNoDataEx = findViewById(R.id.txtNoDataEx);

        //recuperar valores da data
        recuperarUsuario();

        //delize pra sair
        gestureDetector = new GestureDetector(DiaSelecionadoActivity.this, this);

        //limpar listas
        nivelGli.clear(); categoriaGli.clear(); ladoGli.clear();
        localGli.clear(); horaGli.clear();

        //definir referencias
        String data = String.valueOf(year) + String.valueOf(month) + String.valueOf(today);

        referenceGli = ref.child("inserção")
                .child(currentId)
                .child("glicemia")
                .child(data);

        referenceEx = ref.child("inserção")
                .child(currentId)
                .child("exercicio")
                .child(data);

        //recuperar todos os valores.
        recuperarAll();

        //definir data no txt principal
        TextView txtData = (TextView) findViewById(R.id.textView28);
        String strData = String.valueOf(year) + "\n" + String.valueOf(today) + " " + strMonth;
        txtData.setText(strData);

        recyclerView = findViewById(R.id.rcv_conteudoGli);
        recyclerView2 = findViewById(R.id.rcv_conteudoEx);

        //declarar adapter
        adapter = new GlicemiasAdapter(listaGlicemia, DiaSelecionadoActivity.this);
        adapter2 = new ExerciciosAdapter(listaExercicio, DiaSelecionadoActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DiaSelecionadoActivity.this);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(DiaSelecionadoActivity.this);

        //config recyclerGli
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize( true );
        recyclerView.setAdapter(adapter);

        //config recyclerEx
        recyclerView2.setLayoutManager( layoutManager2 );
        recyclerView2.setHasFixedSize( true );
        recyclerView2.setAdapter(adapter2);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //limpar recycler
        referenceGli.removeEventListener( valueEventListenerGli );
    }

    public void recuperarAll() {

        recuperarUsuario();

        //add glicemia
        valueEventListenerGli = referenceGli.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                    Glicemia glicemia = dataSnapshot.getValue(Glicemia.class);

                    listaGlicemia.add(glicemia);

                }

                if(adapter.getItemCount() == 0)
                    txtNoDataGli.setVisibility(View.VISIBLE);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //add exercicio
        valueEventListenerEx = referenceEx.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                    Exercicio exercicio = dataSnapshot.getValue(Exercicio.class);

                    listaExercicio.add(exercicio);

                }

                if(adapter2.getItemCount() == 0)
                    txtNoDataEx.setVisibility(View.VISIBLE);

                adapter2.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void recuperarUsuario() {

        ref = ConfigFirebase.getFirebase();

        FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

        if(auth.getCurrentUser() != null) {

            String email = auth.getCurrentUser().getEmail();
            assert email != null;
            currentId = Base64Custom.codificarBase64(email);
        }

        //recuperar data
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        today = preferences.getInt("today", 0);
        month = preferences.getInt("month", 0);
        year  = preferences.getInt("year", 0);

        switch (month) {

            case 1:
                strMonth = "jan";
                break;
            case 2:
                strMonth = "fev";
                break;
            case 3:
                strMonth = "mar";
                break;
            case 4:
                strMonth = "abr";
                break;
            case 5:
                strMonth = "mai";
                break;
            case 6:
                strMonth = "jun";
                break;
            case 7:
                strMonth = "jul";
                break;
            case 8:
                strMonth = "ago";
                break;
            case 9:
                strMonth = "set";
                break;
            case 10:
                strMonth = "out";
                break;
            case 11:
                strMonth = "nov";
                break;
            case 12:
                strMonth = "dez";
                break;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gestureDetector.onTouchEvent(event);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                float valueY = y2 - y1;

                if(Math.abs(valueY) > minDistance) {
                    if (y2 > y1) {
                        finish();
                    }
                }
        }

        return super.onTouchEvent(event);

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}