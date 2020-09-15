package com.rui.framelibrary.skin;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.TextureView;

import com.rui.framelibrary.skin.attr.SkinView;
import com.rui.framelibrary.skin.config.SkinPreUtils;

import java.io.File;
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
        //防止皮肤被任意删除，做一些措施
        String skinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        File skinFile=new File(skinPath,"rui.skin");
        //皮肤不存在，清空皮肤
        if(!skinFile.exists()){
            SkinPreUtils.getInstance(mContext).clearSkin();
            return;
        }
        //最好做一下能不能获取到包名
        String skinPackageName = mContext.getPackageManager().getPackageArchiveInfo(skinFile.getAbsolutePath(), PackageManager.GET_ACTIVITIES).packageName;
        if(TextUtils.isEmpty(skinPackageName)){
            SkinPreUtils.getInstance(mContext).clearSkin();
            return;
        }

        //做一下初始化工作
        mSkinResource=new SkinResource(mContext,skinPath);
    }

    /**
     * 加载皮肤
     * @param skinPath
     */
    public int loadSkin(String skinPath) {
        int result =-1;
        Set<Activity> activities = skinViewsMap.keySet();
        for (Activity activity : activities) {
            List<SkinView> skinViews = skinViewsMap.get(activity);
            for (SkinView skinView : skinViews) {
                skinView.skin();
                result=1;
            }
        }
        //保存皮肤状态
        saveSkinStatus(skinPath);
        return result;

    }

    /**
     * 保存皮肤状态
     */
    private void saveSkinStatus(String skinPath) {
        //保存皮肤装填至数据库
        SkinPreUtils.getInstance(this.mContext).saveSkinPath(skinPath);

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

    /**
     * 检查当前是否需要换肤
     */
    public void checkChangeSkin(SkinView skinView) {
        //如果当前有皮肤，也就是保存了皮肤路径，则需要换肤
        String currentSkinPath=SkinPreUtils.getInstance(this.mContext).getSkinPath();
        if(!TextUtils.isEmpty(currentSkinPath)){
            skinView.skin();
        }

    }
}
