package com.rui.baselibrary.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Time: 2020/5/8
 * Author: jianrui
 * Description: 整个应用的 Activity base类
 * 通用代码封装到baseActivity
 */
public abstract class BaseActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        initUI();
        initData();
    }

    /**
     * 设置布局
     */
    protected abstract void setLayout();

    /**
     * 初始化Ui
     */
    protected abstract void initUI();
    /**
     * 初始化数据
     */
    protected abstract void initData();


    protected <T extends View>T viewById(int id){
        return (T)findViewById(id);
    }

}
