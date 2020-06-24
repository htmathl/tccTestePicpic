package com.example.testepicpic.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfigFirebase {

    private static FirebaseAuth autenticacao;

    //retorna instância do FirebaseAuth
    public static FirebaseAuth getFirebaseAutenticacao() {
        if(autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return  autenticacao;
    }
}
