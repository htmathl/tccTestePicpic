 package com.example.testepicpic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testepicpic.model.Glicemia;

import java.util.List;

import com.example.testepicpic.R;

 public class GlicemiasAdapter extends RecyclerView.Adapter<GlicemiasAdapter.viewHolder> {

    private List<Glicemia> glicemias;
    private Context context;

    public GlicemiasAdapter(List<Glicemia> glicemias, Context c) {
        this.glicemias = glicemias;
        this.context = c;
    }


    @Override
    public GlicemiasAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int type) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nivel_item, parent, false);

        return new viewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Glicemia glicemia = glicemias.get( position );

        int hora = glicemia.getHora();
        Double nivel = glicemia.getNivel();

        int min = hora % 60;
        hora /= 60;

        String horario = String.format("%02d:%02d", hora, min);

        String strNivel = nivel + " mg/dL";

        holder.hora.setText( horario );
        holder.nivel.setText( strNivel );
        holder.verMais.setText( "ver mais" );

    }

    @Override
    public int getItemCount() {
        return glicemias.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        TextView nivel, hora, verMais;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            nivel = itemView.findViewById(R.id.txtNivel);
            hora = itemView.findViewById(R.id.txtHora);
            verMais = itemView.findViewById(R.id.txtverMais);

        }
    }

}
