package com.rui.baselibrary.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.ArrayMap;

import java.util.Map;

/**
 * Time: 2020/5/27
 * Author: jianrui
 * Description: HttpUtils工具类
 *
 */
@SuppressLint("NewApi")
public class HttpUtils {

    private final static int GET_TYPE=0x0011;

    private final static int POST_TYPE=0x0012;

    private Map<String,Object> mParams;

    private Context mContext;

    private String mUrl;

    //默认使用kHttp引擎
    private IHttpEngine mDefaultEngine=new OkHttpEngine();

    ///默认的请求方式为get
    private int mRequestMethod=GET_TYPE;

    //是否使用缓存
    private boolean mCache;



    public HttpUtils(Context mContext) {
        this.mContext = mContext;
        mParams=new ArrayMap<>();
    }

    public static HttpUtils with(Context context){
        return new HttpUtils(context);
    }

    /**
     * 添加参数
     * @param key
     * @param values
     * @return
     */
    public HttpUtils addParam(String key,Object values){
        mParams.put(key,values);
        return this;
    }

    /**
     * 添加多个参数
     * @param params
     * @return
     */
    public HttpUtils addParams(Map<String,Object> params){
        mParams.putAll(params);
        return this;
    }

    /**
     * post请求
     * @return
     */
    public HttpUtils post(){
        mRequestMethod=POST_TYPE;
        return this;
    }

    /**
     * get请求
     * @return
     */
    public HttpUtils get(){
        mRequestMethod=GET_TYPE;
        return this;
    }

    /**
     * 添加url
     * @return
     */
    public HttpUtils url(String url){
        this.mUrl=url;
        return this;
    }
    /**
     * 添加回调
     */
    public void execute(EngineCallback engineCallback){
        if(engineCallback==null){
            engineCallback=EngineCallback.DEFAULT_CALL;
        }
        engineCallback.onPreExecute(mContext,mParams);
        //判断执行方法
        if(mRequestMethod==POST_TYPE){
            mDefaultEngine.post(mUrl,mParams,engineCallback);
        }else if(mRequestMethod==GET_TYPE){
            mDefaultEngine.get(mUrl,mParams,engineCallback);
        }
    }
}
