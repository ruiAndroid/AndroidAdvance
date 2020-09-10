package com.rui.baselibrary.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import com.rui.framelibrary.skin.SkinAttrSupport;
import com.rui.framelibrary.skin.SkinManager;
import com.rui.framelibrary.skin.attr.SkinAttr;
import com.rui.framelibrary.skin.attr.SkinView;
import com.rui.framelibrary.skin.support.SkinCompatViewInflater;

import java.util.List;

/**
 * Time: 2020/5/9
 * Author: jianrui
 * Description: base 支持换肤Activity
 */
public abstract class BaseSkinActivity extends BaseActivity implements LayoutInflaterFactory {

    private static final String TAG = "BaseSkinActivity";

    private SkinCompatViewInflater mAppCompatViewInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater layoutInflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LayoutInflaterCompat.setFactory(layoutInflater, this);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view=createView(parent,name,context,attrs);
        Log.i(TAG,"view tag:"+view);
        //一个Activity对应多个skinView
        if(view!=null){
            List<SkinAttr> skinAttrs=SkinAttrSupport.getSkinAttrs(context,attrs);
            if(skinAttrs.size()>0){
                SkinView skinView=new SkinView(view,skinAttrs);
                //统一交给skinManager管理
                managerSkinView(skinView);
            }
        }

        return view;
    }

    /**
     * 统一管理skinView
     * @param skinView
     */
    private void managerSkinView(SkinView skinView) {
        List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(this);
        if(skinViews==null){
            try {
                SkinManager.getInstance().register(this,skinViews);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        skinViews.add(skinView);

    }

    public View createView(View parent, String name, Context context, AttributeSet attrs){
        final boolean isPre21 = Build.VERSION.SDK_INT < 21;

        if (mAppCompatViewInflater == null) {
            mAppCompatViewInflater = new SkinCompatViewInflater();
        }

        // We only want the View to inherit its context if we're running pre-v21
        final boolean inheritContext = isPre21 && shouldInheritContext((ViewParent) parent);

        return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                isPre21, /* Only read android:theme pre-L (L+ handles this anyway) */
                true, /* Read read app:theme as a fallback at all times for legacy reasons */
                VectorEnabledTintResources.shouldBeUsed() /* Only tint wrap the context if enabled */
        );
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = getWindow().getDecorView();
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }
}
