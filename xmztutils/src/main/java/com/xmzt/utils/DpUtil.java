package com.xmzt.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class DpUtil {

    /**
     * dp转换成px
     */
    public static int dp2px(float dpValue){
        float scale=AppUtils.getAppContext().getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }
    /**
     * dp转换成px
     */
    public static int dpToRealPx(float dpValue){
        WindowManager wmManager=(WindowManager) AppUtils.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display= wmManager.getDefaultDisplay();
        display.getRealMetrics(dm);
        float scale= dm.density;
        return (int)(dpValue*scale+0.5f);
    }

    /**
     * px转换成dp
     */
    public static int px2dp(float pxValue){
        float scale=AppUtils.getAppContext().getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }
    /**
     * sp转换成px
     */
    public static int sp2px(float spValue){
        float fontScale=AppUtils.getAppContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*fontScale+0.5f);
    }
    /**
     * px转换成sp
     */
    public static int px2sp(float pxValue){
        float fontScale=AppUtils.getAppContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale+0.5f);
    }
}
