package com.rui.baselibrary.utils;

/**
 * Time: 2020/6/4
 * Author: jianrui
 * Description:
 */
public class DaoUtils {

    /**
     * 获取column字段类型
     * @param type
     * @return
     */
    public static String getColumnType(String type){
        String value=null;
        if(type.contains("String")){
            value=" text";
        }else if(type.contains("int")){
            value=" integer";
        }else if(type.contains("boolean")){
            value=" boolean";
        }else if(type.contains("float")){
            value=" float";
        }else if(type.contains("double")){
            value=" double";
        }else if(type.contains("char")){
            value=" varchar";
        }else if(type.contains("long")){
            value=" long";
        }
        return value;
    }

    /**
     * 根据对象获取到对应的table name
     * @param obj
     */
    public static String getTableName(Object obj){
        return obj.getClass().getSimpleName();
    }


}
