package com.rui.advancedemo.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Time: 2020/4/30
 * Author: jianrui
 * Description: viewfinder辅助类
 */
public class ViewFinder {

    private Activity mActivty;
    private View mView;

    public ViewFinder(View view) {
        this.mView=view;
    }


    public ViewFinder(Activity activity) {
        this.mActivty=activity;
    }


    /**
     * 通过id找到view
     * @param id
     */
    public View findViewById(int id){
        return mActivty!=null?mActivty.findViewById(id):mView.findViewById(id);
    }

}
