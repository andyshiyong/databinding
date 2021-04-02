package com.xmzt.databinding.glide;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.xmzt.databinding.rxjava2.AsyncUtil;
import com.xmzt.utils.AppUtils;
import com.xmzt.utils.ToastUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Glide下载保存图片
 * @describe
 */
public class DownloadSaveImgUtils {
    public static void downloadImg(Context contexts, String imgUrl) {
        ProgressDialog mSaveDialog = ProgressDialog.show(AppUtils.getTopActivity(), "保存图片", "图片正在保存中，请稍等...", true);
        AsyncUtil.async(new Function<String, Boolean>() {
            @Override
            public Boolean apply(String o) throws Exception {
                try {
                    Bitmap bitmap = Glide.with(AppUtils.getAppContext()).asBitmap().load(imgUrl).submit().get();
                    saveFile(bitmap);
                }catch (Exception e){
                    return false;
                }
                return true;
            }
        }, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean isSaved) throws Exception {
                mSaveDialog.dismiss();
                if(isSaved){
                    ToastUtils.showShort("图片保存成功！");
                }else {
                    ToastUtils.showShort("图片保存失败！");
                }
            }
        });
    }

    /**
     * 保存图片
     *
     * @param bm
     * @throws IOException
     */
    public static void saveFile(Bitmap bm) throws IOException {
        File dirFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        String fileName = dirFile.getAbsolutePath() + "/DCIM/Camera/" + UUID.randomUUID().toString() + ".jpg";
        File myCaptureFile = new File(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        //把图片保存后声明这个广播事件通知系统相册有新图片到来
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        AppUtils.getAppContext().sendBroadcast(intent);
    }
}
