package com.rui.advancedemo.activity;

import android.content.Intent;
import android.widget.TextView;

import com.rui.baselibrary.base.BaseSkinActivity;
import com.rui.baselibrary.ioc.ViewById;

import java.io.File;

/**
 * 图片选择器Activity
 */
public class ImageSelectActivity extends BaseSkinActivity {
    //选择图片的模式-多选
    private static final int SELECT_PIC_MODE_MULTI=0x0011;
    //选择图片的模式-单选
    private static final int SELECT_PIC_MODE_SINGLE=0x0012;
    //是否显示相机icon
    private static final String EXTRA_SHOW_CAMERA="EXTRA_SHOW_CAMERA";
    //总共可以选择多少张图片得extra_key
    private static final String EXTRA_SELECT_COUNT="EXTRA_SELECT_COUNT";
    //原始的图片路径extra_key
    private static final String EXTRA_ORIGIN_PATH="EXTRA_ORIGIN_PATH";
    //单选模式的extra_key
    private static final String EXTRA_SINGLE_SELECT_MODE="EXTRA_SINGLE_SELECT_MODE";
    //返回图片选择列表的extra_key
    private static final String EXTRA_RESULT="EXTRA_RESULT";

//    @ViewById(R>)
    private TextView mSelectNumTv;

    //拍照临时存放的文件
    private File mTempFile;


    @Override
    protected void setLayout() {

    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {
        //1.获取传递过来的参数
        Intent intent = getIntent();


    }
}
