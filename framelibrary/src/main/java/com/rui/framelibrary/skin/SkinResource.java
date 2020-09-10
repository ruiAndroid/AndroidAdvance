package com.rui.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Time: 2020/8/29
 * Author: jianrui
 * Description: 皮肤的资源类
 */
public class SkinResource {

    //资源通过这个对象来获取
    private Resources mSkinResorce;
    //皮肤包的包名
    private String mSkinPackageName;

    /**
     * 读取本地的一个皮肤资源
     */
    public SkinResource(Context context, String skinPath) {
        //测试换肤
        Resources superRes = context.getResources();

        //创建外部存储皮肤文件的文件夹
        File skinFiles=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator +"rui"+File.separator+"skins");
        if(!skinFiles.exists()){
            skinFiles.mkdirs();
        }

        File skinFile=new File(skinFiles,"rui.skin");

        if(skinFile==null){
            return;
        }
        //通过AssetManager加载自己的资源文件
        try {

            AssetManager assetManager = AssetManager.class.newInstance();

            //低版本api
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager,skinFile.getAbsolutePath());

            //高版本API
            /*
            //通过反射拿到隐藏的AssetsApk类
            @SuppressLint("PrivateApi") Class<?> apkAssetsClass = Class.forName("android.content.res.ApkAssets");
            //调用AssetsApk类的loadFromPath方法加载本地资源
            @SuppressLint("SoonBlockedPrivateApi") Method loadFromPathMethod = apkAssetsClass.getDeclaredMethod("loadFromPath",String.class);
            Object apkAsset = loadFromPathMethod.invoke(apkAssetsClass,skinFile.getAbsolutePath());

            ArrayList<Object> apkAssets = new ArrayList<>();
            apkAssets.add(apkAsset);
            Object[] apkAssetsArray = apkAssets.toArray();

            //通过反射拿到AssetManager类的setApkAssets方法
            Method declaredMethod = AssetManager.class.getDeclaredMethod("setApkAssets",Object[].class,Boolean.class);
            //反射调用setApkAssets设置apkAssets
            declaredMethod.invoke(assetManager, apkAssetsArray, false);*/

            mSkinResorce=new Resources(assetManager,superRes.getDisplayMetrics(),superRes.getConfiguration());

            //获取到皮肤的包名
            mSkinPackageName = context.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过资源Name获取到drawable
     */
    public Drawable getDrawableByName(String resName){
        try {
            int identifier = mSkinResorce.getIdentifier(resName, "drawable", mSkinPackageName);
            return  mSkinResorce.getDrawable(identifier);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 通过资源Name获取到color
     */
    public ColorStateList getColorByName(String resName){
        try {
            int identifier = mSkinResorce.getIdentifier(resName, "drawable", mSkinPackageName);
            return  mSkinResorce.getColorStateList(identifier);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}
