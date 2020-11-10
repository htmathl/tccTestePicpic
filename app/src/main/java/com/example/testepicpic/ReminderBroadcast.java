package com.example.testepicpic;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.testepicpic.activity.MainActivity;


public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Uri uriSom = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Intent intent2 = new Intent (context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, intent2, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "fcm_default_channel")
                .setContentTitle("Tomar Awinha")
                .setContentText("Já tomou sua água hj?")
                .setSmallIcon(R.drawable.ic_agua2)
                .setSound(uriSom)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(0, notification.build());

    }
}
