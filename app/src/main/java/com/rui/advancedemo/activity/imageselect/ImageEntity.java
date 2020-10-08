package com.rui.advancedemo.activity.imageselect;

import android.text.TextUtils;

/**
 * 图片信息bean
 */
public class ImageEntity {
    public String path;
    public String name;
    public long time;

    public ImageEntity(String path, String name, long time) {
        this.path = path;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ImageEntity) {
            ImageEntity compare = (ImageEntity) o;
            return TextUtils.equals(this.path, compare.path);
        }
        return false;
    }

}
