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

public class MesmoDia extends Service {

    private static final String TAG = "MIAU";

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


    }

}
