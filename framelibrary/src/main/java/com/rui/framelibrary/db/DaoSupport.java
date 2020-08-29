package com.rui.framelibrary.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.ArrayMap;
import android.util.Log;


import com.rui.framelibrary.utils.DaoUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Time: 2020/6/4
 * Author: jianrui
 * Description: Dao上层接口实现类
 */
@SuppressLint("NewApi")
public class DaoSupport<T> implements IDaoSupport<T>{

    private SQLiteDatabase mSqLiteDatabase;
    private Class<T> mClazz;

    //查询支持类
    private QuerySupport<T> mQuerySupport;

    //反射拿到的Method存储在ArrayMap中
    private static final ArrayMap<String,Method> sPutMethodMap =
            new ArrayMap<String, Method>();

    static final Object[] mPutMethodArgs = new Object[2];

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
        //优化批量插入
        //开启事务
        for(int i=0;i<list.size();i++){
            T t = list.get(i);
            ContentValues contentValues=contentValuesByObj(t);
            mSqLiteDatabase.insert(DaoUtils.getTableName(t),null,contentValues);
        }
    }


    @Override
    public int delete(String whereClause, String... whereArgs) {
        return mSqLiteDatabase.delete(DaoUtils.getTableName(mClazz),whereClause,whereArgs);
    }

    @Override
    public QuerySupport<T> querySupport() {
        if(mQuerySupport==null){
            mQuerySupport=new QuerySupport<>(mClazz,mSqLiteDatabase);
        }
        return mQuerySupport;
    }

    @Override
    public int update(T t, String whereClause, String... whereArgs) {
        ContentValues contentValues=contentValuesByObj(t);
        return mSqLiteDatabase.update(DaoUtils.getTableName(mClazz),contentValues,whereClause,whereArgs);
    }

    /**
     * 将obj转为contentValues对象
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
                mPutMethodArgs[0]=key;
                mPutMethodArgs[1]=value;
                Method putMethod=sPutMethodMap.get(value.getClass().getName());
                if(putMethod==null){
                    putMethod = ContentValues.class.getDeclaredMethod("put",String.class,value.getClass());
                    sPutMethodMap.put(value.getClass().getName(),putMethod);
                }

                //put value时必须指定类型
                putMethod.setAccessible(true);
                putMethod.invoke(contentValues,mPutMethodArgs);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }finally {
                mPutMethodArgs[0]=null;
                mPutMethodArgs[1]=null;
            }
        }
        return contentValues;
    }



}
