package com.rui.baselibrary.base;

import android.app.Application;

import com.rui.baselibrary.exception.ExceptionCrashHandler;

/**
 * Time: 2020/5/8
 * Author: jianrui
 * Description: base application
 */
public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        ExceptionCrashHandler.getInstance().init(this);

    }
}
