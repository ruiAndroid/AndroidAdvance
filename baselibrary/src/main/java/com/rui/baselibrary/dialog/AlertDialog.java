package com.rui.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;

import com.rui.baselibrary.R;

public class AlertDialog extends Dialog {

    private AlertController mController;

    public AlertDialog(Context context, int themeResId) {
        super(context, themeResId);
        mController=new AlertController(this,getWindow());
    }

    public static class Builder{

        private final AlertController.AlertParam P;

        public Builder(Context context) {
            this(context, R.style.dialog);
        }

        public Builder(Context context, int themeResId) {
            P=new AlertController.AlertParam(context,themeResId);
        }

        public AlertDialog create(){
            final AlertDialog dialog = new AlertDialog(P.mContext, 0);
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


        /**
         * Creates an {@link AlertDialog} with the arguments supplied to this
         * builder and immediately displays the dialog.
         * <p>
         * Calling this method is functionally identical to:
         * <pre>
         *     AlertDialog dialog = builder.create();
         *     dialog.show();
         * </pre>
         */
        public AlertDialog show() {
            final AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }


    }



}
