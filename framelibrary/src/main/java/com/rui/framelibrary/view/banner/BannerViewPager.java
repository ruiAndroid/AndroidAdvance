package com.rui.framelibrary.view.banner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义banner 轮播图
 *
 */
public class BannerViewPager extends ViewPager {

    private Context mContext;

//    private BannerAdapter mAdapter;

    public BannerViewPager(@NonNull Context context) {
        this(context,null);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        init();
    }
//
//    public void setAdapter(BannerAdapter adapter) {
//        this.mAdapter = adapter;
//        setAdapter(new BannerPagerAdapter());
//    }

    /**
     * 初始化
     */
    private void init() {

    }


    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // 返回一个很大的值，确保可以无限轮播
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            // 这么写就对了，看了源码应该就明白
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

//            View bannerView = mAdapter.getView(position);
//            container.addView(bannerView );
//            return bannerView;
            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // 销毁回调的方法  移除页面即可
            container.removeView((View) object);
        }
    }

}
