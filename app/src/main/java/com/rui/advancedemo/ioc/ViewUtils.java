package com.rui.advancedemo.ioc;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Time: 2020/4/30
 * Author: jianrui
 * Description:
 */
public class ViewUtils {


    public static void inject(Activity activity){
        inject(new ViewFinder(activity),activity);
    }

    public static void inject(View view){
        inject(new ViewFinder(view),view);
    }

    public static void inject(ViewFinder viewFinder, Object object){
        injectFields(viewFinder,object);
        injectMethods(viewFinder,object);
    }

    /**
     * 注解filed
     */
    private static void injectFields(ViewFinder viewFinder,Object object) {
        Class<?> clazz = object.getClass();
        //1.获取类里面所有属性
        Field[] fields = clazz.getDeclaredFields();

        //2.获取viewById里面的view值
        for(Field field:fields){
            ViewById annotation = field.getAnnotation(ViewById.class);
            field.setAccessible(true);
            //获取注解里的id值
            int viewId=annotation.value();
            //3.findViewByid找到View
            View view=viewFinder.findViewById(viewId);
            if(view!=null){
                try {
                    field.set(object,view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * 注解method
     */
    private static void injectMethods(ViewFinder viewFinder,Object object) {
        Class<?> clazz= object.getClass();
        Method[] declaredMethods = clazz.getDeclaredMethods();

        for(Method method:declaredMethods){
            method.setAccessible(true);
            OnClick annotation = method.getAnnotation(OnClick.class);
            if(annotation!=null){
                for (int value : annotation.values()) {
                    View view=viewFinder.findViewById(value);
                    if(view!=null){
                        view.setOnClickListener(new DeclaredOnClickListener(method,object));
                    }
                }
            }
        }


    }


    private static class DeclaredOnClickListener implements View.OnClickListener{

        private Method mMethod;
        private Object mObject;

        public DeclaredOnClickListener(Method method,Object object) {
            this.mMethod=method;
            this.mObject=object;
        }

        @Override
        public void onClick(View v) {
            if(mMethod!=null){
                try {
                    mMethod.invoke(mObject);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
