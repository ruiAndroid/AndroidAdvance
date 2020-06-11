package com.rui.framelibrary.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rui.baselibrary.utils.DaoUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Time: 2020/6/4
 * Author: jianrui
 * Description: Dao上层接口实现类
 */
public class DaoSupport<T> implements IDaoSupport<T>{

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
    public void insert(T t) {
        ContentValues contentValues=contentValuesByObj(t);
        mSqLiteDatabase.insert(DaoUtils.getTableName(t),null,contentValues);
    }

    /**
     * 批量插入数据库
     * @param list
     */
    @Override
    public void insert(List<T> list) {
        

    }


    @Override
    public void delete(T t) {

    }


    /**
     * 将obj转为conentValues对象
     * @param t
     */
    public ContentValues contentValuesByObj(T t){
        //封装values
        ContentValues contentValues=new ContentValues();
        Field[] declaredFields = t.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            String key=declaredField.getName();
            try {
                Object value=declaredField.get(t);
                //put value时必须指定类型
                Method putMethod = ContentValues.class.getDeclaredMethod("put",String.class,value.getClass());
                putMethod.setAccessible(true);
                putMethod.invoke(contentValues,key,value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return contentValues;
    }



}
