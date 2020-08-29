package com.rui.framelibrary.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Time: 2020/8/19
 * Author: jianrui
 * Description: 查询支持类
 */
public class QuerySupport<T> {
    //查询的列
    private String[] mQueryColumns;
    //查询的条件
    private String mQuerySelection;
    //查询的参数
    private String[] mQuerySelectionArgs;
    //查询分组
    private String mQueryGroupBy;
    //对结果集进行过滤
    private String mQueryHaving;
    //查询排序
    private String mQueryOrdering;
    //查询可用于分页
    private String mQueryLimit;

    private Class<T> mClazz;

    private SQLiteDatabase mSqlLiteDatabase;

    public QuerySupport(Class<T> mClass, SQLiteDatabase mSqlLiteDatabase) {
        this.mClazz = mClass;
        this.mSqlLiteDatabase = mSqlLiteDatabase;
    }

    public QuerySupport columns(String... columns){
        this.mQueryColumns=columns;
        return this;
    }

    public QuerySupport selections(String selections){
        this.mQuerySelection=selections;
        return this;
    }

    public QuerySupport selectionArgs(String... selectionArgs){
        this.mQuerySelectionArgs=selectionArgs;
        return this;
    }

    public QuerySupport groupBy(String groupBy){
        this.mQueryGroupBy=groupBy;
        return this;
    }

    public QuerySupport having(String having){
        this.mQueryHaving=having;
        return this;
    }

    public QuerySupport ording(String ordering){
        this.mQueryOrdering=ordering;
        return this;
    }

    public QuerySupport limit(String limit){
        this.mQueryLimit=limit;
        return this;
    }

    /**
     * 查询表中所有数据
     * @return
     */
    public List<T> queryAll(){
        Log.i("test","queryAll");
        Cursor cursor = mSqlLiteDatabase.query(mClazz.getSimpleName(),
                null, null, null,null, null, null);
        return cursorToList(cursor);
    }

    /**
     * 根据条件查询
     * @return
     */
    public List<T> query(){
        Log.i("test","query");

        Cursor cursor = mSqlLiteDatabase.query(mClazz.getSimpleName(),
                mQueryColumns, mQuerySelection, mQuerySelectionArgs, mQueryGroupBy, mQueryHaving, mQueryOrdering);
        clearQueryParams();
        return cursorToList(cursor);
    }

    /**
     * 清空所有查询参数
     */
    private void clearQueryParams() {
        this.mQueryColumns=null;
        this.mQuerySelection=null;
        this.mQuerySelectionArgs=null;
        this.mQueryGroupBy=null;
        this.mQueryHaving=null;
        this.mQueryOrdering=null;
        this.mQueryLimit=null;

    }

    /**
     * 游标转为list
     * @param cursor
     * @return
     */
    private List<T> cursorToList(Cursor cursor) {
        List<T> list=new ArrayList<>();
        if(cursor!=null && cursor.moveToFirst()){
            //不断从游标里面获取数据
            do {
                try {
                    T t = mClazz.newInstance();
                    Field[] declaredFields = mClazz.getDeclaredFields();
                    for(Field field:declaredFields){
                        //遍历属性
                        field.setAccessible(true);
                        String name = field.getName();
                        //获取name在表中的index
                        int columnIndex = cursor.getColumnIndex(name);
                        if(columnIndex==-1){
                            continue;
                        }

                        //通过反射获取游标方法
                        Method cursorMethod=cursorMethod(field.getType());
                        if(cursorMethod!=null){
                            Object value = cursorMethod.invoke(cursor, columnIndex);
                            if(value==null){
                                continue;
                            }
                            //处理一些特殊的部分
                            if(field.getType()==boolean.class || field.getType()==Boolean.class){
                                if("0".equals(String.valueOf(value))){
                                    value=false;
                                }else if("1".equals(String.valueOf(value))){
                                    value=true;
                                }
                            }else if(field.getType()==char.class || field.getType()==Character.class){
                                value=((String)value).charAt(0);
                            }else if(field.getType()== Date.class){
                                long date= (long) value;
                                if(date<0){
                                    value=null;
                                }else{
                                    value=new Date(date);
                                }
                            }
                            field.set(t,value);
                            list.add(t);
                        }

                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }


            }while (cursor.moveToNext());
        }
        return list;
    }

    private Method cursorMethod(Class<?> type) throws NoSuchMethodException {
        String methodName=getColumnMethodName(type);
        Method method=Cursor.class.getMethod(methodName,int.class);
        return method;
    }

    private String getColumnMethodName(Class<?> type) {
        String typeName;
        typeName=type.getSimpleName();
        String methodName="get"+typeName;
        if("getBoolean".equals(methodName) || "getInteger".equals(methodName)){
            methodName="getInt";
        }else if("getChar".equals(methodName) || "getCharacter".equals(methodName)){
            methodName="getString";
        }else if("getDate".equals(methodName)){
            methodName="getLong";
        }
        return methodName;
    }



}
