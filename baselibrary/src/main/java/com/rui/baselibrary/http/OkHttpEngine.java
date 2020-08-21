package com.rui.baselibrary.http;


import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Time: 2020/5/27
 * Author: jianrui
 * Description: okHttp引擎
 */
public class OkHttpEngine implements IHttpEngine{


    @Override
    public void get(String url, boolean cache,Map<String, Object> params, final EngineCallback engineCallback) {
        OkHttpClient okHttpClient=new OkHttpClient();
        //添加公共参数
        for(Map.Entry<String, Object> entry:params.entrySet()){
            if(url.endsWith("?")){
                url=url+entry.getKey()+"="+entry.getValue();
            }else{
                url=url+"&"+entry.getKey()+"="+entry.getValue();
            }
        }
        Log.i("test","jianrui url:"+url);
        //判断是否需要缓存
        if(cache){
            //看下是否有缓存，从数据库拿缓存,数据库缓存在frameLibrary

        }else{


        }
        Request.Builder requestBuilder = new Request.Builder().url(url);
        Request request = requestBuilder.build();
        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call,IOException e) {
                        engineCallback.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String resultJson = response.body().string();
                        engineCallback.onSuccess(resultJson);
                    }
                });

    }

    @Override
    public void post(String url, boolean cache, Map<String, Object> params, EngineCallback engineCallback) {
        OkHttpClient okHttpClient=new OkHttpClient();
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        RequestBody body=RequestBody.create(mediaType,"");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });

    }

}
