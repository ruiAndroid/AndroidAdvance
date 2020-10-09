package com.rui.advancedemo.activity.imageselect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.rui.advancedemo.R;
import com.rui.baselibrary.utils.FileUtils;
import com.rui.framelibrary.recyclerview.MultiTypeSupport;
import com.rui.framelibrary.recyclerview.adapter.CommonAdapter;
import com.rui.framelibrary.recyclerview.viewholder.CommonViewHolder;

import java.io.File;
import java.io.IOException;
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

    private UpdateSelectListener mListener;

    public void setOnUpdateSelectListener(UpdateSelectListener listener) {
        this.mListener = listener;
    }

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
    public void convert(CommonViewHolder holder, final ImageEntity item) {
        if (item == null) {
            holder.setVisibility(View.INVISIBLE, R.id.image, R.id.mask, R.id.media_selected_indicator);
            holder.setVisibility(View.VISIBLE, R.id.camera_tv);
            holder.setItemClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCamera();
                }
            });
        } else {
            holder.setVisibility(View.VISIBLE, R.id.image, R.id.media_selected_indicator);
            holder.setVisibility(View.INVISIBLE, R.id.camera_tv);
            // 显示图片
            ImageView imageView = holder.getView(R.id.image);

           /* Glide.with(mContext)
                    .load(item.path)
                    .centerCrop()
                    .into(imageView);*/

            ImageView selectedIndicatorIv = holder.getView(R.id.media_selected_indicator);
            selectedIndicatorIv.setSelected(mSelectImages.contains(item.path));

            // 设置选中效果
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelectImages.contains(item.path)) {
                        mSelectImages.remove(item.path);
                    } else {
                        // 判断是否到达最大
                        if (mMaxCount == mSelectImages.size()) {
                            Toast.makeText(mContext, "最多只能选择" +
                                    mMaxCount + "张图片", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mSelectImages.add(item.path);
                    }
                    if (mListener != null) {
                        mListener.selector();
                    }
                    notifyDataSetChanged();
                }
            });
        }

    }


    /**
     * 打开相机拍照
     */
    private void openCamera() {
        try {
            File tmpFile = FileUtils.createTmpFile(mContext);
            if (mListener != null) {
                mListener.openCamera(tmpFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "相机打开失败", Toast.LENGTH_LONG).show();
        }
    }

    public interface UpdateSelectListener {
        public void selector();

        public void openCamera(File file);
    }


}
