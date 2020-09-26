package com.rui.framelibrary.view.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Time: 2020/9/26
 * Author: jianrui
 * Description: banner的圆点指示器
 *
 */
public class DotIndicatorView extends View {

    private Drawable mDrawable;

    public DotIndicatorView(Context context) {
        this(context,null);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mDrawable!=null){
//            mDrawable.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
//            mDrawable.draw(canvas);
            //画圆
            //得到bitmap
            Bitmap bitmap=drawableToBitmap(mDrawable);
            //将bitmap变为圆形
            Bitmap circleBitmap=getCircleBitmap(bitmap);

            canvas.drawBitmap(circleBitmap,0,0,null);

        }
    }

    /**
     * 得到圆形的bitmap
     * @param bitmap
     * @return
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap circleBitmap=Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(circleBitmap);

        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setFilterBitmap(true);

        //在画布上画一个圆
        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,getMeasuredWidth()/2,paint);

        //取圆和矩形bitmap的交集
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //再把原来的bitmap绘制到新的圆上
        canvas.drawBitmap(bitmap,0,0,paint);

        return circleBitmap;
    }

    /**
     * drawable转bitmap
     * @param mDrawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable mDrawable) {
        //如果是bitmapDrawable类型
        if(mDrawable instanceof BitmapDrawable){
            return ((BitmapDrawable) mDrawable).getBitmap();
        }
        //其他类型bitmap
        //先创建一个空bitmap
        Bitmap bitmap= Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //创建一个画布
        Canvas canvas=new Canvas(bitmap);

        //将drawable画到bitmap上
        mDrawable.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
        mDrawable.draw(canvas);

        return bitmap;
    }

    /**
     * 设置drawable
     * @param drawable
     */
    public void setDrawable(Drawable drawable) {
        this.mDrawable=drawable;
        invalidate();

    }
}
