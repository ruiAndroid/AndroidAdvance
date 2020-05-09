package com.rui.baselibrary.utils;

import android.content.Context;
import android.os.FileUriExposedException;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;

/**
 * Time: 2020/5/9
 * Author: jianrui
 * Description: 热修复工具类
 */
public class FixDexManager {

    private static final String TAG = "FixDexManager";
    private Context mContext;

    private File mDexDir;

    public FixDexManager(Context context) {
        this.mContext=context;
        this.mDexDir=this.mContext.getDir("odex",Context.MODE_PRIVATE);
    }


    /**
     * 修复dex包
     * @param fileDexPath dex路径
     */
    public void fixDex(String fileDexPath) throws NoSuchFieldException, IllegalAccessException, FileNotFoundException {

        //1.获取已经运行的dexElements (BaseDexClassLoader-->dexPathList-->dexElements)
        ClassLoader applicationClassloader = mContext.getClassLoader();

        Object dexElements=getDexElementsFromClassLoader(applicationClassloader);

        //2.获取下载好的dexElements
        //2.1移动到系统能访问的路径
        File srcFile=new File(fileDexPath);
        if(!srcFile.exists()){
            throw new FileNotFoundException(fileDexPath);
        }

        File destFile=new File(mDexDir,srcFile.getName());
        if(destFile.exists()){
            Log.i(TAG,"path 【"+fileDexPath+"】+has be loaded");
            return;
        }

        //destFile挂载路径
        File optimizedDirectory=new File(mDexDir,"odex");
        if(!optimizedDirectory.exists()){
            optimizedDirectory.mkdirs();
        }

        FileUtils.copyFile(srcFile,destFile);
        //2.2 classloader获取fixFile路径
        List<File> dexFlies=new ArrayList<>();
        dexFlies.add(destFile);

        //修复
        for(File dexFile:dexFlies){
            ClassLoader dexClassLoader=new BaseDexClassLoader(
                    dexFile.getAbsolutePath(),  //dex路径
                    optimizedDirectory, //dex挂载路径
                    null,   //so文件位置
                    applicationClassloader
            );

            Object fixDexElement=getDexElementsFromClassLoader(dexClassLoader);

            //3.把补丁插到已经运行的dexElement最前面
            //合并数组
            Object applicationDexElement = combineArray(fixDexElement, dexElements);

            //4.注入到原来的类 applicationClassLoader中
            injectDexElement(applicationClassloader,applicationDexElement );
        }


    }


    /**
     * 将合并后的dexElement注入到classloader中
     * @param applicationClassloader
     * @param applicationDexElement
     */
    private void injectDexElement(ClassLoader applicationClassloader, Object applicationDexElement) throws NoSuchFieldException, IllegalAccessException {
        Field dexElement = (Field) getDexElementsFromClassLoader(applicationClassloader);
        dexElement.set(applicationClassloader,applicationDexElement);

    }

    /**
     * 通过反射从classloader中拿到dexElements
     * @param applicationClassloader
     * @return
     */
    private Object getDexElementsFromClassLoader(ClassLoader applicationClassloader) throws NoSuchFieldException, IllegalAccessException {
        //通过反射拿到dexPathlist
        Field pathListField = BaseDexClassLoader.class.getField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(applicationClassloader);

        //从dexPathList中拿到dexElements
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        return dexElementsField;
    }


    /**
     * 合并数组
     * @return
     */
    private static Object combineArray(Object arrayLhs,Object arrayRhs){
        Class<?> localClass=arrayLhs.getClass().getComponentType();
        int i= Array.getLength(arrayLhs);
        int j=i+Array.getLength(arrayRhs);

        Object result = Array.newInstance(localClass, j);

        for(int k=0;k<j;++k){
            if(k<i){
                Array.set(result,k,Array.get(arrayLhs,k));
            }else{
                Array.set(result,k,Array.get(arrayRhs,k-i));

            }

        }
        return result;

    }
}
