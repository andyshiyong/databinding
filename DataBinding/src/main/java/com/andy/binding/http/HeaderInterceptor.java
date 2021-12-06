package com.andy.binding.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.andy.utils.AppUtils;
import com.andy.utils.NetWorkUtils;
import com.andy.utils.VersionUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Header拦截器
 * 主要是设置一些公用的header的数据
 */
public class HeaderInterceptor implements Interceptor {

    private Map<String, String> headers;
    public HeaderInterceptor() {
        headers=new HashMap<>();
        headers.put("Accept", "*/*");
//        headers.put("token", "");
        headers.put("os","android");
        headers.put("client","android");
        Context context= AppUtils.getApp().getApplicationContext();
        headers.put("version", VersionUtils.getVersionCode(context)+"");
    }

    public void addHeader(@NonNull String key,@NonNull String value) {
        headers.put(key,value);
    }
    public void removeHeader(@NonNull String key) {
        headers.remove(key);
    }
    public HeaderInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }
    @SuppressLint("MissingPermission")
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (NetWorkUtils.isNetConnected()){
            Request request = chain.request();
            Request.Builder builder = request.newBuilder();
            String cookie = request.header("Cookie");
            if(!TextUtils.isEmpty(cookie)){
                headers.put("Cookie",cookie);
            }
            String token=request.header("token");
            if(!TextUtils.isEmpty(token)){
                headers.put("token",token);
            }
            int networkType=NetWorkUtils.getNetworkType();
            if(networkType==1){
                headers.put("NetType","WIFI");
            }else {
                headers.put("NetType","4G");
            }
            if (headers != null && headers.size() > 0) {
                Set<String> keys = headers.keySet();
                for (String headerKey : keys) {
                    builder.addHeader(headerKey, headers.get(headerKey));
                }
            }
            Response mResponse=chain.proceed(builder.build());
            return mResponse;
        } else {
            throw new ConnectException();
        }
    }
}
