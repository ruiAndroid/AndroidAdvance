package com.rui.advancedemo;

import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.rui.baselibrary.ioc.CheckNet;
import com.rui.baselibrary.ioc.OnClick;
import com.rui.baselibrary.ioc.ViewUtils;
import com.rui.baselibrary.base.BaseSkinActivity;
import com.rui.baselibrary.dialog.AlertDialog;

import java.io.File;

public class MainActivity extends BaseSkinActivity {

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

    }

    @CheckNet
    @OnClick(values = R.id.dialog_test)
    public void test(View view){
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
                .show();
    }

    @Override
    public void initUI() {


    }

    @Override
    public void initData() {

    }

    /**
     * 热修复
     */
    public void fixDexBug(){
        File dexFile= new File(Environment.getExternalStorageDirectory(),"fix.dex");
        if(dexFile.exists()){

        }
    }
}
