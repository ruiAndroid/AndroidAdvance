package com.rui.baselibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Time: 2020/4/30
 * Author: jianrui
 * Description: 网络工具类
 */
public class NetUtils {

    public static boolean networkAvailable(Context context){
        try {
            //得到链接管理器对象
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if(activeNetworkInfo!=null && activeNetworkInfo.isConnected()){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
