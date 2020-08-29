package com.rui.framelibrary.skin.attr;

import android.view.View;

import java.util.List;

/**
 * Time: 2020/8/29
 * Author: jianrui
 * Description: 皮肤类
 */
public class SkinView {

    private View mView;

    private List<SkinAttr> mSkinAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mView=view;
        this.mSkinAttrs=skinAttrs;
    }

    public void skin(){
        for (SkinAttr skinAttr : mSkinAttrs) {
            skinAttr.skin(mView);
        }
    }

}
