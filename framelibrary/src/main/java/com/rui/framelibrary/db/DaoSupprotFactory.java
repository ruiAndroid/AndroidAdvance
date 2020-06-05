package com.rui.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * Time: 2020/6/4
 * Author: jianrui
 * Description: Dao工厂类
 */
public class DaoSupprotFactory {

    private volatile static DaoSupprotFactory mFactory;

    //持有外部数据库的引用
    private SQLiteDatabase mSqLiteDatabase;

    private DaoSupprotFactory() {

        //把数据库放在内存卡里面
        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator +"rui"+File.separator+"database");
        if(!file.exists()){
            file.mkdirs();
        }

        File dbFile=new File(file,"rui.db");

        //打开或创建一个数据库
        mSqLiteDatabase=SQLiteDatabase.openOrCreateDatabase(dbFile,null);
    }

    /**
     * 单例实现
     */
    public static DaoSupprotFactory getDaoFactoryInstance(){
        if(mFactory==null){
            synchronized (DaoSupprotFactory.class){
                if(mFactory==null){
                    mFactory=new DaoSupprotFactory();
                }
            }
        }
        return mFactory;
    }


    public<T> IDaoSupport<T> getDaoSupport(Class<T> clazz){
        IDaoSupport<T> iDaoSupport=new DaoSupport();
        iDaoSupport.init(mSqLiteDatabase,clazz);
        return iDaoSupport;
    }


}
