package com.rui.baselibrary.utils;

import android.content.Context;

import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

/**
 * Time: 2020/5/9
 * Author: jianrui
 * Description: 热修复工具类
 */
public class FixDexManager {
    private Context mContext;

    public FixDexManager(Context context) {
        this.mContext=context;
    }


    /**
     * 修复dex包
     * @param path
     */
    public void fixDex(String path){

        //1.获取已经运行的dexElements (BaseDexClassLoader-->dexPathList-->dexElements)
        ClassLoader applicationClassloader = mContext.getClassLoader();

        Object dexPathList=getDexElementsFromClassLoader(applicationClassloader);
        //2.获取下载好的dexElements

        //3.将新的dexElement插到前面

    }

    /**
     * 通过反射从classloader中拿到dexElements
     * @param applicationClassloader
     * @return
     */
    private Object getDexElementsFromClassLoader(ClassLoader applicationClassloader) {
        //通过反射拿到dexPathlist
        try {
            Field pathList = BaseDexClassLoader.class.getField("pathList");

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        //从dexPathList中拿到dexElements

        return null;
    }


}
