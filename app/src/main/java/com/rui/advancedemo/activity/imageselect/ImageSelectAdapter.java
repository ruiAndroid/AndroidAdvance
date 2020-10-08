package com.rui.advancedemo.activity.imageselect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.rui.advancedemo.R;
import com.rui.framelibrary.recyclerview.MultiTypeSupport;
import com.rui.framelibrary.recyclerview.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Time: 2020/9/27
 * Author: jianrui
 * Description: 图片列表adapter
 */
public class ImageSelectAdapter extends CommonAdapter<ImageEntity>{

    private Context mContext;
    private ArrayList<String> mSelectImages;
    private int mMaxCount;
    public final static int REQUEST_CAMERA = 0x0045;
    private int mMode;


    public ImageSelectAdapter(Context context, ArrayList<String> selectImages, int maxCount, int mode) {
        super(context, new ArrayList<ImageEntity>(), R.layout.media_chooser_item);
        this.mContext = context;
        mSelectImages = selectImages;
        this.mMaxCount = maxCount;
        this.mMode = mode;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
    
    @Override
    public void convert(RecyclerView.ViewHolder holder, ImageEntity item) {


    }


}
