package com.example.injection.network;

import android.os.Build;

import com.example.injection.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManager {

    private Retrofit retrofit;

    public NetManager(){
        //constructor
    }

    public Retrofit provideRetrofitClient(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(provideRxJavaCallAdapter())
                .addConverterFactory(provideConverterFactory())
                .client(provideHttpClient())
                .build();
    }

    private GsonConverterFactory provideConverterFactory(){
        return GsonConverterFactory.create();
    }

    private RxJava2CallAdapterFactory provideRxJavaCallAdapter(){
        return RxJava2CallAdapterFactory.create();
    }

    private HttpLoggingInterceptor provideHttpIntercetptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        if(BuildConfig.DEBUG) {
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
        }else   {
            interceptor.level(HttpLoggingInterceptor.Level.NONE);
        }
        return interceptor;
    }
    private OkHttpClient provideHttpClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(provideHttpIntercetptor())
                .build();
    }




}
