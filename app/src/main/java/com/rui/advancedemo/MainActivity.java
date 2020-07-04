package com.rui.advancedemo;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rui.advancedemo.model.DBTestBean;
import com.rui.advancedemo.navigation.DefaultNavigationBar;
import com.rui.baselibrary.base.BaseSkinActivity;
import com.rui.baselibrary.dialog.AlertDialog;
import com.rui.baselibrary.http.HttpUtils;
import com.rui.baselibrary.ioc.CheckNet;
import com.rui.baselibrary.ioc.OnClick;
import com.rui.baselibrary.ioc.ViewById;
import com.rui.baselibrary.ioc.ViewUtils;
import com.rui.framelibrary.db.DaoSupportFactory;
import com.rui.framelibrary.db.IDaoSupport;
import com.rui.framelibrary.http.HttpCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseSkinActivity {

    @ViewById(R.id.content)
    private RelativeLayout mContent;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        HttpUtils.with(this)
                .url("http://www.baidu.com?")
                .get()
                .execute(new HttpCallback<String>() {
                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void onSuccess(String result) {
                        //转换成可操作性的对象

                        Log.i("test","jianrui:"+result);

                    }
                });


        IDaoSupport<DBTestBean> daoSupport = DaoSupportFactory.getDaoFactoryInstance().getDaoSupport(DBTestBean.class);
//        daoSupport.insert(new DBTestBean(1,"haha"));
//        List<DBTestBean> dbTestBeans=new ArrayList<>();
       /* for(int i=0;i<10;i++){
            DBTestBean dbTestBean=new DBTestBean();
            dbTestBean.setAge(i);
            dbTestBean.setName("a:"+i);
            dbTestBeans.add(dbTestBean);
        }
        daoSupport.insert(dbTestBeans);*/
        List<DBTestBean> query = daoSupport.query();
        Log.i("test","query result:"+query.toString());
    }

    @CheckNet
    @OnClick(values = R.id.dialog_test)
    public void test(View view){
        //dialog
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

              //填充title
        DefaultNavigationBar defaultNavigationBar= new DefaultNavigationBar.Builder(this,null)
                .setTitle("wokk")
                .setLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .build();



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
