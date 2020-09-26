package com.rui.framelibrary.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Time: 2020/9/26
 * Author: jianrui
 * Description: 封装通用工具类
 */
public class DeviceUtils {

    /**
     * dp 转 px
     * @param context
     * @param dp
     * @return
     */
    public static int dip2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }
}
