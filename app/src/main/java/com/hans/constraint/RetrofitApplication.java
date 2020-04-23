package com.hans.constraint;

import android.app.Application;
import android.content.Context;

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
    }

    private RetrofitApplication getApplication() {
        return application;
    }

    public static Context getContext(){
        return  application.getApplicationContext();
    }
}
