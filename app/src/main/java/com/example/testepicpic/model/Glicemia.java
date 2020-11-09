package com.example.testepicpic.model;

import android.content.SharedPreferences;

import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.fragment.CalendarFragment;
import com.example.testepicpic.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;


public class Glicemia {

    private double nivel;
    private String lado, local, categoria, currentId;
    private int ano, mes, dia, hora;

    public Glicemia() {}

    public double getNivel() {
        return nivel;
    }

    public void setNivel(double nivel) {
        this.nivel = nivel;
    }

    public String getLado() {
        return lado;
    }

    public void setLado(String lado) {
        this.lado = lado;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public void salvar(String dia, String mes, String ano) {

        recuperarUsuario();

        DatabaseReference reference = ConfigFirebase.getFirebase();

        String data = ano + mes + dia;

        reference.child("inserção")
                .child(currentId)
                .child("glicemia")
                .child(data)
                .push()
                .setValue(this);

    }

    public void recuperarUsuario() {

        FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

        if(auth.getCurrentUser() != null) {

            String email = auth.getCurrentUser().getEmail();
            assert email != null;
            currentId = Base64Custom.codificarBase64(email);
        }

    }

}
