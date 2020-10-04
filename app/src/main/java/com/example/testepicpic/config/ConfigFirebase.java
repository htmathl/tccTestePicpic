package com.example.testepicpic.config;

import android.os.storage.StorageManager;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfigFirebase {

    private static FirebaseAuth autenticacao;
    private static DatabaseReference firebase;
    private static StorageReference storage;


    //retorna a instância do FirebaseDatabase

    public static DatabaseReference getFirebase() {
        if (firebase == null) {
            firebase = FirebaseDatabase.getInstance().getReference();
        }
        return firebase;
    }

    //retorna instância do FirebaseAuth
    public static FirebaseAuth getFirebaseAutenticacao() {
        if (autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;

    }

    public static StorageReference getFirebaseStorage() {
        if (storage == null) {
            storage = FirebaseStorage.getInstance().getReference();
        }
        return storage;
    }

}
