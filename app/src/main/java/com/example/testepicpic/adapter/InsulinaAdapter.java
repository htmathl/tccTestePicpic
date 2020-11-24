package com.example.testepicpic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testepicpic.R;
import com.example.testepicpic.model.Insulina;

import java.util.List;

public class InsulinaAdapter extends RecyclerView.Adapter<InsulinaAdapter.ViewHolder> {

    private List<Insulina> insulinas;
    private Context context;

    public InsulinaAdapter(List<Insulina> insulinas, Context c) {
        this.insulinas = insulinas;
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

        Insulina insulina = insulinas.get( position );

        int hora = insulina.getHora();
        Double nivel = insulina.getNivel();

        int min = hora % 60;
        hora /= 60;

        String horario = String.format("%02d:%02d", hora, min);

        String strNivel = nivel + " mg/dL";

        holder.hora.setText( horario );
        holder.nivel.setText( strNivel );
        //holder.verMais.setText( "ver mais" );

    }

    @Override
    public int getItemCount() {
        return insulinas.size();
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
