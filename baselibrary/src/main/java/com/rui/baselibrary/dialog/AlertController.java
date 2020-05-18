package com.rui.baselibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;
    
    public AlertController(AlertDialog mDialog,Window window) {
        this.mDialog = mDialog;
        this.mWindow=window;
    }

    public AlertDialog getDialog() {
        return mDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    public static class AlertParam {
        public Context mContext;
        public int mThemeId;
        public boolean mCancelable;
        public View mView;
        public int mViewLayoutResId;
        public int mWidth= ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mHeight= ViewGroup.LayoutParams.WRAP_CONTENT;

        public SparseArray<CharSequence> mTextSparseArray=new SparseArray<>();

        public SparseArray<View.OnClickListener> mOnClickListenerSparseArray=new SparseArray<>();

        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;

        public void setOnCancelListener(DialogInterface.OnCancelListener mOnCancelListener) {
            this.mOnCancelListener = mOnCancelListener;
        }

        public void setOnDismissListener(DialogInterface.OnDismissListener mOnDismissListener) {
            this.mOnDismissListener = mOnDismissListener;
        }

        public void setOnKeyListener(DialogInterface.OnKeyListener mOnKeyListener) {
            this.mOnKeyListener = mOnKeyListener;
        }

        public AlertParam(Context context, int themeResId) {
            this.mContext=context;
            this.mThemeId=themeResId;
        }


        public void apply(AlertController alertController){
            //设置参数
            DialogViewHelper dialogViewHelper=null;
            //设置布局
            if(mViewLayoutResId!=0){
                dialogViewHelper=new DialogViewHelper(mContext,mViewLayoutResId);
            }
            if(mView!=null){
                dialogViewHelper=new DialogViewHelper();
                dialogViewHelper.setContentView(mView);
            }
            if(dialogViewHelper==null){
                throw  new IllegalArgumentException("please set contentView");
            }
            //给dialog设置布局
            alertController.mDialog.setContentView(dialogViewHelper.getContentView());
            //设置文本
            int textSize = mTextSparseArray.size();
            for(int i=0;i<textSize;i++){
                dialogViewHelper.setText(mTextSparseArray.keyAt(i),mTextSparseArray.valueAt(i));
            }

            //设置点击
            int clickSize = mOnClickListenerSparseArray.size();
            for(int i=0;i<clickSize;i++){
                dialogViewHelper.setOnClickListener(mOnClickListenerSparseArray.keyAt(i),mOnClickListenerSparseArray.valueAt(i));
            }
            //配置自定义效果等
            Window window=alertController.getWindow();
            //设置宽高
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width=mWidth;
            attributes.height=mHeight;
            window.setAttributes(attributes);

        }
    }
}
