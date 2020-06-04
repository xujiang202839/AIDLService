package com.hans.constraint;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.hans.constraint.utils.MultiLanguageUtil;

/**
 * @创建者 xu
 * @创建时间 2020/3/24
 * @描述
 */
public class RetrofitApplication extends Application {


    private static RetrofitApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        MultiLanguageUtil.init(application.getApplicationContext());
    }

    private RetrofitApplication getApplication() {
        return application;
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }

    /**
     * apk 安装
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
