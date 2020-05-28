package com.rui.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * Time: 2020/5/27
 * Author: jianrui
 * Description: http引擎的回调
 */
public interface EngineCallback {

    //执行之前回调的方法
    public void onPreExecute(Context context, Map<String,Object> params);

    public void onError(Exception e);

    public void onSuccess(String result);

    public final EngineCallback DEFAULT_CALL=new EngineCallback() {
        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };
}
