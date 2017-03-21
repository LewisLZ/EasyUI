package com.lewis.easyui.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lewis.easyui.easyui;

public class UIDialogUtil {


    private static UIDialogUtil instance;

    public static synchronized UIDialogUtil getInstance() {
        if (null == instance) {
            instance = new UIDialogUtil();
        }
        return instance;
    }

    public interface DialogListener {
        public void clickOk(String idOrName);

        public void clickCancel();
    }

    public Dialog buildDialog(Context mContext, View view, boolean outsideCancelable) {
        return buildDialog(mContext, view, -1, -1, outsideCancelable);
    }

    public Dialog buildDialog(Context mContext, View view, int gravity, int animationResId, boolean outsideCancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        if (gravity >= 0) {
            window.setGravity(gravity);
        }
        if (animationResId > 0) {
            window.setWindowAnimations(animationResId);
        }
        dialog.show();
        window.setContentView(view);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = easyui.screenWidthScale; //设置宽度
        window.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(outsideCancelable);
        return dialog;
    }

    public ProgressDialog buildProgressDialog(Context mContext, View view, boolean outsideCancelable) {
        return buildProgressDialog(mContext, view, -1, -1, outsideCancelable);
    }

    public ProgressDialog buildProgressDialog(Context mContext, View view, int gravity, int animationResId, boolean outsideCancelable) {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        Window window = progressDialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        if (gravity >= 0) {
            window.setGravity(gravity);
        }
        if (animationResId > 0) {
            window.setWindowAnimations(animationResId);
        }
        progressDialog.show();
        window.setContentView(view);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = easyui.screenWidthScale; //设置宽度
        window.setAttributes(lp);
        progressDialog.setCanceledOnTouchOutside(outsideCancelable);
        return progressDialog;
    }

}