package com.dm.ycm.gitapp.searchgituser.view.widget;

import android.app.AlertDialog;
import android.content.Context;

import dmax.dialog.SpotsDialog;

/**
 * Created by ycm on 2017/5/7.
 * Description:显示正在加载控件
 */
public class LoadingView {

    private AlertDialog mLoadingDialog;

    public LoadingView(Context context, String message) {
        mLoadingDialog = new SpotsDialog(context, message);
    }

    public void show() {
        mLoadingDialog.show();
    }

    public void dismiss() {
        mLoadingDialog.dismiss();
    }
}
