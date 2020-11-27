package com.example.testepicpic.model;

import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Ajustes {
    private String tratamento, tipo, currentId;
    private int ano;
    private double hiper, normal, hipo;

    public String getTratamento() {
        return tratamento;
    }

    public void setTratamento(String tratamento) {
        this.tratamento = tratamento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getHiper() {
        return hiper;
    }

    public void setHiper(double hiper) {
        this.hiper = hiper;
    }

    public double getNormal() {
        return normal;
    }

    public void setNormal(double normal) {
        this.normal = normal;
    }

    public double getHipo() {
        return hipo;
    }

    public void setHipo(double hipo) {
        this.hipo = hipo;
    }

    public void salvar(){
        recuperarUsurario();
        DatabaseReference reference = ConfigFirebase.getFirebase();

        reference.child("ajustes")
                .child(currentId)
                .setValue(this);

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
