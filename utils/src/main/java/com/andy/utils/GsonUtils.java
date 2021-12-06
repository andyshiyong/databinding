package com.andy.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * detail: Gson 工具类
 */
public final class GsonUtils {

    private GsonUtils() {
    }
    //创建单例
    public static class SingleonHolder {
        private static final GsonUtils instance = new GsonUtils();
    }
    /**
     * 创建 GsonBuilder
     * @param serializeNulls 是否序列化 null 值
     * @return {@link GsonBuilder}
     */
    public GsonBuilder createGson(final boolean serializeNulls) {
        GsonBuilder builder = new GsonBuilder();
        if (serializeNulls) builder.serializeNulls();
        return builder;
    }
    //获取单例
    public static GsonUtils getInstance() {
        return SingleonHolder.instance;
    }
    // 日志 TAG
    private static final String TAG = GsonUtils.class.getSimpleName();
    // Object 转 JSON 字符串
    private final Gson TO_GSON = createGson(true).create();
    // JSON 字符串转 T Object
    private final Gson FROM_GSON = createGson(true).create();

    // ============
    // = 转换方法 =
    // ============

    /**
     * 将对象转换为 JSON String
     * @param object {@link Object}
     * @return JSON String
     */
    public String toJson(final Object object) {
        return toJson(object, TO_GSON);
    }

    /**
     * 将对象转换为 JSON String
     * @param object {@link Object}
     * @param gson   {@link Gson}
     * @return JSON String
     */
    public String toJson(final Object object, final Gson gson) {
        if (gson != null) {
            try {
                return gson.toJson(object);
            } catch (Exception e) {
                Log.e("GsonUtils", "toJson: ",e );
            }
        }
        return null;
    }

    // =

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json     JSON String
     * @param classOfT {@link Class} T
     * @param <T>      泛型
     * @return instance of type
     */
    public <T> T fromJson(final String json, final Class<T> classOfT) {
        return fromJson(json, classOfT, FROM_GSON);
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json     JSON String
     * @param classOfT {@link Class} T
     * @param gson     {@link Gson}
     * @param <T>      泛型
     * @return instance of type
     */
    public <T> T fromJson(final String json, final Class<T> classOfT, final Gson gson) {
        if (gson != null) {
            try {
                return gson.fromJson(json, classOfT);
            } catch (Exception e) {
                Log.e("GsonUtils", "fromJson: ",e );
            }
        }
        return null;
    }

    // =
    public final <T> List<T> parseArray(String jsonArray, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if(!TextUtils.isEmpty(jsonArray)){
            try {
                Type type=GsonUtils.getInstance().getListType(clazz);
                Object obj=fromJson(jsonArray,type);
                if(obj instanceof List){
                    return (List<T>) obj;
                }
            } catch (Exception e) {
                Log.e("GsonUtils", "parseArray: ",e );
            }
        }
        return list;
    }
    /**
     * 将 JSON String 映射为指定类型对象
     * @param json    JSON String
     * @param typeOfT {@link Type} T
     * @param <T>     泛型
     * @return instance of type
     */
    public <T> T fromJson(final String json, final Type typeOfT) {
        return fromJson(json, typeOfT, FROM_GSON);
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json    JSON String
     * @param typeOfT {@link Type} T
     * @param gson    {@link Gson}
     * @param <T>     泛型
     * @return instance of type
     */
    private  <T> T fromJson(final String json, final Type typeOfT, final Gson gson) {
        if (gson != null) {
            try {
                return gson.fromJson(json, typeOfT);
            } catch (Exception e) {
                Log.e("GsonUtils", "fromJson: ",e );
            }
        }
        return null;
    }
    /**
     * 判断字符串是否 JSON 格式
     * @param json 待校验 JSON String
     * @return {@code true} yes, {@code false} no
     */
    public boolean isJSON(final String json) {
        JsonElement jsonElement;
        try {
            jsonElement = new JsonParser().parse(json);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonObject()) {
            return false;
        }
        return true;
    }


    // ========
    // = Type =
    // ========

    /**
     * 获取 Array Type
     * @param type Bean.class
     * @return Bean[] Type
     */
    public Type getArrayType(final Type type) {
        return TypeToken.getArray(type).getType();
    }

    /**
     * 获取 List Type
     * @param type Bean.class
     * @return List<Bean> Type
     */
    public Type getListType(final Type type) {
        return TypeToken.getParameterized(List.class, type).getType();
    }

    /**
     * 获取 Set Type
     * @param type Bean.class
     * @return Set<Bean> Type
     */
    public Type getSetType(final Type type) {
        return TypeToken.getParameterized(Set.class, type).getType();
    }

    /**
     * 获取 Map Type
     * @param keyType   Key.class
     * @param valueType Value.class
     * @return Map<Bean> Type
     */
    public Type getMapType(final Type keyType, final Type valueType) {
        return TypeToken.getParameterized(Map.class, keyType, valueType).getType();
    }

    /**
     * 获取 Type
     * @param rawType       raw type
     * @param typeArguments type arguments
     * @return Type
     */
    public Type getType(final Type rawType, final Type... typeArguments) {
        return TypeToken.getParameterized(rawType, typeArguments).getType();
    }
}