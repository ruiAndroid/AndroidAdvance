package com.rui.framelibrary.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
            TextView textView= (TextView) view;
            textView.setTextColor(colorStateList);

        }
    },BACKGROUND("background"){
        @Override
        public void skin(View view, String resName) {
            //背景可能是图片也可能是颜色
            SkinResource skinResource = getSkinResource();
            Drawable drawableByName = skinResource.getDrawableByName(resName);
            if(drawableByName!=null){
//                ImageView imageView= (ImageView) view;
                view.setBackgroundDrawable(drawableByName);
                return;
            }

            ColorStateList colorStateList = skinResource.getColorByName(resName);
            if(colorStateList!=null){
                view.setBackgroundColor(colorStateList.getDefaultColor());
            }



        }
    },SRC("src"){
        @Override
        public void skin(View view, String resName) {
            SkinResource skinResource = getSkinResource();
            Drawable drawableByName = skinResource.getDrawableByName(resName);
            if(drawableByName!=null){
                ImageView imageView= (ImageView) view;
                imageView.setImageDrawable(drawableByName);

            }

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
