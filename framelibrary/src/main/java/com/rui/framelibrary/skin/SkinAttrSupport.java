package com.rui.framelibrary.skin;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.rui.framelibrary.skin.attr.SkinAttr;
import com.rui.framelibrary.skin.attr.SkinType;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;

/**
 * Time: 2020/8/29
 * Author: jianrui
 * Description: 皮肤属性解析支持类
 */
public class SkinAttrSupport {


    private static final String TAG = "SkinAttrSupport";

    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        List<SkinAttr> skinAttrs=new ArrayList<>();
        int attributeCount = attrs.getAttributeCount();
        for(int i=0;i<attributeCount;i++){
            String attrName=attrs.getAttributeName(i);
            String attrValue=attrs.getAttributeValue(i);
            Log.i(TAG,"attrName:"+attrName);
            Log.i(TAG,"attrValue:"+attrValue);
            //只过滤需要的attr
            SkinType skinType=getSkinType(attrName);
            if(skinType!=null){
                //资源名称，目前只有attrVaule ,是一个@int类型
                String resName=getResName(context,attrValue);
                if(TextUtils.isEmpty(resName)){
                    continue;
                }
                SkinAttr skinAttr=new SkinAttr(resName,skinType);
                skinAttrs.add(skinAttr);
            }
        }
        return skinAttrs;
    }

    /**
     * 获取资源名称
     * @param context
     * @param attrValue
     * @return
     */
    private static String getResName(Context context, String attrValue) {
        if(attrValue.startsWith("@")){
            attrValue = attrValue.substring(1);
            int attrInteger = 0;
            try {
                attrInteger = Integer.parseInt(attrValue);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            if(attrInteger!=0){
                return context.getResources().getResourceEntryName(attrInteger);
            }
        }
        return null;
    }

    /**
     * 根据attrName获取skinType
     * @param attrName
     * @return
     */
    private static SkinType getSkinType(String attrName) {
        SkinType[] skinTypes=SkinType.values();
        for (SkinType skinType : skinTypes) {
            if(skinType.getResName().equals(attrName)){
                return skinType;
            }
        }
        return null;

    }
}
