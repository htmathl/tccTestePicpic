package com.example.testepicpic.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigFirebase {

    private static FirebaseAuth autenticacao;
    private static DatabaseReference firebase;

    //retorna a instância do FirebaseDatabase

    public static DatabaseReference getFirebase() {
        if(firebase == null) {
            firebase = FirebaseDatabase.getInstance().getReference();
        }
        return firebase;
    }

    //retorna instância do FirebaseAuth
    public static FirebaseAuth getFirebaseAutenticacao() {
        if(autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return  autenticacao;
    }
}
