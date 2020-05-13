package com.rui.baselibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;

class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;
    
    public AlertController(AlertDialog mDialog,Window window) {
        this.mDialog = mDialog;
        this.mWindow=window;
    }

    public static class AlertParam {
        public Context mContext;
        public int mThemeId;
        public boolean mCancelable;

        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;

        public AlertParam(Context context, int themeResId) {
            this.mContext=context;
            this.mThemeId=themeResId;
        }
        public void apply(AlertController alertController){

        }
    }
}
