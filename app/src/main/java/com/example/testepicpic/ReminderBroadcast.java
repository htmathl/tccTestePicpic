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


public class ReminderBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Uri uriSom = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "fcm_default_channel")
                .setContentTitle("miau?")
                .setContentText("miau!")
                .setSmallIcon(R.drawable.ic_agua)
                .setSound(uriSom)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(0, notification.build());
    }
    //dAvojFw9TnGrkIleb0K942:APA91bH9DnTABbWHn5VwsTQVS5laiLOZmwW_ozuMpmaIiId6Cm6zZL65JHf4VFOemnANHrLGxQIJ-Gfw6q8xM0-4G1Z1ivCGD-l4GmgardVcmOrlwiwTMSOfg3kujTsGUh6QNx7S7Q_s
    //dRFQmnWaTviLzobQdbGYRQ:APA91bG3CUMZe-XanGJv1OHxgvZNT2fW_cDttSQT-d55oR8f6qGZVSIvhNNlVMVQIta21mjYMLt360OOjMei1Wmzk1IsmMyCJnbDCfVBpL361B-_1n9IgZyuWLDoPYqghFk0YwXa7zgy
}
