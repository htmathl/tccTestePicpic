package com.example.testepicpic.model;

import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Alimentacao {

    private String alimentos, descricao, tipo , currentId;
    private int dia, mes, ano;

    public Alimentacao() {

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAlimentos() {
        return alimentos;
    }

    public void setAlimentos(String alimentos) {
        this.alimentos = alimentos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void salvarCafe(String dia, String mes, String ano) {

        recuperarUsuario();

        DatabaseReference reference = ConfigFirebase.getFirebase();

        String data = ano + mes + dia;

        reference.child("inserção")
                .child(currentId)
                .child("alimentação")
                .child(data)
                .child("café")
                .setValue(this);

    }

    public void salvarAlmoco(String dia, String mes, String ano) {

        recuperarUsuario();

        DatabaseReference reference = ConfigFirebase.getFirebase();

        String data = ano + mes + dia;

        reference.child("inserção")
                .child(currentId)
                .child("alimentação")
                .child(data)
                .child("almoço")
                .setValue(this);

    }

    public void salvarJanta(String dia, String mes, String ano) {

        recuperarUsuario();

        DatabaseReference reference = ConfigFirebase.getFirebase();

        String data = ano + mes + dia;

        reference.child("inserção")
                .child(currentId)
                .child("alimentação")
                .child(data)
                .child("janta")
                .setValue(this);

    }

    public void salvarLanches(String dia, String mes, String ano) {

        recuperarUsuario();

        DatabaseReference reference = ConfigFirebase.getFirebase();

        String data = ano + mes + dia;

        reference.child("inserção")
                .child(currentId)
                .child("alimentação")
                .child(data)
                .child("lanches")
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
