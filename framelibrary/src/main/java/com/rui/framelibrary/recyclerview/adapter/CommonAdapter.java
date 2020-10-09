package com.rui.framelibrary.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rui.framelibrary.recyclerview.MultiTypeSupport;
import com.rui.framelibrary.recyclerview.viewholder.CommonViewHolder;

import java.util.List;

/**
 * Time: 2020/9/30
 * Author: jianrui
 * Description: RecyclerView通用的adapter
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder>{

    protected Context mContext;
    protected LayoutInflater mInflater;
    //数据怎么办？利用泛型
    protected List<T> mDatas;
    // 布局怎么办？直接从构造里面传递
    private int mLayoutId;

    //多布局支持
    private MultiTypeSupport mMultiTypeSupport;

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = datas;
        this.mLayoutId = layoutId;
    }

    /**
     * 多布局支持
     */
    public CommonAdapter(Context context, List<T> datas, MultiTypeSupport<T> multiTypeSupport) {
        this(context, datas, -1);
        this.mMultiTypeSupport = multiTypeSupport;
    }

    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 多布局支持
        if (mMultiTypeSupport != null) {
            mLayoutId = viewType;
        }
        // 先inflate数据
        View itemView = mInflater.inflate(mLayoutId, parent, false);
        // 返回ViewHolder
        CommonViewHolder commonViewHolder=new CommonViewHolder(itemView);
        return commonViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder viewHolder, int position) {
        convert(viewHolder,mDatas.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        // 多布局支持
        if (mMultiTypeSupport != null) {
            return mMultiTypeSupport.getLayoutId(mDatas.get(position), position);
        }
        return super.getItemViewType(position);
    }

    /**
     * 利用抽象方法回传出去，每个不一样的Adapter去设置
     * @param item 当前的数据
     */
    public abstract void convert(CommonViewHolder holder, T item);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
