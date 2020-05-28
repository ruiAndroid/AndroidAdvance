package com.rui.framelibrary.http;

import android.content.Context;

import com.rui.baselibrary.http.EngineCallback;

import java.util.Map;

/**
 * Time: 2020/5/27
 * Author: jianrui
 * Description: http 请求callback
 */
public abstract class HttpCallback implements EngineCallback {

    @Override
    public void onPreExecute(Context context, Map<String, Object> params) {
        //添加公共参数
        params.put("platform","android");

        onPreExecute();
    }

    public void onPreExecute(){

    }
}
