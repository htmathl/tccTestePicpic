package com.example.testepicpic.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.testepicpic.R;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.helper.Base64Custom;
import com.example.testepicpic.model.Exercicio;
import com.example.testepicpic.utils.MaskEditUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddExercicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddExercicioFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private EditText edtEsporte, edtDescricao;

    private Button btnHojeEx;
    private Button btnHora;

    private ImageButton imgbSalvar;

    private RadioButton rdbCorrida, rdbCaminhada, rdbCiclismo, rdbAcademia, rdbNatacao, rdbMusculacao, rdbArtesMarciais,
            rdbYoga, rdbBox, rdbPilates, rdbTenis, rdbVolei, rdbFut, rdbHandBol, rdbBasquete;

    private int Hour, min, pDay, pMonth, pYear, hora;

    private String strModal, currentId;

    private ConstraintLayout clEx;

    private DatabaseReference ref;

    private Exercicio exercicio;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddExercicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddExercicio.
     */
    // TODO: Rename and change types and number of parameters
    public static AddExercicioFragment newInstance(String param1, String param2) {
        AddExercicioFragment fragment = new AddExercicioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_exercicio, container, false);

        btnHojeEx = view.findViewById(R.id.btnExDia);

        final Button btnModal = view.findViewById(R.id.btnModalidade);

        final EditText edtDuracao = view.findViewById(R.id.edtDuracao);

        btnHora = view.findViewById(R.id.btnTimeEx);

        edtDuracao.addTextChangedListener(MaskEditUtil.mask(edtDuracao, MaskEditUtil.FORMAT_HOUR));

        clEx = view.findViewById(R.id.clEx);

        rdbCorrida = view.findViewById(R.id.checkBox41);
        rdbCaminhada = view.findViewById(R.id.checkBox42);
        rdbCiclismo = view.findViewById(R.id.checkBox43);
        rdbAcademia = view.findViewById(R.id.checkBox44);
        rdbNatacao = view.findViewById(R.id.checkBox45);
        rdbMusculacao = view.findViewById(R.id.checkBox46);
        rdbArtesMarciais = view.findViewById(R.id.checkBox47);
        rdbYoga = view.findViewById(R.id.checkBox48);
        rdbBox = view.findViewById(R.id.checkBox49);
        rdbPilates = view.findViewById(R.id.checkBox50);
        rdbTenis = view.findViewById(R.id.checkBox51);
        rdbVolei = view.findViewById(R.id.checkBox52);
        rdbFut = view.findViewById(R.id.checkBox53);
        rdbHandBol = view.findViewById(R.id.checkBox54);
        rdbBasquete = view.findViewById(R.id.checkBox55);

        rdbCorrida.setOnCheckedChangeListener(this);
        rdbCaminhada.setOnCheckedChangeListener(this);
        rdbCiclismo.setOnCheckedChangeListener(this);
        rdbAcademia.setOnCheckedChangeListener(this);
        rdbNatacao.setOnCheckedChangeListener(this);
        rdbMusculacao.setOnCheckedChangeListener(this);
        rdbArtesMarciais.setOnCheckedChangeListener(this);
        rdbYoga.setOnCheckedChangeListener(this);
        rdbBox.setOnCheckedChangeListener(this);
        rdbPilates.setOnCheckedChangeListener(this);
        rdbTenis.setOnCheckedChangeListener(this);
        rdbVolei.setOnCheckedChangeListener(this);
        rdbFut.setOnCheckedChangeListener(this);
        rdbHandBol.setOnCheckedChangeListener(this);
        rdbBasquete.setOnCheckedChangeListener(this);

        Button btnSalvar = view.findViewById(R.id.btnSalvar2);

        imgbSalvar = view.findViewById(R.id.btnCheck);

        TextView txtOutroEsporte = view.findViewById(R.id.txtOutroEsporte);

        edtEsporte = view.findViewById(R.id.edtEsporte);

        edtDescricao = view.findViewById(R.id.edtDescicao);

        Calendar c = Calendar.getInstance();
        Hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

        final RadioButton[] pEsportes = {
                rdbCorrida, rdbCaminhada, rdbCiclismo, rdbAcademia, rdbNatacao, rdbMusculacao,
                rdbArtesMarciais, rdbYoga, rdbBox, rdbPilates, rdbTenis, rdbVolei, rdbFut,
                rdbHandBol, rdbBasquete,
        };

        btnHojeEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btnHojeEx.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                        pDay = dayOfMonth;
                        pMonth = (month+1);
                        pYear = year;
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        btnModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.to_down);

                clEx.startAnimation(animation);

                clEx.setVisibility(View.VISIBLE);

            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtEsporte.getText().toString().equals("")) {

                    for(RadioButton pEsporte : pEsportes) {
                        if(pEsporte.isChecked()) {
                            strModal = pEsporte.getText().toString();
                            btnModal.setText(pEsporte.getText());
                        }
                    }

                } else {
                    strModal = edtEsporte.getText().toString();
                    btnModal.setText(strModal);
                }

                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.from_top);

                clEx.startAnimation(animation);

                clEx.setVisibility(View.GONE);

                edtEsporte.setVisibility(View.GONE);
                edtEsporte.setText("");

                for(RadioButton pEsporte : pEsportes)
                    pEsporte.setChecked(false);

            }
        });


        txtOutroEsporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //murulo que fez :))

                if(edtEsporte.getVisibility() == View.GONE) {
                    Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.fade_in);

                    edtEsporte.startAnimation(animation);

                    edtEsporte.setVisibility(View.VISIBLE);
                }

            }
        });

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        hora = (hourOfDay * 60 + minute);

                        btnHora.setText(String.format("%02d:%02d", hourOfDay, minute));

                    }
                }, Hour, min, true);

                timePickerDialog.show();
            }
        });

        imgbSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarUsurario();

                ref = ConfigFirebase.getFirebase();

                final String txtDuracao = edtDuracao.getText().toString();

                final String txtDescricao = edtDescricao.getText().toString();

                if(btnHojeEx.getText().toString().equals("Hoje")) {
                    pYear = Calendar.getInstance().get(Calendar.YEAR);
                    pMonth = (Calendar.getInstance().get(Calendar.MONTH)+1);
                    pDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                }

                if(!btnHora.getText().equals("")) {
                    if(!edtDuracao.getText().toString().equals("")) {
                        if(!btnModal.getText().equals("")) {

                            try {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                                builder.setTitle("Deseja mesmo salvar?");

                                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        exercicio = new Exercicio();

                                        exercicio.setModalidade(strModal);
                                        exercicio.setDuracao(txtDuracao);
                                        exercicio.setHora(hora);
                                        exercicio.setDescricao(txtDescricao);
                                        exercicio.setDia(pDay);
                                        exercicio.setMes(pMonth);
                                        exercicio.setAno(pYear);

                                        exercicio.salvar(String.valueOf(pDay), String.valueOf(pMonth), String.valueOf(pYear));

                                        /*ref.child("users")
                                                .child(currentId)
                                                .child("inserção")
                                                .child("exercicio")
                                                .child(String.valueOf(pYear))
                                                .child(String.valueOf(pMonth))
                                                .child(String.valueOf(pDay))
                                                .child("horário")
                                                .child(String.valueOf(hora))
                                                .child("Modalidade")
                                                .setValue(strModal);

                                        ref.child("users")
                                                .child(currentId)
                                                .child("inserção")
                                                .child("exercicio")
                                                .child(String.valueOf(pYear))
                                                .child(String.valueOf(pMonth))
                                                .child(String.valueOf(pDay))
                                                .child("horário")
                                                .child(String.valueOf(hora))
                                                .child("Duração")
                                                .setValue(txtDuracao);

                                        ref.child("users")
                                                .child(currentId)
                                                .child("inserção")
                                                .child("exercicio")
                                                .child(String.valueOf(pYear))
                                                .child(String.valueOf(pMonth))
                                                .child(String.valueOf(pDay))
                                                .child("horário")
                                                .child(String.valueOf(hora))
                                                .child("hora")
                                                .setValue(hora);

                                        ref.child("users")
                                                .child(currentId)
                                                .child("inserção")
                                                .child("exercicio")
                                                .child(String.valueOf(pYear))
                                                .child(String.valueOf(pMonth))
                                                .child(String.valueOf(pDay))
                                                .child("horário")
                                                .child(String.valueOf(hora))
                                                .child("Descrição")
                                                .setValue(txtDescricao);*/

                                        Toast.makeText(getActivity(), "Pronto, anotação salva :)", Toast.LENGTH_SHORT).show();

                                        getActivity().finish();

                                    }
                                });

                                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) { }});

                                builder.create();
                                builder.show();

                            } catch (Exception e) {
                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getActivity(), "Pòr favor, selecione uma modalidade", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Por favor, digite uma duração", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Por favor, selecione um horário", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(isChecked) {

            if(buttonView.getId() == R.id.checkBox41){

                rdbCaminhada.setChecked(false);
                rdbCiclismo.setChecked(false);
                rdbAcademia.setChecked(false);
                rdbNatacao.setChecked(false);
                rdbMusculacao.setChecked(false);
                rdbArtesMarciais.setChecked(false);
                rdbYoga.setChecked(false);
                rdbBox.setChecked(false);
                rdbPilates.setChecked(false);
                rdbTenis.setChecked(false);
                rdbVolei.setChecked(false);
                rdbFut.setChecked(false);
                rdbHandBol.setChecked(false);
                rdbBasquete.setChecked(false);

            }

            if(buttonView.getId() == R.id.checkBox42) {

                rdbCorrida.setChecked(false);
                rdbCiclismo.setChecked(false);
                rdbAcademia.setChecked(false);
                rdbNatacao.setChecked(false);
                rdbMusculacao.setChecked(false);
                rdbArtesMarciais.setChecked(false);
                rdbYoga.setChecked(false);
                rdbBox.setChecked(false);
                rdbPilates.setChecked(false);
                rdbTenis.setChecked(false);
                rdbVolei.setChecked(false);
                rdbFut.setChecked(false);
                rdbHandBol.setChecked(false);
                rdbBasquete.setChecked(false);

            }

            if(buttonView.getId() == R.id.checkBox43) {

                rdbCorrida.setChecked(false);
                rdbCaminhada.setChecked(false);
                rdbAcademia.setChecked(false);
                rdbNatacao.setChecked(false);
                rdbMusculacao.setChecked(false);
                rdbArtesMarciais.setChecked(false);
                rdbYoga.setChecked(false);
                rdbBox.setChecked(false);
                rdbPilates.setChecked(false);
                rdbTenis.setChecked(false);
                rdbVolei.setChecked(false);
                rdbFut.setChecked(false);
                rdbHandBol.setChecked(false);
                rdbBasquete.setChecked(false);

            }

            if(buttonView.getId() == R.id.checkBox44) {

                rdbCorrida.setChecked(false);
                rdbCaminhada.setChecked(false);
                rdbCiclismo.setChecked(false);
                rdbNatacao.setChecked(false);
                rdbMusculacao.setChecked(false);
                rdbArtesMarciais.setChecked(false);
                rdbYoga.setChecked(false);
                rdbBox.setChecked(false);
                rdbPilates.setChecked(false);
                rdbTenis.setChecked(false);
                rdbVolei.setChecked(false);
                rdbFut.setChecked(false);
                rdbHandBol.setChecked(false);
                rdbBasquete.setChecked(false);

            }

            if(buttonView.getId() == R.id.checkBox45) {

                rdbCorrida.setChecked(false);
                rdbCaminhada.setChecked(false);
                rdbCiclismo.setChecked(false);
                rdbAcademia.setChecked(false);
                rdbMusculacao.setChecked(false);
                rdbArtesMarciais.setChecked(false);
                rdbYoga.setChecked(false);
                rdbBox.setChecked(false);
                rdbPilates.setChecked(false);
                rdbTenis.setChecked(false);
                rdbVolei.setChecked(false);
                rdbFut.setChecked(false);
                rdbHandBol.setChecked(false);
                rdbBasquete.setChecked(false);

            }

            if(buttonView.getId() == R.id.checkBox46) {

                rdbCorrida.setChecked(false);
                rdbCaminhada.setChecked(false);
                rdbCiclismo.setChecked(false);
                rdbAcademia.setChecked(false);
                rdbNatacao.setChecked(false);
                rdbArtesMarciais.setChecked(false);
                rdbYoga.setChecked(false);
                rdbBox.setChecked(false);
                rdbPilates.setChecked(false);
                rdbTenis.setChecked(false);
                rdbVolei.setChecked(false);
                rdbFut.setChecked(false);
                rdbHandBol.setChecked(false);
                rdbBasquete.setChecked(false);

            }

            if(buttonView.getId() == R.id.checkBox47) {

                rdbCorrida.setChecked(false);
                rdbCaminhada.setChecked(false);
                rdbCiclismo.setChecked(false);
                rdbAcademia.setChecked(false);
                rdbNatacao.setChecked(false);
                rdbMusculacao.setChecked(false);
                rdbYoga.setChecked(false);
                rdbBox.setChecked(false);
                rdbPilates.setChecked(false);
                rdbTenis.setChecked(false);
                rdbVolei.setChecked(false);
                rdbFut.setChecked(false);
                rdbHandBol.setChecked(false);
                rdbBasquete.setChecked(false);

            }

            if(buttonView.getId() == R.id.checkBox48) {

                rdbCorrida.setChecked(false);
                rdbCaminhada.setChecked(false);
                rdbCiclismo.setChecked(false);
                rdbAcademia.setChecked(false);
                rdbNatacao.setChecked(false);
                rdbMusculacao.setChecked(false);
                rdbArtesMarciais.setChecked(false);
                rdbBox.setChecked(false);
                rdbPilates.setChecked(false);
                rdbTenis.setChecked(false);
                rdbVolei.setChecked(false);
                rdbFut.setChecked(false);
                rdbHandBol.setChecked(false);
                rdbBasquete.setChecked(false);

            }

            if(buttonView.getId() == R.id.checkBox49) {

                rdbCorrida.setChecked(false);
                rdbCaminhada.setChecked(false);
                rdbCiclismo.setChecked(false);
                rdbAcademia.setChecked(false);
                rdbNatacao.setChecked(false);
                rdbMusculacao.setChecked(false);
                rdbArtesMarciais.setChecked(false);
                rdbYoga.setChecked(false);
                rdbPilates.setChecked(false);
                rdbTenis.setChecked(false);
                rdbVolei.setChecked(false);
                rdbFut.setChecked(false);
                rdbHandBol.setChecked(false);
                rdbBasquete.setChecked(false);

            }

            if(buttonView.getId() == R.id.checkBox50) {

                rdbCorrida.setChecked(false);
                rdbCaminhada.setChecked(false);
                rdbCiclismo.setChecked(false);
                rdbAcademia.setChecked(false);
                rdbNatacao.setChecked(false);
                rdbMusculacao.setChecked(false);
                rdbArtesMarciais.setChecked(false);
                rdbYoga.setChecked(false);
                rdbBox.setChecked(false);
                rdbTenis.setChecked(false);
                rdbVolei.setChecked(false);
                rdbFut.setChecked(false);
                rdbHandBol.setChecked(false);
                rdbBasquete.setChecked(false);

            }

            if(buttonView.getId() == R.id.checkBox51) {

                rdbCorrida.setChecked(false);
                rdbCaminhada.setChecked(false);
                rdbCiclismo.setChecked(false);
                rdbAcademia.setChecked(false);
                rdbNatacao.setChecked(false);
                rdbMusculacao.setChecked(false);
                rdbArtesMarciais.setChecked(false);
                rdbYoga.setChecked(false);
                rdbBox.setChecked(false);
                rdbPilates.setChecked(false);
                rdbVolei.setChecked(false);
                rdbFut.setChecked(false);
                rdbHandBol.setChecked(false);
                rdbBasquete.setChecked(false);

            }

            if(buttonView.getId() == R.id.checkBox52) {

                rdbCorrida.setChecked(false);
                rdbCaminhada.setChecked(false);
                rdbCiclismo.setChecked(false);
                rdbAcademia.setChecked(false);
                rdbNatacao.setChecked(false);
                rdbMusculacao.setChecked(false);
                rdbArtesMarciais.setChecked(false);
                rdbYoga.setChecked(false);
                rdbBox.setChecked(false);
                rdbPilates.setChecked(false);
                rdbTenis.setChecked(false);
                rdbFut.setChecked(false);
                rdbHandBol.setChecked(false);
                rdbBasquete.setChecked(false);

            }

            if(buttonView.getId() == R.id.checkBox53) {

                rdbCorrida.setChecked(false);
                rdbCaminhada.setChecked(false);
                rdbCiclismo.setChecked(false);
                rdbAcademia.setChecked(false);
                rdbNatacao.setChecked(false);
                rdbMusculacao.setChecked(false);
                rdbArtesMarciais.setChecked(false);
                rdbYoga.setChecked(false);
                rdbBox.setChecked(false);
                rdbPilates.setChecked(false);
                rdbTenis.setChecked(false);
                rdbVolei.setChecked(false);
                rdbHandBol.setChecked(false);
                rdbBasquete.setChecked(false);

            }

            if(buttonView.getId() == R.id.checkBox54) {

                rdbCorrida.setChecked(false);
                rdbCaminhada.setChecked(false);
                rdbCiclismo.setChecked(false);
                rdbAcademia.setChecked(false);
                rdbNatacao.setChecked(false);
                rdbMusculacao.setChecked(false);
                rdbArtesMarciais.setChecked(false);
                rdbYoga.setChecked(false);
                rdbBox.setChecked(false);
                rdbPilates.setChecked(false);
                rdbTenis.setChecked(false);
                rdbVolei.setChecked(false);
                rdbFut.setChecked(false);
                rdbBasquete.setChecked(false);

            }

            if(buttonView.getId() == R.id.checkBox55) {

                rdbCorrida.setChecked(false);
                rdbCaminhada.setChecked(false);
                rdbCiclismo.setChecked(false);
                rdbAcademia.setChecked(false);
                rdbNatacao.setChecked(false);
                rdbMusculacao.setChecked(false);
                rdbArtesMarciais.setChecked(false);
                rdbYoga.setChecked(false);
                rdbBox.setChecked(false);
                rdbPilates.setChecked(false);
                rdbTenis.setChecked(false);
                rdbVolei.setChecked(false);
                rdbFut.setChecked(false);
                rdbHandBol.setChecked(false);

            }

        }

    }

    public void recuperarUsurario() {
        FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

        if(auth.getCurrentUser() != null) {

            String email = auth.getCurrentUser().getEmail();
            assert email != null;
            currentId = Base64Custom.codificarBase64(email);

        }

    }

}