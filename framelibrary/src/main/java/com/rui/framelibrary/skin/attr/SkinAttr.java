package com.rui.framelibrary.skin.attr;

import android.view.View;

/**
 * Time: 2020/8/29
 * Author: jianrui
 * Description: 皮肤的属性类
 */
public class SkinAttr {

    private String mResName;

    private SkinType mType;

    public SkinAttr(String resName, SkinType skinType) {
        this.mResName=resName;
        this.mType=skinType;
    }

    public void skin(View view) {
        mType.skin(view,mResName);
    }


}
