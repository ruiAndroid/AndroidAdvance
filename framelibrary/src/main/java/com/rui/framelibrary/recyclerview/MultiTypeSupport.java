package com.rui.framelibrary.recyclerview;

/**
 * Time: 2020/9/30
 * Author: jianrui
 * Description: 多布局接口
 */
public interface MultiTypeSupport<T> {
    // 根据当前位置或者条目数据返回布局
    public int getLayoutId(T item, int position);
}
