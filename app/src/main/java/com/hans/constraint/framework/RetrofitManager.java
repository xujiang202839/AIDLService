package com.hans.constraint.framework;

import com.hans.constraint.BuildConfig;
import com.hans.constraint.RetrofitApplication;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @创建者 xu
 * @创建时间 2020/3/24
 * @描述
 */
public class RetrofitManager {

    private static final String CACHE_NAME = "retrofit_cache";

    private static Retrofit retrofit = null;
    private static volatile OkHttpClient sOkHttpClient;

    /**
     * baseUrl
     */
    private static String url = "";

    private static final int CONNECT_TIME_OUT = 10;
    private static final int READ_TIME_OUT = 20;
    private static final int WRITE_TIME_OUT = 20;

    public static Retrofit getInstance(String baseUrl) {
        url = baseUrl;
        if (retrofit == null) {
            return create();
        } else {
            return retrofit;
        }
    }

    private static OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (sOkHttpClient == null) {
                    sOkHttpClient = new OkHttpClient.Builder()
                            .addInterceptor(new CacheInterceptor())
                            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)//设置超时
                            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true)
                            .build();
                }
            }
        }
        return sOkHttpClient;
    }

    private static Retrofit create() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // log用拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        //设置缓存目录
        File cacheFile = new File(RetrofitApplication.getContext().getExternalCacheDir(), CACHE_NAME);
        //生成缓存，20M
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 20);
        builder.addInterceptor(loggingInterceptor)
                .addInterceptor(new CacheInterceptor())
                .cache(cache)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)//设置超时
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);//错误重连  默认重试一次，若需要重试N次，则要实现拦截器。

        OkHttpClient client = builder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                //设置 Json 转换器 数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                //RxJava 适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }
}
