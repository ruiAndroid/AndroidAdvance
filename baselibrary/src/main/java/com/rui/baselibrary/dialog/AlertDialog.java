package com.rui.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.rui.baselibrary.R;

public class AlertDialog extends Dialog {

    private AlertController mController;

    public AlertDialog(Context context, int themeResId) {
        super(context, themeResId);
        mController=new AlertController(this,getWindow());
    }

    /**
     * 建造者设计模式
     */
    public static class Builder{

        private final AlertController.AlertParam P;

        public Builder(Context context) {
            this(context, R.style.dialog);
        }

        public Builder(Context context, int themeResId) {
            P=new AlertController.AlertParam(context,themeResId);
        }

        public Builder setText(int viewId,String text){
            P.mTextSparseArray.put(viewId,text);
            return this;
        }

        public Builder setClickListener(int viewId,View.OnClickListener listener){
            P.mOnClickListenerSparseArray.put(viewId,listener);
            return this;
        }
        public Builder setView(int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }

        public Builder setView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        public Builder fullWidth() {
            P.mWidth= ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }


        public AlertDialog create(){
            final AlertDialog dialog = new AlertDialog(P.mContext, R.style.dialog);
            P.apply(dialog.mController);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        public AlertDialog show() {
            final AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }

    }

}
