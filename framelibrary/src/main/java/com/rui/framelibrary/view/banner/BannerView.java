package com.rui.framelibrary.view.banner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rui.framelibrary.R;
import com.rui.framelibrary.utils.DeviceUtils;
import com.rui.framelibrary.view.banner.adapter.BannerAdapter;

/**
 * Time: 2020/9/26
 * Author: jianrui
 * Description: 自定义bannerView,包含点和文字描述
 */
public class BannerView extends RelativeLayout {

    private BannerViewPager mBannerViewPager;
    //文字描述
    private TextView mTvDescription;
    //点指示器layout
    private LinearLayout mDotContainer;

    //点指示器选中的drawable
    private Drawable mFocusDotIndicatorDrawable;
    //默认的点指示的drawable
    private Drawable mNormalDotIndicatorDrawable;

    private BannerAdapter mBannerAdapter;

    private Context mContext;

    //当前item的位置
    private int mCurrentPosotion=0;

    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        inflate(context, R.layout.layout_banner,this);
        initView();

    }

    /**
     * 初始化view
     */
    private void initView() {
        mBannerViewPager=findViewById(R.id.banner_viewpager);
        mTvDescription=findViewById(R.id.tv_description);
        mDotContainer=findViewById(R.id.ll_dot_container);

        mFocusDotIndicatorDrawable=new ColorDrawable(Color.RED);
        mNormalDotIndicatorDrawable=new ColorDrawable(Color.WHITE);

    }

    /**
     * 给viewPager设置适配器
     * @param adapter
     */
    public void setAdapter(BannerAdapter adapter){
        this.mBannerAdapter=adapter;
        mBannerViewPager.setAdapter(adapter);
        //初始化点指示器
        initDot();
        //设置监听
        mBannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                //监听当前选中的位置
                pageSelected(position);

            }
        });
        pageSelected(mCurrentPosotion);
    }

    /**
     * 页面切换的回调
     * @param position
     */
    private void pageSelected(int position) {
        //改变dot的状态以及文本状态
        DotIndicatorView oldDotIndicatorView= (DotIndicatorView) mDotContainer.getChildAt(mCurrentPosotion);
        oldDotIndicatorView.setDrawable(mNormalDotIndicatorDrawable);

        mCurrentPosotion=position%mBannerAdapter.getCount();
        DotIndicatorView currentDotIndicatorView= (DotIndicatorView) mDotContainer.getChildAt(mCurrentPosotion);
        currentDotIndicatorView.setDrawable(mFocusDotIndicatorDrawable);


    }

    /**
     * 初始化点指示器
     */
    private void initDot() {
        int dotCount=mBannerAdapter.getCount();
        mDotContainer.removeAllViews();
        for (int i=0;i<dotCount;i++){
            DotIndicatorView dotIndicatorView=new DotIndicatorView(this.mContext);
            //设置大小
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(DeviceUtils.dip2px(mContext,8),DeviceUtils.dip2px(mContext,8));
            layoutParams.leftMargin=DeviceUtils.dip2px(mContext,2);
            dotIndicatorView.setLayoutParams(layoutParams);
            dotIndicatorView.setBackgroundColor(Color.RED);
            if(i==0){   //默认选中位置
                dotIndicatorView.setDrawable(mFocusDotIndicatorDrawable);
            }else{
                dotIndicatorView.setDrawable(mNormalDotIndicatorDrawable);
            }
            mDotContainer.addView(dotIndicatorView);
        }

    }


    /**
     * 开始轮播
     */
    public void startAutoBanner() {
        mBannerViewPager.startAutoBanner();
    }


    /**
     * 设置滚动时间
     * @param duration
     */
    public void setScrollDuration(int duration) {
        mBannerViewPager.setScrollDuration(duration);

    }
}
