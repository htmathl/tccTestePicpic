package com.example.testepicpic.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validarpermisoes(String[] permissoes, Activity activity , int requestCode) {
        if(Build.VERSION.SDK_INT >=23) {
            List <String> listapermissoes = new ArrayList<>();
            for(String permissao : permissoes) {
                Boolean temPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                if (!temPermissao) listapermissoes.add(permissao);
            }
            //caso a lista esteja vazia
            if(listapermissoes.isEmpty()) return true;
            String[] novaspermissoes = new String[listapermissoes.size()];
            listapermissoes.toArray(novaspermissoes);

            //solicitar permissao
            ActivityCompat.requestPermissions( activity,novaspermissoes,requestCode);
        }
        return true;
    }
}
