package com.rui.baselibrary.http;

import java.util.Map;

/**
 * Time: 2020/5/27
 * Author: jianrui
 * Description: 抽象的Http engine类
 */
public interface IHttpEngine {
    //get请求
    void get(String url, boolean cache,Map<String,Object> params,EngineCallback engineCallback);
    //post 请求
    void post(String url, boolean cache,Map<String,Object> params,EngineCallback engineCallback);

}
