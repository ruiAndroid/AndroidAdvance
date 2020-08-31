package com.rui.framelibrary.skin.attr;

import android.content.res.ColorStateList;
import android.view.View;

import com.rui.framelibrary.skin.SkinManager;
import com.rui.framelibrary.skin.SkinResource;

/**
 * Time: 2020/8/29
 * Author: jianrui
 * Description: 皮肤类型
 */
public enum  SkinType {

    TEXT_COLOR("textColor"){
        @Override
        public void skin(View view, String resName) {
            SkinResource skinResource = getSkinResource();
            ColorStateList colorStateList = skinResource.getColorByName(resName);
            if(colorStateList==null){
                return;
            }


        }
    },BACKGROUND("background"){
        @Override
        public void skin(View view, String resName) {

        }
    },SRC("src"){
        @Override
        public void skin(View view, String resName) {

        }
    };


    //会根据名字调用对应的方法
    private String mResName;

    SkinType(String resName){
        this.mResName=resName;
    }

    public String getResName() {
        return mResName;
    }

    public abstract void skin(View view, String resName);

    public SkinResource getSkinResource(){
        return SkinManager.getInstance().getSkinResource();
    }

}
