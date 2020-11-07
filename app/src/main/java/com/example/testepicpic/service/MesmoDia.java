package com.example.testepicpic.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.testepicpic.activity.MainActivity;
import com.example.testepicpic.broadcast.DayChangedBroadcastReceiver;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

public class MesmoDia extends Service {

    private static final String TAG = "MIAU";

    int pYear, pMonth, pDay;

    DatabaseReference ref;

    String currentId;

    FirebaseAuth auth;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "miaumiau");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pYear = Calendar.getInstance().get(Calendar.YEAR);
        pMonth = (Calendar.getInstance().get(Calendar.MONTH)+1);
        pDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        ref = ConfigFirebase.getFirebase();

        ref.child("data_antiga")
                .child("ano")
                .setValue(pYear);

        ref.child("data_antiga")
                .child("mes")
                .setValue(pMonth);

        ref.child("data_antiga")
                .child("dia")
                .setValue(pDay);

        Log.e(TAG, String.valueOf(pDay));

    }

}
