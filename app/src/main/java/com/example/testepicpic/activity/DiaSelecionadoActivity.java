package com.example.testepicpic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testepicpic.R;
import com.example.testepicpic.adapter.AlimentacoesAdapter;
import com.example.testepicpic.adapter.BemEstarAdapter;
import com.example.testepicpic.adapter.ExerciciosAdapter;
import com.example.testepicpic.adapter.GlicemiasAdapter;
import com.example.testepicpic.adapter.InsulinaAdapter;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.helper.Base64Custom;
import com.example.testepicpic.helper.RecyclerItemClickListener;
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
import com.rockerhieu.rvadapter.states.StatesRecyclerViewAdapter;

import java.util.ArrayList;

public class DiaSelecionadoActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    public static final String PREFS_NAME = "shareData";
    private String strMonth;

    private static final String TAG = "swipe position";
    private float x1, y1, x2, y2;
    private static int minDistance = 150;
    private GestureDetector gestureDetector;

    private DatabaseReference ref, referenceGli, referenceEx, referenceAli, referenceInsu, referenceBem;
    private String currentId;

    private ArrayList<Double>  nivelGli     = new ArrayList<>();
    private ArrayList<String>  categoriaGli = new ArrayList<>();
    private ArrayList<String>  localGli     = new ArrayList<>();
    private ArrayList<String>  ladoGli      = new ArrayList<>();
    private ArrayList<Integer> horaGli      = new ArrayList<>();

    private RecyclerView recyclerView, recyclerView2, recyclerView3, recyclerView4, recyclerView5;

    private ArrayList<Glicemia> listaGlicemia = new ArrayList<>();
    private ArrayList<Exercicio> listaExercicio = new ArrayList<>();
    private ArrayList<Alimentacao> listaAlimentacao = new ArrayList<>();
    private ArrayList<Insulina> listaInsulina = new ArrayList<>();
    private ArrayList<BemEstar> listaBemEstar = new ArrayList<>();

    private int today, month, year, i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0;

    private GlicemiasAdapter adapter;
    private ExerciciosAdapter adapter2;
    private AlimentacoesAdapter adapter3;
    private InsulinaAdapter adapter4;
    private BemEstarAdapter adapter5;

    private ValueEventListener valueEventListenerGli, valueEventListenerEx, valueEventListenerAli,
            valueEventListenerInsu, valueEventListenerBem;

    private TextView txtNoDataGli, txtNoDataEx, txtNoDataAli, txtNoDataInsu, txtNoDataBem;

    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_selecionado);

        //declarar textviews
        txtNoDataGli = findViewById(R.id.txtNoDataGli);
        txtNoDataEx = findViewById(R.id.txtNoDataEx);
        txtNoDataAli = findViewById(R.id.txtNoDataAli);
        txtNoDataInsu = findViewById(R.id.txtNoDataInsu);
        txtNoDataBem = findViewById(R.id.txtNoDataBem);

        //recuperar valores da data
        recuperarUsuario();

        //delize pra sair
        gestureDetector = new GestureDetector(DiaSelecionadoActivity.this, this);

        //limpar listas
        nivelGli.clear(); categoriaGli.clear(); ladoGli.clear();
        localGli.clear(); horaGli.clear();

        //definir referencias
        data = String.valueOf(year) + String.valueOf(month) + String.valueOf(today);

        try {

            referenceGli = ref.child("inserção")
                    .child(currentId)
                    .child("glicemia")
                    .child(data);

            referenceEx = ref.child("inserção")
                    .child(currentId)
                    .child("exercicio")
                    .child(data);

            referenceAli = ref.child("inserção")
                    .child(currentId)
                    .child("alimentação")
                    .child(data);

            referenceInsu = ref.child("inserção")
                    .child(currentId)
                    .child("insulina")
                    .child(data);

            referenceBem = ref.child("inserção")
                    .child(currentId)
                    .child("bem-estar");

        } catch (Exception e) {

        }

        //recuperar todos os valores.
        recuperarAll();

        //definir data no txt principal
        TextView txtData = (TextView) findViewById(R.id.textView28);
        String strData = String.valueOf(year) + "\n" + String.valueOf(today) + " " + strMonth;
        txtData.setText(strData);

        recyclerView  = findViewById(R.id.rcv_conteudoGli);
        recyclerView2 = findViewById(R.id.rcv_conteudoEx);
        recyclerView3 = findViewById(R.id.rcv_conteudoAli);
        recyclerView4 = findViewById(R.id.rcv_conteudoInsu);
        recyclerView5 = findViewById(R.id.rcv_conteudoBem);

        //declarar adapter
        adapter = new GlicemiasAdapter(listaGlicemia, DiaSelecionadoActivity.this);
        adapter2 = new ExerciciosAdapter(listaExercicio, DiaSelecionadoActivity.this);
        adapter3 = new AlimentacoesAdapter(listaAlimentacao, DiaSelecionadoActivity.this);
        adapter4 = new InsulinaAdapter(listaInsulina, DiaSelecionadoActivity.this);
        adapter5 = new BemEstarAdapter(listaBemEstar, DiaSelecionadoActivity.this);

        //config recyclerGli
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DiaSelecionadoActivity.this);
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize( true );
        recyclerView.setAdapter(adapter);

        //config recyclerEx
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(DiaSelecionadoActivity.this);
        recyclerView2.setLayoutManager( layoutManager2 );
        recyclerView2.setHasFixedSize( true );
        recyclerView2.setAdapter(adapter2);

        //config recyclerAli
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(DiaSelecionadoActivity.this);
        recyclerView3.setLayoutManager( layoutManager3 );
        recyclerView3.setHasFixedSize( true );
        recyclerView3.setAdapter(adapter3);

        //config recyclerInsu
        RecyclerView.LayoutManager layoutManager4 = new LinearLayoutManager(DiaSelecionadoActivity.this);
        recyclerView4.setLayoutManager( layoutManager4 );
        recyclerView4.setHasFixedSize( true );
        recyclerView4.setAdapter(adapter4);

        //config recyclerBem
        RecyclerView.LayoutManager layoutManager5 = new LinearLayoutManager(DiaSelecionadoActivity.this);
        recyclerView5.setLayoutManager( layoutManager5 );
        recyclerView5.setHasFixedSize( true );
        recyclerView5.setAdapter(adapter5);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //limpar recycler
        referenceGli.removeEventListener( valueEventListenerGli );
        referenceEx.removeEventListener( valueEventListenerEx );
        referenceAli.removeEventListener( valueEventListenerAli );
        referenceInsu.removeEventListener( valueEventListenerInsu );
        referenceBem.removeEventListener( valueEventListenerBem );
    }

    public void recuperarAll() {

        recuperarUsuario();

        String data = String.valueOf(year) + month + today;

        try {

            //add glicemia
            valueEventListenerGli = referenceGli.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if( data.equals( snapshot.getKey() ) ) {

                        for ( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                            Glicemia glicemia = dataSnapshot.getValue(Glicemia.class);

                            listaGlicemia.add(glicemia);

                            //editar e remover itens
                            recyclerView.addOnItemTouchListener(
                                    new RecyclerItemClickListener(
                                            DiaSelecionadoActivity.this,
                                            recyclerView,
                                            new RecyclerItemClickListener.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(View view, int position) {

                                                }

                                                @Override
                                                public void onLongItemClick(View view, int position) {

                                                    AlertDialog.Builder builder = new AlertDialog.Builder(DiaSelecionadoActivity.this);

                                                    builder.setTitle("Deseja excluir este item?");


                                                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                            if( position == i1 )
                                                                dataSnapshot.getRef().removeValue();

                                                            i1++;

                                                            listaGlicemia.remove(position);
                                                            recyclerView.removeViewAt(position);
                                                            adapter.notifyItemRemoved(position);
                                                            adapter.notifyItemRangeChanged(position, listaGlicemia.size());

                                                        }
                                                    });

                                                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                        }
                                                    });

                                                    builder.create();
                                                    builder.show();

                                                }

                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                }
                                            })
                            );

                        }

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

                        //editar e remover itens
                        recyclerView2.addOnItemTouchListener(
                                new RecyclerItemClickListener(
                                        DiaSelecionadoActivity.this,
                                        recyclerView2,
                                        new RecyclerItemClickListener.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {

                                            }

                                            @Override
                                            public void onLongItemClick(View view, int position) {

                                                AlertDialog.Builder builder = new AlertDialog.Builder(DiaSelecionadoActivity.this);

                                                builder.setTitle("Deseja excluir este item?");


                                                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        if( position == i2 )
                                                            dataSnapshot.getRef().removeValue();

                                                        i2++;

                                                        listaExercicio.remove(position);
                                                        recyclerView2.removeViewAt(position);
                                                        adapter2.notifyItemRemoved(position);
                                                        adapter2.notifyItemRangeChanged(position, listaExercicio.size());

                                                    }
                                                });

                                                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                });

                                                builder.create();
                                                builder.show();

                                            }

                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            }
                                        })
                        );

                    }

                    if(adapter2.getItemCount() == 0)
                        txtNoDataEx.setVisibility(View.VISIBLE);

                    adapter2.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //add alimento
            valueEventListenerAli = referenceAli.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for ( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                        Alimentacao alimentacao = dataSnapshot.getValue(Alimentacao.class);

                        listaAlimentacao.add(alimentacao);

                        //editar e remover itens
                        recyclerView3.addOnItemTouchListener(
                                new RecyclerItemClickListener(
                                        DiaSelecionadoActivity.this,
                                        recyclerView3,
                                        new RecyclerItemClickListener.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {

                                            }

                                            @Override
                                            public void onLongItemClick(View view, int position) {

                                                AlertDialog.Builder builder = new AlertDialog.Builder(DiaSelecionadoActivity.this);

                                                builder.setTitle("Deseja excluir este item?");


                                                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        if( position == i3 )
                                                            dataSnapshot.getRef().removeValue();

                                                        i3++;

                                                        listaAlimentacao.remove(position);
                                                        recyclerView3.removeViewAt(position);
                                                        adapter3.notifyItemRemoved(position);
                                                        adapter3.notifyItemRangeChanged(position, listaAlimentacao.size());

                                                    }
                                                });

                                                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                });

                                                builder.create();
                                                builder.show();

                                            }

                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            }
                                        })
                        );

                    }

                    if(adapter3.getItemCount() == 0)
                        txtNoDataAli.setVisibility(View.VISIBLE);

                    adapter3.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //add insulina
            valueEventListenerInsu = referenceInsu.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for ( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                        Insulina insulina = dataSnapshot.getValue(Insulina.class);

                        listaInsulina.add(insulina);

                        //editar e remover itens
                        recyclerView4.addOnItemTouchListener(
                                new RecyclerItemClickListener(
                                        DiaSelecionadoActivity.this,
                                        recyclerView4,
                                        new RecyclerItemClickListener.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {

                                            }

                                            @Override
                                            public void onLongItemClick(View view, int position) {

                                                AlertDialog.Builder builder = new AlertDialog.Builder(DiaSelecionadoActivity.this);

                                                builder.setTitle("Deseja excluir este item?");


                                                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        if( position == i4 )
                                                            dataSnapshot.getRef().removeValue();

                                                        i4++;

                                                        listaInsulina.remove(position);
                                                        recyclerView4.removeViewAt(position);
                                                        adapter4.notifyItemRemoved(position);
                                                        adapter4.notifyItemRangeChanged(position, listaInsulina.size());

                                                    }
                                                });

                                                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                });

                                                builder.create();
                                                builder.show();

                                            }

                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            }
                                        })
                        );

                    }

                    if(adapter4.getItemCount() == 0)
                        txtNoDataInsu.setVisibility(View.VISIBLE);

                    adapter4.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //add bem-estar
            valueEventListenerBem = referenceBem.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    try {

                        for( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                            if(data.equals(dataSnapshot.getKey())) {

                                if(dataSnapshot.hasChild("geral")) {

                                    DatabaseReference reference = ref.child("inserção")
                                            .child(currentId)
                                            .child("bem-estar")
                                            .child(data)
                                            .child("geral");

                                    reference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                            BemEstar bemEstar = snapshot1.getValue(BemEstar.class);

                                            listaBemEstar.add(bemEstar);

                                            try {

                                                //editar e remover itens
                                                recyclerView5.addOnItemTouchListener(
                                                        new RecyclerItemClickListener(
                                                                DiaSelecionadoActivity.this,
                                                                recyclerView5,
                                                                new RecyclerItemClickListener.OnItemClickListener() {
                                                                    @Override
                                                                    public void onItemClick(View view, int position) {

                                                                    }

                                                                    @Override
                                                                    public void onLongItemClick(View view, int position) {

                                                                        AlertDialog.Builder builder = new AlertDialog.Builder(DiaSelecionadoActivity.this);

                                                                        builder.setTitle("Deseja excluir este item?");


                                                                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {

                                                                                snapshot1.getRef().removeValue();

                                                                                listaBemEstar.remove(position);
                                                                                recyclerView5.removeViewAt(position);
                                                                                adapter5.notifyItemRemoved(position);
                                                                                adapter5.notifyItemRangeChanged(position, listaBemEstar.size());

                                                                            }
                                                                        });

                                                                        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {

                                                                            }
                                                                        });

                                                                        builder.create();
                                                                        builder.show();

                                                                    }

                                                                    @Override
                                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                                    }
                                                                })
                                                );

                                            }catch (Exception e) {

                                            }

                                            adapter5.notifyDataSetChanged();

                                            if(adapter5.getItemCount() != 0)
                                                txtNoDataBem.setVisibility(View.GONE);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }

                            }


                        }

                    } catch (Exception e) {

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } catch (Exception e) {

        }

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