package com.ervin.mvp.api;

import android.ervin.mvp.BuildConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ervin on 2017/10/30.
 */

public class ApiClient {

    public static final String HOST = "https://www.v2ex.com/api/";
    public static final String TAB_HOST = "https://www.v2ex.com/?tab=";
    private static ApiService mApiService;
    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;

    private ApiClient(){}

    static {
        //静态代码块可以做些初始化操作
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);

        /*ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();

        builder.connectionSpecs(Collections.singletonList(spec));*/

        /*CustomTrust customTrust = new CustomTrust();
        customTrust.sslCustomTrust(builder);*/

        //builder.certificatePinner(new CertificatePinner.Builder().add("publicobject.com", "sha256/afwiKY3RxoMmLkuRW1l7QsPZTJPwDS2pdDROQjXw8ig=").build());


        mOkHttpClient = builder.build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();

    }


    public static ApiService getApiService(){
        synchronized (new Object()){
            if(mApiService == null){
                mApiService = mRetrofit.create(ApiService.class);
            }
            return mApiService;
        }
    }
}
