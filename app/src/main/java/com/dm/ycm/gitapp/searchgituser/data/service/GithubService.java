package com.dm.ycm.gitapp.searchgituser.data.service;

import com.dm.ycm.gitapp.searchgituser.data.config.Config;
import com.dm.ycm.gitapp.searchgituser.data.convert.StringConverterFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by ycm on 2017/5/7.
 * Description:配置retrofit 和okhttp
 */

public class GithubService {

    public static <T> T createRetrofitService(final Class<T> service) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "token " + Config.ACCESS_TOKEN)
                        .build();
                return chain.proceed(request);
            }
        }).addInterceptor(httpLoggingInterceptor);
        OkHttpClient okHttpClient = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(StringConverterFactory.create())
                .baseUrl(Config.BASE_URL)
                .build();
        return retrofit.create(service);
    }
}
