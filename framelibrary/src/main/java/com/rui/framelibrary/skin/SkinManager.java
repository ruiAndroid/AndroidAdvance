package com.rui.framelibrary.skin;

import android.app.Activity;
import android.content.Context;

import com.rui.framelibrary.skin.attr.SkinView;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Time: 2020/8/29
 * Author: jianrui
 * Description: 皮肤管理类
 */
public class SkinManager {

    private  static  SkinManager mInstance;

    private Context mContext;

    //存放 activity 对应的皮肤view集合
    private HashMap<Activity, List<SkinView>> skinViewsMap=new HashMap<>();

    private SkinResource mSkinResource;

    static {
        mInstance=new SkinManager();
    }

    public static SkinManager getInstance() {
        return mInstance;
    }

    public void init(Context context){
        this.mContext=context.getApplicationContext();
    }

    /**
     * 加载皮肤
     * @param skinPath
     */
    public int loadSkin(String skinPath) {
        int result =-1;
        mSkinResource=new SkinResource(mContext,skinPath);
        Set<Activity> activities = skinViewsMap.keySet();
        for (Activity activity : activities) {
            List<SkinView> skinViews = skinViewsMap.get(activity);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }
        }
        return result;

    }

    /**
     * 还原默认皮肤
     */
    public void restoreSkin() {


    }

    /**
     * 获取activity 下对应的skinViews
     * @param activity
     * @return
     */
    public  List<SkinView> getSkinViews(Activity activity) {
        return skinViewsMap.get(activity);
    }

    /**
     * 注册一个skin
     * @param skinViews
     */
    public void register(Activity activity,List<SkinView> skinViews) throws Exception {
        if(!skinViewsMap.containsKey(activity)){
            this.skinViewsMap.put(activity,skinViews);
        }else{
            throw new Exception("this activity has registed already");
        }
    }

    /**
     * 获取当前的SkinResource
     * @return
     */
    public SkinResource getSkinResource() {
        return mSkinResource;
    }
}
