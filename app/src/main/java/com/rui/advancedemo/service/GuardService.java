package com.rui.advancedemo.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.rui.advancedemo.ProcessConnection;

/**
 * 守护进程
 */
public class GuardService extends Service {

    private int guard_id=1;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(guard_id,new Notification());
        //绑定建立连接
        bindService(new Intent(this,MessageService.class),serviceConnection,BIND_AUTO_CREATE);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub(){

        };
    }

    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
