package com.rui.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Time: 2020/6/4
 * Author: jianrui
 * Description: Dao上层接口
 */
public interface IDaoSupport<T> {
    public void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz);
    //插入数据
    public void insert(T t);
    //批量插入
    public void insert(List<T> list);

    //删除数据
    public int delete(String whereClause, String... whereArgs);

    //查询数据
    public List<T> query();

    //更新数据
    public int update(T t,String whereClause,String ...whereArgs);



}
