package com.andy.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
/**
 * 剪切板管理
 */

public class ClipboardUtils {
    public static final boolean copyText(String data) {
        try {
            // 获取系统剪贴板
            ClipboardManager clipboard = (ClipboardManager) AppUtils.getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）,其他的还有
            // newHtmlText、
            // newIntent、
            // newUri、
            // newRawUri
            ClipData clipData = ClipData.newPlainText(null, data);
            // 把数据集设置（复制）到剪贴板
            clipboard.setPrimaryClip(clipData);
            return true;
        }catch (Exception e){}
        return false;
    }
    /**
     * 获取剪切板的数据
     * @return
     */
    public static String getClipboardData(){
        try {
            ClipboardManager cm = (ClipboardManager)AppUtils.getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData=cm.getPrimaryClip();
            if(mClipData!=null&&mClipData.getItemCount()>0){
                ClipData.Item mItem=mClipData.getItemAt(0);
                if(mItem!=null){
                    return mItem.getText().toString();
                }
            }
        }catch (Exception e){}
        return "";
    }
    public static final int getClipboardDataLength() {
        String text = getClipboardData();
        return text != null ? text.length() : 0;
    }
}