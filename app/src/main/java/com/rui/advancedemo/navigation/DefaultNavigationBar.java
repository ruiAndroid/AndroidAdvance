package com.rui.advancedemo.navigation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.rui.advancedemo.R;
import com.rui.baselibrary.navigationbar.AbsNavigationBar;

/**
 * Time: 2020/5/23
 * Author: jianrui
 * Description:默认的navigationBar
 */
public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationBarParams> {


    public DefaultNavigationBar(Builder.DefaultNavigationBarParams params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.layout_navigation;
    }

    @Override
    public void applyView() {
        setText(R.id.title,getParams().title);
    }


    public static class Builder extends AbsNavigationBar.Builder{

        private DefaultNavigationBarParams defaultNavigationBarParams;


        /**
         * 设置标题
         * @param title
         * @return
         */
        public Builder setTitle(String title){
            defaultNavigationBarParams.title=title;
            return this;
        }

        /**
         * 设置左边的点击事件
         * @return
         */
        public Builder setLeftClickListener(View.OnClickListener leftClickListener){
            defaultNavigationBarParams.leftClickListener=leftClickListener;
            return this;
        }

        public Builder(Context context, ViewGroup viewGroup) {
            defaultNavigationBarParams=new DefaultNavigationBarParams(context,viewGroup);
        }

        @Override
        public DefaultNavigationBar build() {
            DefaultNavigationBar defaultNavigationBar=new DefaultNavigationBar(defaultNavigationBarParams);
            return defaultNavigationBar;
        }



        public static class DefaultNavigationBarParams extends AbsNavigationParams{
            //标题
            private String title;
            //左边的点击事件
            private View.OnClickListener leftClickListener;

            public DefaultNavigationBarParams(Context context, ViewGroup viewGroup) {
                super(context, viewGroup);
            }
        }
    }
}
