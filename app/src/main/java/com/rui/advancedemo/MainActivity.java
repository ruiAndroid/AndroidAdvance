package com.rui.advancedemo;

import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.rui.advancedemo.ioc.OnClick;
import com.rui.advancedemo.ioc.ViewUtils;
import com.rui.baselibrary.base.BaseSkinActivity;
import com.rui.baselibrary.dialog.AlertDialog;

import java.io.File;

public class MainActivity extends BaseSkinActivity {



    @Override
    public void setLayout() {
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

    }

    @Override
    public void initUI() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(R.layout.layout_dialog)
                .setText(R.id.dialog_ok,"好的")
                .setClickListener(R.id.dialog_ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
                    }
                })
                .fullWidth()
                .create().show();
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
