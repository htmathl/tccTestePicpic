package com.example.testepicpic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testepicpic.R;
import com.example.testepicpic.model.Exercicio;

import java.util.ArrayList;
import java.util.List;

public class ExerciciosAdapter extends RecyclerView.Adapter<ExerciciosAdapter.ViewHolder> {

    public List<Exercicio> exercicios;
    public Context context;

    public ExerciciosAdapter(List<Exercicio> exercicios, Context c) {
        this.exercicios = exercicios;
        this.context = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nivel_item, parent, false);

        return new ViewHolder(listItem);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Exercicio exercicio = exercicios.get( position );

        int hora = exercicio.getHora();

        int min = hora % 60;
        hora /= 60;

        String horario = String.format("%02d:%02d", hora, min);

        holder.hora.setText( horario );
        holder.modal.setText( exercicio.getModalidade() );
        //holder.verMais.setText( "ver mais" );

    }

    @Override
    public int getItemCount() {
        return exercicios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView modal, hora, verMais;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            modal = itemView.findViewById(R.id.txtNivel);
            hora = itemView.findViewById(R.id.txtHora);
            verMais = itemView.findViewById(R.id.txtverMais);

        }
    }

}
