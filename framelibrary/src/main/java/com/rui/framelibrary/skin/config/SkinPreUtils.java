package com.rui.framelibrary.skin.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Time: 2020/9/11
 * Author: jianrui
 * Description: 前皮肤utils
 */
public class SkinPreUtils {

    private  static Context mContext;


    private volatile static SkinPreUtils mInstance;

    public SkinPreUtils(Context context) {
        mContext=context.getApplicationContext();
    }

    public static SkinPreUtils getInstance(Context context){

        if(mInstance==null){
            synchronized (SkinPreUtils.class){
                if(mInstance==null){
                    mInstance=new SkinPreUtils(context);
                }
            }
        }
        return mInstance;
    }


    /**
     * 保存皮肤路径
     * @param skinPath
     */
    public void saveSkinPath(String skinPath){
        mContext.getSharedPreferences(SkinConfig.SKIN_INFO_NAME,Context.MODE_PRIVATE)
                .edit()
                .putString(SkinConfig.SKIN_PATH_NAME,skinPath)
                .commit();

    }

    /**
     * 获取皮肤路径
     * @return
     */
    public String getSkinPath(){
        String path=mContext.getSharedPreferences(SkinConfig.SKIN_INFO_NAME,Context.MODE_PRIVATE)
                .getString(SkinConfig.SKIN_PATH_NAME,"");
        return  path;
    }

    /**
     * 清空皮肤信息
     */
    public void clearSkin() {
        mContext.getSharedPreferences(SkinConfig.SKIN_INFO_NAME, Context.MODE_PRIVATE)
        .edit()
        .clear();
    }
}
