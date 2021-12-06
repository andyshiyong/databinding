package com.andy.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

public class SPUtils {
    public static final String COMMON_KEY_VALUE = "kankan";
    public static final String KEY_TOKEN = "KEY_TOKEN";
    public static final String KEY_SPLASHES = "KEY_SPLASHES";
    public static final String KEY_AD = "KEY_AD";
    public static final String KEY_HOME = "KEY_HOME";
    public static final String KEY_PARAMS = "KEY_PARAMS";

    public static void putString(String key, String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            return;
        }
        SharedPreferences sharedPreferences = AppUtils.getAppContext().getSharedPreferences(COMMON_KEY_VALUE, 0);
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static String getString(String key,String defValue) {
        SharedPreferences sharedPreferences = AppUtils.getAppContext().getSharedPreferences(COMMON_KEY_VALUE, 0);
        return sharedPreferences.getString(key, defValue);
    }

    public static void putInt(String key, int value) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        SharedPreferences sharedPreferences = AppUtils.getAppContext().getSharedPreferences(COMMON_KEY_VALUE, 0);
        sharedPreferences.edit().putInt(key, value).commit();
    }

    public static int getInt(String key,int defValue) {
        SharedPreferences sharedPreferences = AppUtils.getAppContext().getSharedPreferences(COMMON_KEY_VALUE, 0);
        return sharedPreferences.getInt(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        SharedPreferences sharedPreferences = AppUtils.getAppContext().getSharedPreferences(COMMON_KEY_VALUE, 0);
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defualtValue) {
        SharedPreferences sharedPreferences = AppUtils.getAppContext().getSharedPreferences(COMMON_KEY_VALUE, 0);
        return sharedPreferences.getBoolean(key, defualtValue);
    }

    public static void clear() {
        SharedPreferences sharedPreferences = AppUtils.getAppContext().getSharedPreferences(COMMON_KEY_VALUE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
