package com.rui.advancedemo;

import android.os.Environment;

import com.rui.advancedemo.ioc.ViewUtils;
import com.rui.baselibrary.base.BaseSkinActivity;

import java.io.File;

public class MainActivity extends BaseSkinActivity {



    @Override
    public void setLayout() {
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {

    }


    public void fixDexBug(){
        File dexFile= new File(Environment.getExternalStorageDirectory(),"fix.dex");
        if(dexFile.exists()){

        }
    }
}
