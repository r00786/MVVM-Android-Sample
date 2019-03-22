package com.rohitsharma.moviesnew.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.rohitsharma.moviesnew.R;
import com.rohitsharma.moviesnew.activities.movies.pojos.response.Datum;
import com.rohitsharma.moviesnew.utility.Constants;
import com.rohitsharma.moviesnew.utility.SharedPreferencesHelper;

import io.realm.Realm;

public class ClearCacheService extends IntentService {

    public ClearCacheService() {
        super("Clear Cache");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.delete(Datum.class));
        SharedPreferencesHelper sp = SharedPreferencesHelper.getInstance(getApplicationContext(), Constants.PREF_KEY);
        sp.putBoolean(Constants.ClEAR_CACHE, false);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        checkToCreateForegroundNotification();
    }

    public void checkToCreateForegroundNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String NOTIFICATION_CHANNEL_ID = "com.rohitsharma.moviesnew";
            String channelName = "My Background Service";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("App is running in background")
                    .setPriority(NotificationManager.IMPORTANCE_MIN)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
            startForeground(1, notification);
        }

    }
}
