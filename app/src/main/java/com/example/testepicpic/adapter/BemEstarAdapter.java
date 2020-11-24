package com.example.testepicpic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testepicpic.R;
import com.example.testepicpic.model.BemEstar;

import java.util.List;

public class BemEstarAdapter extends RecyclerView.Adapter<BemEstarAdapter.ViewHolder> {

    private List<BemEstar> bemEstars;
    private Context context;

    public BemEstarAdapter(List<BemEstar> bemEstars, Context c) {
        this.bemEstars = bemEstars;
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

        BemEstar bemEstar = bemEstars.get( position );

        holder.humor.setText( bemEstar.getHumor() );
        holder.hora.setText( "Húmor" );
        //holder.verMais.setText( "ver mais" );

    }

    @Override
    public int getItemCount() {
        return bemEstars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView humor, hora, verMais;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            humor = itemView.findViewById(R.id.txtNivel);
            hora = itemView.findViewById(R.id.txtHora);
            verMais = itemView.findViewById(R.id.txtverMais);

        }
    }

}
