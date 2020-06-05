package com.rui.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rui.baselibrary.utils.DaoUtils;

import java.lang.reflect.Field;

/**
 * Time: 2020/6/4
 * Author: jianrui
 * Description: Dao上层接口实现类
 */
public class DaoSupport<T> implements IDaoSupport{

    private SQLiteDatabase mSqLiteDatabase;
    private Class<T> mClazz;



    @Override
    public void init(SQLiteDatabase sqLiteDatabase, Class clazz) {
        this.mSqLiteDatabase=sqLiteDatabase;
        this.mClazz=clazz;

        StringBuffer sb=new StringBuffer();
        sb.append("create table if not exists ").append(clazz.getSimpleName()).append(" (id integer primary key autoincrement, ");
        //反射拿字段
        Field[] declaredFields = mClazz.getDeclaredFields();
        for(Field field:declaredFields){
            field.setAccessible(true);
            String name=field.getName();
            String type=field.getType().getSimpleName();
            //type进行转换
            sb.append(name).append(DaoUtils.getColumnType(type)).append(",");
        }
        sb.replace(sb.length()-1,sb.length(),")");
        Log.i("test","jianrui:"+sb);
        //创建表
        mSqLiteDatabase.execSQL(sb.toString());
    }

    /**
     * 插入数据库
     * @param o  任意对象
     */
    @Override
    public void insert(Object o) {


    }
}
