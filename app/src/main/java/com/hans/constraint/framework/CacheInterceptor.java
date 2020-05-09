package com.hans.constraint.framework;

import com.hans.constraint.RetrofitApplication;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @创建者 xu
 * @创建时间 2020/4/29
 * @描述 缓存拦截器
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //网络不可用
        if (!NetworkUtils.isAvailable(RetrofitApplication.getContext())) {
            //在请求头中加入：强制使用缓存，不访问网络
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        //网络可用
        if (NetworkUtils.isAvailable(RetrofitApplication.getContext())) {
            int maxAge = 0;
            // 有网络时 在响应头中加入：设置缓存超时时间0个小时
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("pragma")
                    .build();
        } else {
            // 无网络时，在响应头中加入：设置超时为2天
            int maxStale = 60 * 60 * 24 * 2;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("pragma")
                    .build();
        }
        return response;
    }
}
