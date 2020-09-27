package com.rui.advancedemo.activity.imageselect;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;

import com.rui.advancedemo.R;
import com.rui.baselibrary.base.BaseSkinActivity;
import com.rui.baselibrary.ioc.ViewById;

import java.io.File;
import java.util.ArrayList;

/**
 * 图片选择器Activity
 */
public class ImageSelectActivity extends BaseSkinActivity {
    //选择图片的模式-多选
    //0000 0000 0000 0000 0000 0000 0000 0001
    private static final int SELECT_PIC_MODE_MULTI=0x00000001;
    //选择图片的模式-单选
    //0000 0000 0000 0000 0000 0000 0000 0010
    private static final int SELECT_PIC_MODE_SINGLE=0x00000002;

    //加载类型 默认加载所有数据 all
    //0000 0000 0000 0000 0000 0000 0001 0000
    private static final int LOAD_TYPE=0x00000010;

    //是否显示相机icon
    private static final String EXTRA_SHOW_CAMERA="EXTRA_SHOW_CAMERA";
    //总共可以选择多少张图片得extra_key
    private static final String EXTRA_SELECT_COUNT="EXTRA_SELECT_COUNT";
    //原始的图片路径extra_key
    private static final String EXTRA_ORIGIN_PATH="EXTRA_ORIGIN_PATH";
    //返回图片选择列表的extra_key
    private static final String EXTRA_RESULT="EXTRA_RESULT";

    // 选择模式的EXTRA_KEY
    public static final String EXTRA_SELECT_MODE = "EXTRA_SELECT_MODE";

    // 单选或者多选，int类型的type
    private int mMode = SELECT_PIC_MODE_MULTI;
    // int 类型的图片张数
    private int mMaxCount = 8;
    // boolean 类型的是否显示拍照按钮
    private boolean mShowCamera = true;

    //拍照临时存放的文件
    private File mTempFile;

    // ArrayList<String> 已经选择好的图片
    private ArrayList<String> mResultList;

    //图片RecyclerView
    @ViewById(R.id.image_list_rv)
    private RecyclerView mImageListRv;


    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_select_image);

    }

    @Override
    protected void initUI() {


    }

    @Override
    protected void initData() {
        //1.获取传递过来的参数
        Intent intent = getIntent();
        mMode = intent.getIntExtra(EXTRA_SELECT_MODE, mMode);
        mMaxCount = intent.getIntExtra(EXTRA_SELECT_COUNT, mMaxCount);
        mShowCamera = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, mShowCamera);
        mResultList = intent.getStringArrayListExtra(EXTRA_ORIGIN_PATH);
        if (mResultList == null) {
            mResultList = new ArrayList<>();
        }
        //2.初始化本地数据
        initImageList();
    }

    /**
     * 初始化本地图片数据
     */
    private void initImageList() {
        //开启线程
        getLoaderManager().initLoader(LOAD_TYPE,null,loaderCallbacks);

    }

    /**
     * 显示列表数据
     * @param images
     */
    private void showImageList(ArrayList<String> images) {


    }

    /**
     * loaderManager的回调
     */
    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks=new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID};

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            // 查询数据库一样 语句
            CursorLoader cursorLoader = new CursorLoader(ImageSelectActivity.this,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                    IMAGE_PROJECTION[4] + ">0 AND " + IMAGE_PROJECTION[3] + "=? OR "
                            + IMAGE_PROJECTION[3] + "=? ",
                    new String[]{"image/jpeg", "image/png"}, IMAGE_PROJECTION[2] + " DESC");

            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader loader, Cursor data) {
            // 解析，封装到集合  只保存String路径
            if (data != null && data.getCount() > 0) {
                ArrayList<String> images = new ArrayList<>();

                // 如果需要显示拍照，就在第一个位置上加一个空String
                if(mShowCamera){
                    images.add("");
                }


                // 不断的遍历循环
                while (data.moveToNext()) {
                    // 只保存路径
                    String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                    images.add(path);
                }

                // 显示列表数据
                showImageList(images);
            }
        }

        @Override
        public void onLoaderReset(Loader loader) {

        }
    };
}
