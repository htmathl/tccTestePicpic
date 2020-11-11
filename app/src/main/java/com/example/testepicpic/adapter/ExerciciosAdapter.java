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

    public List<Exercicio> exercicios = new ArrayList<>();
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

    }

    @Override
    public int getItemCount() {
        return exercicios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nivel, hora, verMais;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nivel = itemView.findViewById(R.id.txtNivel);
            hora = itemView.findViewById(R.id.txtHora);
            verMais = itemView.findViewById(R.id.txtverMais);

        }
    }

}
