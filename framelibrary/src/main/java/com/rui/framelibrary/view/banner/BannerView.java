package com.rui.framelibrary.view.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
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

    private RelativeLayout mDotBottom;

    //点指示器选中的drawable
    private Drawable mFocusDotIndicatorDrawable;
    //默认的点指示的drawable
    private Drawable mNormalDotIndicatorDrawable;
    //底部指示器背景色
    private Drawable mDotBgDrawable;

    private BannerAdapter mBannerAdapter;

    private Context mContext;

    //当前item的位置
    private int mCurrentPosition =0;


    //各种自定义属性值
    //点的大小 默认8dp
    private int mDotSize=8;
    //点之间的间距
    private int mDotDistance=8;

    //点的位置 center:0 left:-1 right:1
    private int mDotLocation=1;

    //宽高比
    private float mWidthRate=8;
    private float mHeightRate=3;

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
        initAttrs(attrs);
        initView();
    }

    /**
     * 初始化自定义属性
     */
    private void initAttrs(AttributeSet attributeSet) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attributeSet, R.styleable.BannerView);
        if(typedArray!=null){
            mDotLocation=typedArray.getInt(R.styleable.BannerView_dotLocation,mDotLocation);

            mDotBgDrawable=typedArray.getDrawable(R.styleable.BannerView_bottomBgColor);
            if(mDotBgDrawable==null){
                mDotBgDrawable=new ColorDrawable(Color.GRAY);
            }
            mFocusDotIndicatorDrawable=typedArray.getDrawable(R.styleable.BannerView_dotFocusColor);
            if(mFocusDotIndicatorDrawable==null){
                mFocusDotIndicatorDrawable=new ColorDrawable(Color.RED);
            }

            mNormalDotIndicatorDrawable=typedArray.getDrawable(R.styleable.BannerView_dotNormalColor);
            if(mNormalDotIndicatorDrawable==null){
                mNormalDotIndicatorDrawable=new ColorDrawable(Color.GRAY);
            }
            mDotSize= (int) typedArray.getDimension(R.styleable.BannerView_dotSize,DeviceUtils.dip2px(mContext,mDotSize));
            mDotDistance= (int) typedArray.getDimension(R.styleable.BannerView_dotDistance,DeviceUtils.dip2px(mContext,mDotDistance));

            mWidthRate=typedArray.getFloat(R.styleable.BannerView_widthRate,mWidthRate);
            mHeightRate=typedArray.getFloat(R.styleable.BannerView_heightRate,mHeightRate);
            typedArray.recycle();
        }

    }

    /**
     * 初始化view
     */
    private void initView() {
        mBannerViewPager=findViewById(R.id.banner_viewpager);
        mTvDescription=findViewById(R.id.tv_description);
        mDotContainer=findViewById(R.id.ll_dot_container);
        mDotBottom=findViewById(R.id.ll_dot_bottom);

        mDotBottom.setBackgroundDrawable(mDotBgDrawable);
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
        pageSelected(mCurrentPosition);

        if(mWidthRate==0 || mHeightRate==0){
            return;
        }

        //动态指定宽高
        post(new Runnable() {
            @Override
            public void run() {
                int width=getMeasuredWidth();
                //指定宽高
                getLayoutParams().height=(int) (width*mHeightRate/mWidthRate);
            }
        });
    }

    /**
     * 页面切换的回调
     * @param position
     */
    private void pageSelected(int position) {
        //改变dot的状态以及文本状态
        DotIndicatorView oldDotIndicatorView= (DotIndicatorView) mDotContainer.getChildAt(mCurrentPosition);
        oldDotIndicatorView.setDrawable(mNormalDotIndicatorDrawable);

        mCurrentPosition =position%mBannerAdapter.getCount();
        DotIndicatorView currentDotIndicatorView= (DotIndicatorView) mDotContainer.getChildAt(mCurrentPosition);
        currentDotIndicatorView.setDrawable(mFocusDotIndicatorDrawable);


    }

    /**
     * 初始化点指示器
     */
    private void initDot() {
        int dotCount=mBannerAdapter.getCount();
        mDotContainer.removeAllViews();
        mDotContainer.setGravity(getDotLocation());
        for (int i=0;i<dotCount;i++){
            DotIndicatorView dotIndicatorView=new DotIndicatorView(this.mContext);
            //设置大小
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(mDotSize,mDotSize);
            //设置左右间距
            layoutParams.leftMargin=mDotDistance;
            dotIndicatorView.setLayoutParams(layoutParams);
            if(i==0){   //默认选中位置
                dotIndicatorView.setDrawable(mFocusDotIndicatorDrawable);
            }else{
                dotIndicatorView.setDrawable(mNormalDotIndicatorDrawable);
            }
            mDotContainer.addView(dotIndicatorView);
        }

    }

    /**
     * 获取点的位置
     * @return
     */
    public int getDotLocation() {
        switch (mDotLocation){
            case -1:
               return Gravity.LEFT;
            case 0:
                return Gravity.CENTER;
            case 1:
                return Gravity.RIGHT;
            default:
                return Gravity.RIGHT;
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
