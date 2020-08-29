package com.rui.framelibrary.skin.attr;

import android.view.View;

/**
 * Time: 2020/8/29
 * Author: jianrui
 * Description: 皮肤类型
 */
public enum  SkinType {

    TEXT_COLOR("textColor"){
        @Override
        public void skin(View view, String resName) {

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

}
