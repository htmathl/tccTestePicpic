package com.example.testepicpic.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.testepicpic.R;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.helper.Base64Custom;
import com.example.testepicpic.model.Alimentacao;
import com.example.testepicpic.model.BemEstar;
import com.example.testepicpic.model.Exercicio;
import com.example.testepicpic.model.Glicemia;
import com.example.testepicpic.model.Insulina;
import com.example.testepicpic.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RelatorioFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class RelatorioFragment extends Fragment {

    private Button btnGerarRelatorio;
    private Bitmap onda, escala;
    private PdfDocument pdfTeste;
    private PdfDocument.PageInfo info;
    private PdfDocument.Page pagina1;

    private DatabaseReference ref;
    private String currentId;

    private String nome, altura, peso, idade, tipoDiabetes;

    private final List<Double> listaNivelGli = new ArrayList<>();
    private final List<Integer> listaDiaGli = new ArrayList<>();
    private final List<Integer> listaMesGli = new ArrayList<>();
    private final List<Integer> listaAnoGli = new ArrayList<>();
    private final List<String> listaCategoriaGli = new ArrayList<>();
    private final List<String> listaLadoGli = new ArrayList<>();
    private final List<String> listaLocalGli = new ArrayList<>();
    private final List<String> listaHoraGli = new ArrayList<>();

    private final List<Double> listaNivelInsu = new ArrayList<>();
    private final List<Integer> listaDiaInsu = new ArrayList<>();
    private final List<Integer> listaMesInsu = new ArrayList<>();
    private final List<Integer> listaAnoInsu = new ArrayList<>();
    private final List<String> listaCategoriaInsu = new ArrayList<>();
    private final List<String> listaLocalInsu = new ArrayList<>();
    private final List<String> listaHoraInsu = new ArrayList<>();

    private final List<String> listaModalidadeEx = new ArrayList<>();
    private final List<Integer> listaDiaEx = new ArrayList<>();
    private final List<Integer> listaMesEx = new ArrayList<>();
    private final List<Integer> listaAnoEx = new ArrayList<>();
    private final List<String> listaHoraEx = new ArrayList<>();
    private final List<String> listaDescriEx = new ArrayList<>();
    private final List<String> listaDuracaoEx = new ArrayList<>();

    private final List<String> listaTipoAli   = new ArrayList<>();
    private final List<String> listaAlimentos = new ArrayList<>();
    private final List<String> listaDescriAli = new ArrayList<>();
    private final List<Integer> listaDiaAli = new ArrayList<>();
    private final List<Integer> listaMesAli = new ArrayList<>();
    private final List<Integer> listaAnoAli = new ArrayList<>();

    private final List<String> listaHumor = new ArrayList<>();
    private final List<String> listaDescriBem = new ArrayList<>();
    private final List<String> listaSintomas = new ArrayList<>();
    private final List<Integer> listaDiaBem = new ArrayList<>();
    private final List<Integer> listaMesBem = new ArrayList<>();
    private final List<Integer> listaAnoBem = new ArrayList<>();

    private Glicemia glicemia;

    private int j = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RelatorioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RelatorioFragment newInstance(String param1, String param2) {
        RelatorioFragment fragment = new RelatorioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RelatorioFragment() {
        // Required empty public constructor
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
        View view =  inflater.inflate(R.layout.fragment_main_relatorio, container, false);

        recuperarAll();

        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, PackageManager.PERMISSION_GRANTED);

        btnGerarRelatorio = view.findViewById(R.id.btnGerarRelatorio);

        onda = BitmapFactory.decodeResource(getResources(), R.drawable.waves1);
        escala = Bitmap.createScaledBitmap(onda,1200,400, false);


        btnGerarRelatorio.setOnClickListener(v -> {

            AlertDialog.Builder certezaGerar = new AlertDialog.Builder(getActivity());

            certezaGerar.setTitle("Deseja gerar o relatório?");

            certezaGerar.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if( nome != null && idade != null && altura != null && peso != null ) {

                        if( !listaNivelInsu.isEmpty() && !listaNivelGli.isEmpty() && !listaModalidadeEx.isEmpty() && !listaTipoAli.isEmpty() && !listaHumor.isEmpty() ) {

                            Paint pinta = new Paint();
                            Paint titulo = new Paint();
                            Paint pinto = new Paint();
                            Paint pintu = new Paint();
                            Paint pintao = new Paint();
                            Paint pintinho = new Paint();
                            Paint pipoca = new Paint();

                            pdfTeste = new PdfDocument();
                            info  = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                            pagina1 = pdfTeste.startPage(info);
                            Canvas canvinhas = pagina1.getCanvas();
                            canvinhas.drawBitmap(escala,0,0,pinta);

                            titulo.setTextSize(70);
                            titulo.setTextAlign(Paint.Align.CENTER);
                            titulo.setTypeface( Typeface.create( Typeface.DEFAULT, Typeface.BOLD ) );

                            pinto.setTextSize(35);
                            pinto.setTextAlign(Paint.Align.CENTER);

                            pintu.setTextSize(40);
                            pintu.setTextAlign(Paint.Align.CENTER);
                            pintu.setColor( getResources().getColor( R.color.colorPrimary ) );
                            pintu.setTypeface( Typeface.create( Typeface.DEFAULT, Typeface.BOLD ));

                            pintao.setTextSize(60);
                            pintao.setTextAlign(Paint.Align.CENTER);
                            pintao.setColor( getResources().getColor( R.color.colorPrimary ) );
                            pintao.setTypeface( Typeface.create( Typeface.DEFAULT, Typeface.BOLD ) );

                            pintinho.setTextSize(30);
                            pintinho.setTextAlign(Paint.Align.LEFT);

                            pipoca.setTextSize(30);
                            pipoca.setTextAlign(Paint.Align.LEFT);
                            pipoca.setTypeface( Typeface.create( Typeface.DEFAULT, Typeface.BOLD ) );

                            canvinhas.drawText(nome, 600, 450, titulo);
                            canvinhas.drawText(idade + " anos", 600, 550, pinto);
                            canvinhas.drawText(altura + " metros", 300, 550, pinto);
                            canvinhas.drawText(peso + " Kg", 900, 550, pinto);
                            canvinhas.drawText("Diabetes " + tipoDiabetes.toLowerCase(), 600, 650, pinto);

                            List<String> listaMiau = new ArrayList<>();
                            listaMiau.add( "Glicemia: ");
                            listaMiau.add( "Insulina: ");
                            listaMiau.add( "Exercício: ");
                            listaMiau.add( "Alimentação: ");
                            listaMiau.add( "Bem-estar: " );

                            int miau = 0, x2 = 250;

                            int x = 300, x1 = 100, y0 = 800, y = 725, y2 = 800, y3 = 1150, y4 = 100;

                            while(y0 < 1810){

                                for( int i = 0; i < listaMiau.size(); i++ ) {
                                    canvinhas.drawText( listaMiau.get(i), x1, y0, pipoca);
                                    y0 += 35;
                                }
                                y0+=100;
                            }

                            String strMonth = "ERRO 0-0977a254";
                            for ( int i = 0; i < j; i++ ) {

                                switch (listaMesGli.get(i)) {

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

                                if( y >= 2010 ) {

                                    pdfTeste.finishPage(pagina1);
                                    PdfDocument.Page pagina2 = pdfTeste.startPage(info);
                                    Canvas canvinhas2 = pagina2.getCanvas();

                                    canvinhas2.drawText(listaDiaGli.get(i) + " de " + strMonth + " de " + listaAnoGli.get(i), x, y4, pintu);
                                    canvinhas2.drawText("Sua glicemia media " + listaNivelGli.get(i) + " mg/dL, às " + listaHoraGli.get(i) + " horas,", x1, y2, pintinho);
                                    canvinhas2.drawText("furou " + listaLocalGli.get(i) + " " + listaLadoGli.get(i), x1, y3, pintinho);

                                    y4 += 250;

                                    pdfTeste.finishPage(pagina2);

                                } else {
                                    canvinhas.drawText(listaDiaGli.get(i) + " de " + strMonth + " de " + listaAnoGli.get(i), x, y - miau, pintu);
                                    canvinhas.drawText("Sua glicemia media " + listaNivelGli.get(i) + " mg/dL, às " + listaHoraGli.get(i) + " horas,", x2, y2, pintinho);
                                    canvinhas.drawText("hm " + listaNivelInsu.get(i), x2, y2 + 35, pintinho);
                                }

                                miau+=10;
                                y += 285;
                                y2 += 275;
                                y3 += 250;
                            }

                            pdfTeste.finishPage(pagina1);

                            UUID uuid = UUID.randomUUID();
                            String struuid = uuid.toString();

                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/" + struuid + ".pdf");
                            try{
                                pdfTeste.writeTo(new FileOutputStream(file));
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "é n salvou :/", Toast.LENGTH_SHORT).show();
                            }
                            pdfTeste.close();

                        }

                    }

                }
            });

            certezaGerar.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            certezaGerar.create();
            certezaGerar.show();


        });

        return view;
    }

    public void recuperarUsurario() {
        FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

        if(auth.getCurrentUser() != null) {

            String email = auth.getCurrentUser().getEmail();
            assert email != null;
            currentId = Base64Custom.codificarBase64(email);

        }

        ref = ConfigFirebase.getFirebase();

    }

    public void recuperarAll() {

        recuperarUsurario();

        DatabaseReference reference = ref.child("users")
                .child(currentId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Usuario usuario = snapshot.getValue( Usuario.class );

                assert usuario != null;
                nome = usuario.getNome();
                idade = String.valueOf( usuario.getIdade() );
                peso = String.valueOf( usuario.getPeso() );
                altura = String.valueOf( usuario.getAltura() );
                tipoDiabetes = usuario.getTipoDiabetes();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        try {

            int mesAtual = (Calendar.getInstance().get(Calendar.MONTH)+1);

            listaAnoGli.clear();
            listaMesGli.clear();
            listaDiaGli.clear();
            listaHoraGli.clear();
            listaCategoriaGli.clear();
            listaLadoGli.clear();
            listaLocalGli.clear();
            listaNivelGli.clear();

            listaNivelInsu.clear();
            listaDiaInsu.clear();
            listaMesInsu.clear();
            listaAnoInsu.clear();
            listaLocalGli.clear();
            listaCategoriaInsu.clear();
            listaHoraInsu.clear();

            listaModalidadeEx.clear();
            listaDuracaoEx.clear();
            listaDescriEx.clear();
            listaDiaEx.clear();
            listaMesEx.clear();
            listaAnoEx.clear();
            listaHoraEx.clear();

            listaAlimentos.clear();
            listaTipoAli.clear();
            listaAnoAli.clear();
            listaMesAli.clear();
            listaDiaAli.clear();
            listaDescriAli.clear();

            listaHumor.clear();
            listaDescriBem.clear();
            listaSintomas.clear();
            listaAnoBem.clear();
            listaMesBem.clear();
            listaDiaBem.clear();

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
                            public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                int i = 0;

                                for( DataSnapshot dataSnapshot1 : snapshot1.getChildren() ) {

                                    glicemia = dataSnapshot1.getValue( Glicemia.class );

                                    assert glicemia != null;

                                    int hora = glicemia.getHora();
                                    int min = hora % 60;
                                    hora /= 60;

                                    String categoria = glicemia.getCategoria().substring(1, glicemia.getCategoria().length()-1);
                                    String horario = String.format("%02d:%02d", hora, min);

                                    if ( glicemia.getMes() == mesAtual ) {

                                        listaNivelGli.add( i, glicemia.getNivel() );
                                        listaDiaGli.add( i, glicemia.getDia() );
                                        listaMesGli.add( i, glicemia.getMes() );
                                        listaAnoGli.add( i, glicemia.getAno() );
                                        listaCategoriaGli.add( i, categoria );
                                        listaLadoGli.add( i, glicemia.getLado() );
                                        listaLocalGli.add( i, glicemia.getLocal() );
                                        listaHoraGli.add( i, horario );

                                    }
                                    i++;
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });

                        j++;

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            DatabaseReference referenceInsu = ref.child("inserção")
                    .child(currentId)
                    .child("insulina");

            referenceInsu.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                        DatabaseReference reference1 = ref.child("inserção")
                                .child(currentId)
                                .child("insulina")
                                .child(dataSnapshot.getKey());

                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                int i = 0;

                                for( DataSnapshot dataSnapshot1 : snapshot1.getChildren() ) {

                                    Insulina insulina = dataSnapshot1.getValue( Insulina.class );

                                    assert insulina != null;

                                    int hora = insulina.getHora();
                                    int min = hora % 60;
                                    hora /= 60;

                                    String horario = String.format("%02d:%02d", hora, min);

                                    if( insulina.getMes() == mesAtual ) {

                                        listaNivelInsu.add(i, insulina.getNivel() );
                                        listaDiaInsu.add(i, insulina.getDia() );
                                        listaMesInsu.add(i, insulina.getMes() );
                                        listaAnoInsu.add(i, insulina.getAno() );
                                        listaLocalInsu.add(i, insulina.getLocal() );
                                        listaCategoriaInsu.add(i, insulina.getCategoria() );
                                        listaHoraInsu.add(i, horario );

                                    }
                                    i++;

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        j++;

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            DatabaseReference referenceEx = ref.child("inserção")
                    .child(currentId)
                    .child("exercicio");

            referenceEx.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                        DatabaseReference reference1 = ref.child("inserção")
                                .child(currentId)
                                .child("exercicio")
                                .child(dataSnapshot.getKey());

                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                for( DataSnapshot dataSnapshot1 : snapshot1.getChildren() ) {

                                    Exercicio exercicio = dataSnapshot1.getValue( Exercicio.class );

                                    assert exercicio != null;
                                    int hora = exercicio.getHora();
                                    int min = hora % 60;
                                    hora /= 60;

                                    String horario = String.format("%02d:%02d", hora, min);

                                    if( exercicio.getMes() == mesAtual ) {

                                        listaModalidadeEx.add( exercicio.getModalidade() );
                                        listaDuracaoEx.add( exercicio.getDuracao() );
                                        listaDescriEx.add( exercicio.getDescricao() );
                                        listaDiaEx.add( exercicio.getDia() );
                                        listaMesEx.add( exercicio.getMes() );
                                        listaAnoEx.add( exercicio.getAno() );
                                        listaHoraEx.add( horario );

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

            DatabaseReference referenceAli = ref.child("inserção")
                    .child(currentId)
                    .child("alimentação");

            referenceAli.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                        DatabaseReference reference1 = ref.child("inserção")
                                .child(currentId)
                                .child("alimentação")
                                .child(dataSnapshot.getKey());

                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                for( DataSnapshot dataSnapshot1 : snapshot1.getChildren() ) {

                                    Alimentacao alimentacao = dataSnapshot1.getValue( Alimentacao.class );

                                    assert alimentacao != null;
                                    String alimento = alimentacao.getAlimentos().substring(1, alimentacao.getAlimentos().length()-1);

                                    if( alimentacao.getMes() == mesAtual ) {

                                        listaAlimentos.add( alimento );
                                        listaTipoAli.add( alimentacao.getTipo() );
                                        listaAnoAli.add( alimentacao.getAno() );
                                        listaMesAli.add( alimentacao.getMes() );
                                        listaDiaAli.add( alimentacao.getDia() );
                                        listaDescriAli.add( alimentacao.getDescricao() );

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

            DatabaseReference referenceBem = ref.child("inserção")
                    .child(currentId)
                    .child("bem-estar");

            referenceBem.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for( DataSnapshot dataSnapshot : snapshot.getChildren() ) {

                        if( dataSnapshot.hasChild("geral") ) {

                            DatabaseReference reference1 = ref.child("inserção")
                                    .child(currentId)
                                    .child("bem-estar")
                                    .child(dataSnapshot.getKey())
                                    .child("geral");

                            reference1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                    BemEstar bemEstar = snapshot1.getValue( BemEstar.class );

                                    assert bemEstar != null;
                                    if( bemEstar.getMes() == mesAtual ) {

                                        listaHumor.add( bemEstar.getHumor() );
                                        listaDescriBem.add( bemEstar.getDescicao() );
                                        listaSintomas.add( bemEstar.getSintomas() );
                                        listaAnoBem.add( bemEstar.getAno() );
                                        listaMesBem.add( bemEstar.getMes() );
                                        listaDiaBem.add( bemEstar.getDia() );

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

}