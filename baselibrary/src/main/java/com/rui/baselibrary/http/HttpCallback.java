package com.rui.baselibrary.http;

import android.content.Context;

import com.google.gson.Gson;
import com.rui.framelibrary.http.EngineCallback;

import java.util.Map;

/**
 * Time: 2020/5/27
 * Author: jianrui
 * Description: http 请求callback
 */
public abstract class HttpCallback<T> implements EngineCallback {

    @Override
    public void onPreExecute(Context context, Map<String, Object> params) {
        //添加公共参数
        params.put("platform","android");

        onPreExecute();
    }

    public void onPreExecute(){

    }

    @Override
    public void onSuccess(String result) {
        Gson gson=new Gson();
        T objResult= (T) gson.fromJson(result, HttpUtils.getClazzInfo(this));
        onSuccess(objResult);
    }

    //返回可以直接操作的对象
    public abstract void onSuccess(T result);
}
