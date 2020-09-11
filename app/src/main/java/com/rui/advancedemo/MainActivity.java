package com.rui.advancedemo;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.rui.advancedemo.model.DBTestBean;
import com.rui.advancedemo.navigation.DefaultNavigationBar;
import com.rui.baselibrary.base.BaseSkinActivity;
import com.rui.baselibrary.http.HttpUtils;
import com.rui.baselibrary.ioc.CheckNet;
import com.rui.baselibrary.ioc.OnClick;
import com.rui.baselibrary.ioc.ViewById;
import com.rui.baselibrary.ioc.ViewUtils;
import com.rui.framelibrary.db.DaoSupportFactory;
import com.rui.framelibrary.db.IDaoSupport;
import com.rui.baselibrary.http.HttpCallback;
import com.rui.framelibrary.skin.SkinManager;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

public class MainActivity extends BaseSkinActivity {

    private static final String TAG = "MainActivity";
    @ViewById(R.id.content)
    private RelativeLayout mContent;

    @ViewById(R.id.dialog_test)
    private Button mButton;

    @ViewById(R.id.skin_load)
    private Button mSkinLoadButton;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        HttpUtils.with(this)
                .url("http://www.baidu.com?")
                .cache(true)    //读缓存
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
        /*List<DBTestBean> dbTestBeans=new ArrayList<>();
        for(int i=0;i<10;i++){
            DBTestBean dbTestBean=new DBTestBean();
            dbTestBean.setAge(i);
            dbTestBean.setName("a:"+i);
            dbTestBeans.add(dbTestBean);
        }
        daoSupport.insert(dbTestBeans);*/
        List<DBTestBean> query = daoSupport.querySupport().selections("age=?").selectionArgs("2").query();

        Log.i("test","query result:"+query.toString());
        mSkinLoadButton.requestFocus();
    }

    /**
     * 加载皮肤
     */
    @CheckNet
    @OnClick(values = R.id.skin_load)
    public void skinLoad(){
        String skinPath=Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator +"rui"+File.separator+"skins";
        int result=SkinManager.getInstance().loadSkin(skinPath);
        Log.i(TAG,"skinload result:"+result);


    }

    /**
     * 还原皮肤
     */
    @CheckNet
    @OnClick(values = R.id.skin_store)
    public void skinRestore(){
        SkinManager.getInstance().restoreSkin();

    }

    @CheckNet
    @OnClick(values = R.id.dialog_test)
    public void test(){
        //dialog
     /*   AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(R.layout.layout_dialog)
                .setText(R.id.dialog_ok,"好的")
                .setClickListener(R.id.dialog_ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
                    }
                })
                .fullWidth()
                .show();*/



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
