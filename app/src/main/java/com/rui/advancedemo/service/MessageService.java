package com.rui.advancedemo.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 *
 */
public class MessageService extends Service {

    private int message_id=1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //提高进程优先级
        startForeground(message_id,new Notification());
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }



}
