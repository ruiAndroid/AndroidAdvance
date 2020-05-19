package com.rui.baselibrary.navigationbar;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Time: 2020/5/19
 * Author: jianrui
 * Description: 导航栏 build设计模式
 */
public class AbsNavigationBar implements INavigationBar{

    private Builder.AbsNavigationParams mAbsNavigationParams;

    public AbsNavigationBar(Builder.AbsNavigationParams absNavigationParams) {
        this.mAbsNavigationParams=absNavigationParams;
        createAndBindView();
    }

    public void createAndBindView(){

    }

    @Override
    public int bindLayoutId() {
        return 0;
    }

    @Override
    public void applyView() {

    }

    public abstract static class Builder{

        AbsNavigationParams P;

        public Builder(Context context, ViewGroup viewGroup) {
            P=new AbsNavigationParams(context,viewGroup);
        }

        public abstract AbsNavigationBar builder();

        public static class AbsNavigationParams {
            private Context mContext;
            private ViewGroup mViewGroup;

            public AbsNavigationParams(Context context, ViewGroup viewGroup) {
                this.mContext=context;
                this.mViewGroup=viewGroup;
            }
        }

    }

}
