package com.rui.baselibrary.exception;

import android.content.Context;

/**
 * Time: 2020/5/8
 * Author: jianrui
 * Description:全局异常处理类
 * 单例模式
 */
public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler{

    private volatile static ExceptionCrashHandler mInstance;

    private Context mContext;

    //系统默认异常
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    private ExceptionCrashHandler() {

    }

    public void init(Context context){
        this.mContext=context;
        //设置全局的异常类为本类
        Thread.currentThread().setUncaughtExceptionHandler(this);
        mDefaultUncaughtExceptionHandler=Thread.currentThread().getDefaultUncaughtExceptionHandler();
    }

    public static ExceptionCrashHandler getInstance(){
        if(mInstance==null){
            synchronized (ExceptionCrashHandler.class){
                if(mInstance==null){
                    mInstance=new ExceptionCrashHandler();
                }
            }
        }
        return mInstance;
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //全局异常

        //1.崩溃详细信息

        //2.应用信息,手机信息等

        //3.log上传

        //系统默认处理下
        mDefaultUncaughtExceptionHandler.uncaughtException(t,e);

    }


}
