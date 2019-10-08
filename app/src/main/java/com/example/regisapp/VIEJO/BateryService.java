package com.example.regisapp.VIEJO;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BateryService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        BatteryBroadcast broadcast = new BatteryBroadcast();
        this.registerReceiver(broadcast, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        return START_STICKY;
    }

}

