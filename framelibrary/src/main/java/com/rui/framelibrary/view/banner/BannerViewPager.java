package com.rui.framelibrary.view.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.rui.framelibrary.view.banner.adapter.BannerAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * 自定义banner 轮播图
 *
 */
public class BannerViewPager extends ViewPager {

    private static final String TAG = "BannerViewPager";
    private Context mContext;

    private BannerAdapter mAdapter;

    //自动轮播的msg
    private static final int MSG_AUTO_BANNER=0x0001;
    //轮播间隔时间
    private long DELAY_TIME_BANNER=1500;

    //自定义的scroller
    private BannerScroller mBannerScroller;

    //view的复用
    private List<View> mConvertViews;

    //自动轮播的handler
    private Handler mAutoBannerHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            setCurrentItem(getCurrentItem()+1,true);
            startAutoBanner();
        }
    };

    public BannerViewPager(@NonNull Context context) {
        this(context,null);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        init();
    }



    /**
     * 自动轮播功能
     */
    public void startAutoBanner(){
        Log.i(TAG,"startAutoBanner");
        if(mAutoBannerHandler.hasMessages(MSG_AUTO_BANNER)){
            mAutoBannerHandler.removeMessages(MSG_AUTO_BANNER);
        }
        mAutoBannerHandler.sendEmptyMessageDelayed(MSG_AUTO_BANNER,DELAY_TIME_BANNER);
    }

    /**
     * 设置adapter
     * @param adapter
     */
    public void setAdapter(BannerAdapter adapter) {
        this.mAdapter = adapter;
        //设置父类ViewPager的adapter
        setAdapter(new BannerPagerAdapter());
    }

    /**
     * 初始化
     */
    private void init() {
        //改变viewPager的切换速率
        //duration持续时间,通过反射设置
        try {
            Field field=ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            mBannerScroller=new BannerScroller(mContext);
            field.set(this,mBannerScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mConvertViews=new ArrayList<>();

    }

    /**
     * 设置切换页面持续的时间
     * @param mScrollDuration
     */
    public void setScrollDuration(int mScrollDuration) {
        mBannerScroller.setScrollDuration(mScrollDuration);
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.i(TAG,"onDetachedFromWindow");
        if(mAutoBannerHandler!=null){
            if(mAutoBannerHandler.hasMessages(MSG_AUTO_BANNER)){
                mAutoBannerHandler.removeMessages(MSG_AUTO_BANNER);
            }
            mAutoBannerHandler=null;
        }
        super.onDetachedFromWindow();
    }

    /**
     * viewPager 适配器
     */
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
            View bannerView = mAdapter.getView(position%mAdapter.getCount(),getConvertView());
            container.addView(bannerView );
            return bannerView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // 销毁回调的方法  移除页面即可
            container.removeView((View) object);
            mConvertViews.add((View) object);
        }
    }

    /**
     * 获取到复用的view
     * @return
     */
    private View getConvertView() {
        for(int i=0;i<mConvertViews.size();i++){
            //获取没有添加到viewpager里面
            if(mConvertViews.get(i).getParent()==null){
                return mConvertViews.get(i);
            }
        }
        return null;
    }

}
