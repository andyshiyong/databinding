package com.andy.binding.http;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.security.interfaces.RSAPrivateKey;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class ResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Type baseDataType;
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    ResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        baseDataType=new TypeToken<BaseDataBean<String>>() {}.getType();
        this.gson = gson;
        this.adapter = adapter;
    }
    /**
     *  密钥字符串（经过base64编码）RSA私钥:
     */
    private static  final String PRIVATE_KEY = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEArMcJAsoXPSiHJN/PFN6feMHj0UP6JXRz/KxXBVYhEFWlZ6BaUxtO29NWteM90Xs9oqfDILhC3kpsyZBVz58U3wIDAQABAkEAoM5OcK4umtlRjtxPHBlnSzkKFAa0lo+gNBcilvDmrQ/B8iaYPDmhPo4DmvO9tJZouJqb9UtqAxaEfdT6824FIQIhANUGx7d8oPt1UlsW7w8+YaVRNl3lD3kzBBq+sGAOy3pTAiEAz6GyKjAAFhcEDe67q7pIXF9UVUSgcnHobVJNMjR94cUCIED/8Z6WI5S2pqtuowEWurqzvhAGXaNQorb6alzVBtdLAiEAmYabYbxqY+sS0WaEGD++v6axifcbmGQHk8Y2VHgh2G0CIFtYhdHKOkGiVAvRJqcVJd4jOmagNfNwtoTut4IzwKvo";
    /**
     * 得到私钥
     */
    public RSAPrivateKey getPrivateKey() {
        try {
            return RSAUtil.getPrivateKey(PRIVATE_KEY);
        } catch (Exception e) {
            Log.e("ResponseBodyConverter", "getPrivateKey: ",e);
        }
        return null;
    }
    @Override
    public T convert(ResponseBody value) throws IOException {
        String jsonBody=value.string();
        StringReader reader = new StringReader(jsonBody);
        JsonReader jsonReader = gson.newJsonReader(reader);
        Type jsonType = new TypeToken<BaseDataBean<Object>>() {}.getType();
        BaseDataBean<Object> dataBean = new Gson().fromJson(jsonBody, jsonType);
        try {
            if(dataBean!=null&&!TextUtils.isEmpty(dataBean.getSecret())){
                String secret=dataBean.getSecret();//aes密码
                String aesKey = RSAUtil.privateDecrypt(secret,getPrivateKey());
                String jsonData=AesUtil.decode(aesKey, (String) dataBean.getRel());
                Object object = gson.fromJson(jsonData, Object.class);
                try {
                    Object json = new JSONTokener(jsonData).nextValue();
                    if(json instanceof JSONObject){
                        dataBean.setRel(object);
                    }else if (json instanceof JSONArray){
                        dataBean.setRel(object);
                    }else {
                        dataBean.setRel(jsonData);
                    }
                    Log.e("", "convert: jsonData="+jsonData);
                } catch (Exception e) {
                    Log.e("", "json格式不对",e);
                    dataBean.setRel(jsonData);
                }
                //解密字符串 后台加密时间格式有问题
                String json=gson.toJson(dataBean);
                Log.d("httpResponseBody", "json: \n"+json);
                T result = adapter.fromJson(json);
                return result;
            }
            T result = adapter.read(jsonReader);
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
            return result;
        } finally {
            value.close();
        }
    }
}