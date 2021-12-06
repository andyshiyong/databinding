package com.andy.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.core.widget.NestedScrollView;

public class ScreenUtils {

    private static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * Get Display
     * @param context Context for get WindowManager
     * @return Display
     */
    private static Display getDisplay(Context context) {
        WindowManager wm;
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            wm = activity.getWindowManager();
        } else {
            wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        if (wm != null) {
            return wm.getDefaultDisplay();
        }
        return null;
    }


    /**
     * Get Screen Width 获取应用屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    /**
     * Get Screen Height 获取应用屏幕高度
     */
    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    /**
     * Get Density
     */
    private static float getDensity(Context context) {
        return getDisplayMetrics(context).density;
    }

    /**
     * Get Dpi
     */
    private static int getDpi(Context context) {
        return getDisplayMetrics(context).densityDpi;
    }


    /**
     * Get Screen Real Height
     *
     * @param context Context
     * @return Real Height
     */
    public static int getRealHeight(Context context) {
        Display display = getDisplay(context);
        int screenHeight = 0;
        //版本API 19
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics dm = new DisplayMetrics();
            display.getRealMetrics(dm);
            screenHeight = dm.heightPixels;
            //版本API 14
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            try {
                screenHeight = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception e) {
                screenHeight = getScreenHeight(context);
            }
        }
        return screenHeight;
    }



    /**
     * Get Screen Real Width
     * @param context Context
     * @return Real Width
     */
    public static int getRealWidth(Context context) {
        Display display = getDisplay(context);
        int screenWidth = 0;
        //版本高于API 19
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics dm = new DisplayMetrics();
            display.getRealMetrics(dm);
            screenWidth = dm.widthPixels;
            //版本高于API 14 ,小于API 14
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            try {
                screenWidth = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
            } catch (Exception e) {
                screenWidth = getScreenWidth(context);
            }
        }
        return screenWidth;
    }
    /**
     *  判断导航栏是否可见
     * @return
     */
    public static boolean isNavVisible(Context context,int statusBarHeight) {
        int screenHeight=getScreenHeight(context);
        int screenRealHeight=getRealHeight(context);
        int rootViewY=screenHeight+statusBarHeight;
        if(rootViewY>=screenRealHeight||screenHeight==screenRealHeight){
            return false;
        }
        return true;
    }

    public static Bitmap convertViewToBitmap(View view){
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
    public static Bitmap screenShotToBitmap(View view){
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return bitmap;
    }

    /**
     * 截取scrollview的屏幕
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByView(NestedScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();//获取scrollView的屏幕高度
            scrollView.getChildAt(i).setBackgroundColor(
                    Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);//把创建的bitmap放到画布中去
        scrollView.draw(canvas);//绘制canvas 画布
        return bitmap;
    }
}
