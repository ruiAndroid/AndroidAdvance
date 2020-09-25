package com.rui.framelibrary.view.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Time: 2020/9/25
 * Author: jianrui
 * Description: 重写viewpager的Scroller
 */
public class BannerScroller extends Scroller {

    //动画持续的时间
    private int mScrollDuration=850;

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }


    public int getScrollDuration() {
        return mScrollDuration;
    }

    /**
     * 设置切换页面持续的时间
     * @param mScrollDuration
     */
    public void setScrollDuration(int mScrollDuration) {
        this.mScrollDuration = mScrollDuration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }
}
