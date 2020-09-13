package com.example.testepicpic.model;

import com.google.firebase.database.DatabaseReference;
import com.example.testepicpic.config.ConfigFirebase;
import com.google.firebase.database.Exclude;

public class Usuario {

    private String idUser;
    private String email;
    private String senha;
    private String nome;
    private int idade;
    private double altura;
    private double peso;
    private String genero;
    private String tipoDiabetes;
    private boolean utilizoInsulina;
    private boolean utilizoMedicacao;
    private String[] medicacao;
    private boolean[] lembretes;

    @Exclude
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }


    public String getTipoDiabetes() {
        return tipoDiabetes;
    }

    public void setTipoDiabetes(String tipoDiabetes) {
        this.tipoDiabetes = tipoDiabetes;
    }

    public boolean isUtilizoInsulina() {
        return utilizoInsulina;
    }

    public void setUtilizoInsulina(boolean utilizoInsulina) {
        this.utilizoInsulina = utilizoInsulina;
    }

    public boolean isUtilizoMedicacao() {
        return utilizoMedicacao;
    }

    public void setUtilizoMedicacao(boolean utilizoMedicacao) {
        this.utilizoMedicacao = utilizoMedicacao;
    }

    /*
    public String[] getMedicacao() {
        return medicacao;
    }

    public void setMedicacao(String[] medicacao) {
        this.medicacao = medicacao;
    }

    public boolean[] getLembretes() {
        return lembretes;
    }

    public void setLembretes(boolean[] lembretes) {
        this.lembretes = lembretes;
    }*/

    public Usuario() {
    }

    public void salvar(){
        DatabaseReference firebase = ConfigFirebase.getFirebase();
        firebase.child("users")
                .child(this.idUser)
                .setValue(this);
    }

}
