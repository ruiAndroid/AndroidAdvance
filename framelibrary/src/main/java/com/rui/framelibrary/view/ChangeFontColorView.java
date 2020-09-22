package com.rui.framelibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Time: 2020/9/22
 * Author: jianrui
 * Description: 自定义变色View
 */
public class ChangeFontColorView extends View {
    private static final String TEXT_TEST="测试test";
    private static final String TAG = "ChangeFontColorView";

    //原始画笔
    private Paint mOriginPaint;

    //彩色画笔
    private Paint mColorPaint;

    //文本测量对象
    private Paint.FontMetrics mFontMetrics;

    //绘制文本的基线坐标
    private int baseX,baseY;

    private float rate= 0.1f;

    public ChangeFontColorView(Context context) {
        this(context,null);
    }

    public ChangeFontColorView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ChangeFontColorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {

        mOriginPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mOriginPaint.setTextSize(50);
        mOriginPaint.setColor(Color.parseColor("#000000"));

        mColorPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorPaint.setTextSize(50);
        mColorPaint.setColor(Color.parseColor("#0094ff"));

        mFontMetrics=mOriginPaint.getFontMetrics();

        Log.i(TAG, "ascent：" + mFontMetrics.ascent);
        Log.i(TAG, "top：" + mFontMetrics.top);
        Log.i(TAG, "leading：" + mFontMetrics.leading);
        Log.i(TAG, "descent：" + mFontMetrics.descent);
        Log.i(TAG, "bottom：" + mFontMetrics.bottom);

        mHandler.sendEmptyMessageDelayed(1,50);
    }


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                if(rate>=1){
                    mHandler.removeMessages(1);
                    return;
                }
                rate+=0.01;
                invalidate();
                mHandler.sendEmptyMessageDelayed(1,50);
            }
        }

    };

    @Override
    protected void onDraw(Canvas canvas) {
        int middle=(int) (rate*getWidth());
        //绘制左半边
        drawOrigin(canvas,middle);
        drawChange(canvas,middle);
    }

    /**
     * 绘制原始的
     */
    public void drawOrigin(Canvas canvas,int middle){
        drawText(canvas,mOriginPaint,0,middle);
    }

    /**
     * 绘制变色的
     */
    public void drawChange(Canvas canvas,int middle){
        drawText(canvas,mColorPaint,middle,getWidth());
    }

    /**
     * 绘制文本
     * @param canvas
     */
    public void drawText(Canvas canvas,Paint paint,int startX,int endX){
        Log.i(TAG,"startX:"+startX);
        Log.i(TAG,"endX:"+endX);
        canvas.save();


        //计算baseX坐标
        baseX= (int) (canvas.getWidth()/2-mOriginPaint.measureText(TEXT_TEST)/2);
        //计算baseY坐标
        baseY= (int) ((canvas.getHeight()/2)-(mFontMetrics.descent+mFontMetrics.ascent)/2);

        // 截取绘制的内容，待会就只会绘制clipRect设置的参数部分
        canvas.clipRect(startX, 0, endX, getHeight());

        //获取文字范围
        Rect bounds=new Rect();
        mOriginPaint.getTextBounds(TEXT_TEST,0,TEXT_TEST.length(),bounds);

        canvas.drawText(TEXT_TEST,(getMeasuredWidth()/2-bounds.width()/2),baseY,paint);

        canvas.restore();

    }


}
