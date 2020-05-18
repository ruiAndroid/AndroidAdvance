package com.rui.baselibrary.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

class DialogViewHelper {

    private View mContentView;
    private SparseArray<WeakReference<View>> viewIds;

    public DialogViewHelper(Context mContext, int mViewLayoutResId) {
        this();
        mContentView=LayoutInflater.from(mContext).inflate(mViewLayoutResId,null,false);

    }

    public DialogViewHelper() {
        viewIds=new SparseArray<>();
    }

    public View getContentView() {
        return mContentView;
    }

    public void setContentView(View mView) {
        mContentView=mView;
    }

    public void setText(int viewId, CharSequence Text) {
        TextView tv=findView(viewId);
        tv.setText(Text);

    }



    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View tv=findView(viewId);
        tv.setOnClickListener(listener);

    }

    /**
     * 优化findView次数
     * @param viewId
     * @return
     */
    private <T extends View> T findView(int viewId) {
        WeakReference<View> viewWeakReference = viewIds.get(viewId);
        View view=null;
        if(viewWeakReference!=null){
            view=viewWeakReference.get();
        }
        if(view==null){
            view=mContentView.findViewById(viewId);
            if(view!=null){
                viewIds.put(viewId,new WeakReference<View>(view));
            }
        }
        return (T) view;
    }
}
