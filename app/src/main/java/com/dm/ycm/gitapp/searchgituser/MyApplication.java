package com.dm.ycm.gitapp.searchgituser;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by ycm on 2017/5/7.
 * Description: 检测内存泄漏
 */

public class MyApplication extends Application {
    private RefWatcher refWatcher;
    @Override public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }
}
