package com.rui.advancedemo;

import android.os.Environment;
import android.widget.Toast;

import com.rui.advancedemo.ioc.OnClick;
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

    @OnClick(values = R.id.ioc_test)
    public void test(){
        Toast.makeText(this,"bug fix",Toast.LENGTH_LONG).show();
        fixDexBug();
    }

    public void fixDexBug(){
        File dexFile= new File(Environment.getExternalStorageDirectory(),"fix.dex");
        if(dexFile.exists()){

        }
    }
}
