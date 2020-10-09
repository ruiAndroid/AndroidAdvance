package com.rui.framelibrary.recyclerview.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Time: 2020/9/30
 * Author: jianrui
 * Description: 通用的RecyclerView viewholder
 */
public class CommonViewHolder extends RecyclerView.ViewHolder{

    // 用来存放子View减少findViewById的次数
    private SparseArray<View> mViews;
    private View mRootView;

    public CommonViewHolder(@NonNull View itemView) {
        super(itemView);
        this.mRootView=itemView;
        mViews = new SparseArray<>();
    }


    /**
     * 通过id获取view
     */
    public <T extends View> T getView(int viewId) {
        // 先从缓存中找
        View view = mViews.get(viewId);
        if (view == null) {
            // 直接从ItemView中找
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public void setVisibility(int visible, int... viewIds) {
        for (int viewId : viewIds)
            getView(viewId).setVisibility(visible);
    }

    public void setItemClick(View.OnClickListener listener) {
        mRootView.setOnClickListener(listener);
    }

    /**
     * 设置条目点击事件
     */
    public void setOnIntemClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }

    /**
     * 设置条目长按事件
     */
    public void setOnIntemLongClickListener(View.OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
    }



}
