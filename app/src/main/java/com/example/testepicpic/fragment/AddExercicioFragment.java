package com.example.testepicpic.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.testepicpic.R;
import com.example.testepicpic.utils.MaskEditUtil;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddExercicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddExercicioFragment extends Fragment {

    private TextView txtOutroEsporte;

    private EditText edtDuracao, edtEsporte;

    private Button btnHojeEx, btnModal, btnHora;

    private int Hour, min, pDay, pMonth, pYear, hora;

    private ConstraintLayout clEx;

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

        btnModal = view.findViewById(R.id.btnModalidade);

        edtDuracao = view.findViewById(R.id.edtDuracao);

        btnHora = view.findViewById(R.id.btnTimeEx);

        edtDuracao.addTextChangedListener(MaskEditUtil.mask(edtDuracao, MaskEditUtil.FORMAT_HOUR));

        clEx = view.findViewById(R.id.clEx);

        txtOutroEsporte = view.findViewById(R.id.txtOutroEsporte);

        edtEsporte = view.findViewById(R.id.edtEsporte);


        Calendar c = Calendar.getInstance();
        Hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

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

        clEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.from_top);

                clEx.startAnimation(animation);

                clEx.setVisibility(View.GONE);

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

        return view;
    }
}