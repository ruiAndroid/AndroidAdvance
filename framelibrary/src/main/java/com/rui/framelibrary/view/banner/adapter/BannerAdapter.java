package com.rui.framelibrary.view.banner.adapter;

import android.view.View;

/**
 * Time: 2020/9/24
 * Author: jianrui
 * Description: Banner adapter
 */
public abstract class BannerAdapter {


    //根据位置获取viewPager的子view
    public abstract  View getView(int position,View convertView);

    //获取轮播的数量
    public abstract int getCount();

    //获取广告位描述
    public abstract String getBannerDesc();
}
