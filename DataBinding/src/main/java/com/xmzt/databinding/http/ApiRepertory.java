package com.xmzt.databinding.http;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.QueryMap;

public class ApiRepertory{
    private OkHttpClient okHttpClient;
    private HeaderInterceptor headerInterceptor=new HeaderInterceptor();

    private final HashMap<String, Retrofit> mRetrofitMap = new HashMap<>();
    private Retrofit mRetrofit;
    private final HashMap<String, Api> mApiMap = new HashMap<>();
    private Api mTApi;
    /**
     * 超时时间
     */
    public static final int DEFAULT_TIMEOUT = 10*60;

    private ApiRepertory() {
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .addInterceptor(headerInterceptor)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        mRetrofit = createRetrofit(Api.baseApiUrl);
        mRetrofitMap.put(Api.baseApiUrl,mRetrofit);
    }
    private Retrofit createRetrofit(String baseUrl) {
        mRetrofit=mRetrofitMap.get(baseUrl);
        if(mRetrofit==null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
            mRetrofitMap.put(baseUrl,mRetrofit);
        }
        return mRetrofit;
    }
    public void addHeader(@NonNull String key, @NonNull String value) {
        headerInterceptor.addHeader(key,value);
    }

    public void addToken(String token) {
        if(TextUtils.isEmpty(token)){
            headerInterceptor.removeHeader("token");
        }else {
            addHeader("token",token);
        }
    }
    //创建单例
    public static class SingleonHolder {
        private static final ApiRepertory instance = new ApiRepertory();
    }

    //获取单例
    public static ApiRepertory getInstance() {
        return SingleonHolder.instance;
    }

    public <TApi extends Api> TApi getApiService(Class<TApi> apiClass,String baseUrl) {
        mTApi=mApiMap.get(apiClass.getName());
        Retrofit mRetrofit=createRetrofit(baseUrl);
        if(mTApi==null){
            mTApi = mRetrofit.create(apiClass);
            mApiMap.put(apiClass.getName(),mTApi);
        }
        return (TApi) mTApi;
    }
    public void clear(Class<Api> apiClass,String baseUrl){
        mApiMap.remove(apiClass.getName());
        mRetrofitMap.remove(baseUrl);
    }
    public void clear(){
        mApiMap.clear();
        mRetrofitMap.clear();
    }
}