package com.example.testepicpic.helper;

import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.testepicpic.activity.AjustesActivity;
import com.example.testepicpic.fragment.ConfigPerfilFragment;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validarpermisoes(String[] permissoes, ConfigPerfilFragment fragment, int requestCode) {
        if(Build.VERSION.SDK_INT >=23) {
            List <String> listapermissoes = new ArrayList<>();
            for(String permissao : permissoes) {
                Boolean temPermissao = ContextCompat.checkSelfPermission(fragment, permissao) == PackageManager.PERMISSION_GRANTED;
            if (!temPermissao) listapermissoes.add(permissao);
            }
            //caso a lista esteja vazia
            if(listapermissoes.isEmpty()) return true;
            String[] novaspermissoes = new String[listapermissoes.size()];
            listapermissoes.toArray(novaspermissoes);

            //solicitar permissao
            ActivityCompat.requestPermissions(fragment,novaspermissoes,requestCode);
        }
        return true;
    }
}
