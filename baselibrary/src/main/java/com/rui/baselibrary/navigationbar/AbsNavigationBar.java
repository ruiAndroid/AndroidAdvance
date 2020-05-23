package com.rui.baselibrary.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Time: 2020/5/19
 * Author: jianrui
 * Description: 导航栏 build设计模式
 */
public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INavigationBar{

    private P params;

    private View view;
    public AbsNavigationBar(P params) {
        this.params=params;
        createAndBindView();
    }

    /**
     * 创建和绑定布局
     */
    private void createAndBindView(){
        if(params.mViewGroup==null){
            //获取到decorView
            ViewGroup decorView = (ViewGroup) ((Activity) params.mContext).getWindow().getDecorView();

            View childAt = decorView.getChildAt(0);

            params.mViewGroup= (ViewGroup) childAt;
        }
        view =LayoutInflater.from(params.mContext)
                .inflate(bindLayoutId(),params.mViewGroup,false);
        //添加到头部

        params.mViewGroup.addView(view,0);

        applyView();
    }

    /**
     * 设置text
     * @param id
     * @param title
     */
    protected void setText(int id,String title){
        TextView textView= (TextView) findViewById(id);
        textView.setText(title);
    }


    public <T extends View>View findViewById(int id){
        return view.findViewById(id);
    }

    public P getParams() {
        return params;
    }

    public abstract static class Builder{

        //构建导航条方法
        public abstract AbsNavigationBar build();
        //默认配置参数
        public static class AbsNavigationParams {
            Context mContext;
            ViewGroup mViewGroup;

            public AbsNavigationParams(Context context, ViewGroup viewGroup) {
                this.mContext=context;
                this.mViewGroup=viewGroup;
            }

        }

    }

}
