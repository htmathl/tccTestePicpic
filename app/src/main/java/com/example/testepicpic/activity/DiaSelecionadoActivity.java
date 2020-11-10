package com.example.testepicpic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.example.testepicpic.R;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.helper.Base64Custom;
import com.example.testepicpic.model.Alimentacao;
import com.example.testepicpic.model.BemEstar;
import com.example.testepicpic.model.Exercicio;
import com.example.testepicpic.model.Glicemia;
import com.example.testepicpic.model.Insulina;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;

public class DiaSelecionadoActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    public static final String PREFS_NAME = "shareData";
    private String strMonth;

    private static final String TAG = "swipe position";
    private float x1, y1, x2, y2;
    private static int minDistance = 150;
    private GestureDetector gestureDetector;

    private DatabaseReference ref;
    private String currentId;

    private ArrayList<Double>  nivelGli     = new ArrayList<>();
    private ArrayList<String>  categoriaGli = new ArrayList<>();
    private ArrayList<String>  localGli     = new ArrayList<>();
    private ArrayList<String>  ladoGli      = new ArrayList<>();
    private ArrayList<Integer> horaGli      = new ArrayList<>();

    private int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_selecionado);

        nivelGli.clear(); categoriaGli.clear(); ladoGli.clear();
        localGli.clear(); horaGli.clear();

        ref = ConfigFirebase.getFirebase();
        recuperarUsuario();

        gestureDetector = new GestureDetector(DiaSelecionadoActivity.this, this);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int today = preferences.getInt("today", 0);
        int month = preferences.getInt("month", 0);
        int year  = preferences.getInt("year", 0);

        TextView txtData = (TextView) findViewById(R.id.textView28);

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

        String strData = String.valueOf(year) + "\n" + String.valueOf(today) + " " + strMonth;

        txtData.setText(strData);

        //add glicemia
        DatabaseReference referenceGli = ref.child("inserção")
                .child(currentId)
                .child("glicemia");

        referenceGli.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                    DatabaseReference reference1 = ref.child("inserção")
                            .child(currentId)
                            .child("glicemia")
                            .child(dataSnapshot.getKey());

                    reference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for( DataSnapshot dataSnapshot1 : snapshot.getChildren() ) {

                                Glicemia glicemia = dataSnapshot1.getValue(Glicemia.class);

                                int dia = glicemia.getDia();
                                int mes = glicemia.getMes();
                                int ano = glicemia.getAno();

                                if(dia == today && mes == month && ano == year) {

                                    nivelGli.add(glicemia.getNivel());
                                    categoriaGli.add(glicemia.getCategoria());
                                    localGli.add(glicemia.getLocal());
                                    ladoGli.add(glicemia.getLado());
                                    horaGli.add(glicemia.getHora());

                                    //exclusao
                                    txtData.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            dataSnapshot1.getRef().removeValue();

                                        }
                                    });

                                    //update


                                    i++;

                                }

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

        //add insulina
        DatabaseReference referenceInsu = ref.child("inserção")
                .child(currentId)
                .child("insulina");

        referenceInsu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                    DatabaseReference reference1 = ref.child("inserção")
                            .child(currentId)
                            .child("insulina")
                            .child(dataSnapshot.getKey());

                    reference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            int[] dias  =  new int[(int) snapshot.getChildrenCount()];
                            int[] meses =  new int[(int) snapshot.getChildrenCount()];
                            int[] anos  =  new int[(int) snapshot.getChildrenCount()];


                            for( DataSnapshot dataSnapshot1 : snapshot.getChildren() ) {

                                Insulina insulina = dataSnapshot1.getValue(Insulina.class);


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

        //add exercicio
        DatabaseReference referenceEx = ref.child("inserção")
                .child(currentId)
                .child("exercicio");

        referenceEx.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                    DatabaseReference reference1 = ref.child("inserção")
                            .child(currentId)
                            .child("exercicio")
                            .child(dataSnapshot.getKey());

                    reference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            int[] dias  =  new int[(int) snapshot.getChildrenCount()];
                            int[] meses =  new int[(int) snapshot.getChildrenCount()];
                            int[] anos  =  new int[(int) snapshot.getChildrenCount()];

                            for( DataSnapshot dataSnapshot1 : snapshot.getChildren() ) {

                                Exercicio exercicio = dataSnapshot1.getValue(Exercicio.class);



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

        //add alimentacao
        DatabaseReference referenceAli = ref.child("inserção")
                .child(currentId)
                .child("alimentação");

        referenceAli.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                    DatabaseReference reference1 = ref.child("inserção")
                            .child(currentId)
                            .child("alimentação")
                            .child(dataSnapshot.getKey());

                    reference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            int[] dias  =  new int[(int) snapshot.getChildrenCount()];
                            int[] meses =  new int[(int) snapshot.getChildrenCount()];
                            int[] anos  =  new int[(int) snapshot.getChildrenCount()];

                            for( DataSnapshot dataSnapshot1 : snapshot.getChildren() ) {

                                Alimentacao alimentacao = dataSnapshot1.getValue(Alimentacao.class);


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

        //add bem-estar
        DatabaseReference referenceBem = ref.child("inserção")
                .child(currentId)
                .child("bem-estar");

        referenceBem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                    DatabaseReference reference1 = ref.child("inserção")
                            .child(currentId)
                            .child("bem-estar")
                            .child(dataSnapshot.getKey())
                            .child("geral");

                    reference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            int[] dias  =  new int[(int) snapshot.getChildrenCount()];
                            int[] meses =  new int[(int) snapshot.getChildrenCount()];
                            int[] anos  =  new int[(int) snapshot.getChildrenCount()];

                            for( DataSnapshot dataSnapshot1 : snapshot.getChildren() ) {

                                BemEstar bemEstar = dataSnapshot1.getValue(BemEstar.class);



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

    public void recuperarUsuario() {

        FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

        if(auth.getCurrentUser() != null) {

            String email = auth.getCurrentUser().getEmail();
            assert email != null;
            currentId = Base64Custom.codificarBase64(email);
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