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

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.testepicpic.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

        btnGerarRelatorio = view.findViewById(R.id.btnGerarRelatorio);

        //onda = BitmapFactory.decodeResource(getResources(), R.drawable.ic_waves);
        //escala = Bitmap.createScaledBitmap(onda,1200,500, false);


        btnGerarRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder certezaGerar = new AlertDialog.Builder(getActivity());

                certezaGerar.setTitle("Deseja gerar o relatório?");

                certezaGerar.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Paint pinta = new Paint();

                        pdfTeste = new PdfDocument();
                        info  = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                        pagina1 = pdfTeste.startPage(info);
                        //Canvas canvas = pagina1.getCanvas();

                        //canvas.drawBitmap(escala, 0, 0, pinta);

                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

                        pdfTeste.finishPage(pagina1);

                        String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/Relatorio.pdf";

                        File file = new File(myFilePath);
                        try{
                            pdfTeste.writeTo(new FileOutputStream(file));
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "é n salvou :/", Toast.LENGTH_SHORT).show();
                        }
                        pdfTeste.close();

                    }
                });

                certezaGerar.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                certezaGerar.create();
                certezaGerar.show();


            }
        });

        return view;
    }
}