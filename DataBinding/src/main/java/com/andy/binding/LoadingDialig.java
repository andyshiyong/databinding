package com.andy.binding;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * 加载dialog
 */
public class LoadingDialig extends AlertDialog{
    private Context mContext;

    public LoadingDialig(Context context) {
        super(context, R.style.dialog_loading);
        mContext = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT ;
        getWindow().setAttributes(params);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }
}
