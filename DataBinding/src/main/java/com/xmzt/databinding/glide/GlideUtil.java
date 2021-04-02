package com.xmzt.databinding.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.xmzt.databinding.R;
import com.xmzt.utils.AppUtils;
import com.xmzt.utils.DpUtil;

import java.io.File;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 图片工具类
 */
public class GlideUtil {
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .centerCrop()
                .into(imageView);
    }
    public static void loadImage(ImageView imageView, String url,int icon_default) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(icon_default)
                .error(icon_default)
                .centerCrop()
                .into(imageView);
    }
    public static void loadDefaultImage(ImageView imageView, String url,int icon_default) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(icon_default)
                .error(icon_default)
                .into(imageView);
    }
    /**
     * 加载底部圆角图片
     * @param imageView
     * @param bitmap
     * @param radius 单位dp
     */
    public static void loadImgBottomC(ImageView imageView,Bitmap bitmap,int radius) {
        int radiusDp= DpUtil.dp2px(radius);
        MultiTransformation<Bitmap> transform=new MultiTransformation<Bitmap>(new CenterCrop(),new RoundedCornersTransformation(radiusDp, 0,
                RoundedCornersTransformation.CornerType.BOTTOM));
        Glide.with(imageView.getContext())
                .load(bitmap)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(transform)
                .into(imageView);
    }
    /**
     * 加载底部圆角图片
     * @param imageView
     * @param bitmap
     * @param radius 单位dp
     */
    public static void loadImgBottomC(ImageView imageView,byte[] bitmap,int radius) {
        int radiusDp= DpUtil.dp2px(radius);
        MultiTransformation<Bitmap> transform=new MultiTransformation<Bitmap>(new CenterCrop(),new RoundedCornersTransformation(radiusDp, 0,
                RoundedCornersTransformation.CornerType.BOTTOM));
        Glide.with(imageView.getContext())
                .load(bitmap)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(transform)
                .into(imageView);
    }
    /**
     * 加载顶部圆角图片
     * @param imageView
     * @param url
     */
    @BindingAdapter({"imgTopCUrl"})
    public static void loadImgTopC(ImageView imageView,String url) {
        int radius= DpUtil.dp2px(4);
        MultiTransformation<Bitmap> transform=new MultiTransformation<Bitmap>(new CenterCrop(),new RoundedCornersTransformation(radius, 0,
                RoundedCornersTransformation.CornerType.TOP));
        Glide.with(imageView.getContext())
                .load(TextUtils.isEmpty(url)?R.drawable.icon_default:url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .transform(transform)
                .into(imageView);
    }
    public static void loadImgTopC(ImageView imageView,String url,int radius) {
        int radiusDp= DpUtil.dp2px(radius);
        MultiTransformation<Bitmap> transform=new MultiTransformation<Bitmap>(new CenterCrop(),new RoundedCornersTransformation(radiusDp, 0,
                RoundedCornersTransformation.CornerType.TOP));
        Glide.with(imageView.getContext())
                .load(TextUtils.isEmpty(url)?R.drawable.icon_default:url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .transform(transform)
                .into(imageView);
    }
    /**
     * 加载顶部圆角图片
     * @param imageView
     * @param url
     */
    @BindingAdapter({"imgLeftCUrl"})
    public static void loadImgLeftC(ImageView imageView,String url) {
        int radius= DpUtil.dp2px(4);
        MultiTransformation<Bitmap> transform=new MultiTransformation<Bitmap>(new CenterCrop(),new RoundedCornersTransformation(radius, 0,
                RoundedCornersTransformation.CornerType.LEFT));
        Glide.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .transform(transform)
                .into(imageView);
    }
    /**
     * 加载圆角图片
     * @param imageView
     * @param url
     */
    @BindingAdapter({"imgCUrl"})
    public static void loadImgC(ImageView imageView,String url) {
        int radius=DpUtil.dp2px(4);
        MultiTransformation<Bitmap> transform=new MultiTransformation<Bitmap>(new CenterCrop(),new RoundedCornersTransformation(radius, 0,
                RoundedCornersTransformation.CornerType.ALL));
        Glide.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .transform(transform)
                .into(imageView);
    }
    /**
     * 加载圆角图片
     * @param imageView
     * @param url
     * @param radius 半径单位dp
     */
    public static void loadImgRoundRadius(ImageView imageView,String url,int radius) {
        int dpRadius=DpUtil.dp2px(radius);
        MultiTransformation<Bitmap> transform=new MultiTransformation<Bitmap>(new CenterCrop(),new RoundedCornersTransformation(dpRadius, 0,
                RoundedCornersTransformation.CornerType.ALL));
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .dontAnimate()
                .transform(transform);

        if(TextUtils.isEmpty(url)){
            Glide.with(imageView.getContext())
                    .load(R.drawable.icon_default)
                    .apply(options)
                    .into(imageView);
        }else {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(options)
                    .into(imageView);
        }

    }
    /**
     * 加载圆角图片
     * @param imageView
     * @param url
     * @param radius 半径单位dp
     * @param placeholder 预加载的图片
     */
    public static void loadImgRoundRadius(ImageView imageView,String url,int radius,int placeholder) {
        int dpRadius=DpUtil.dp2px(radius);
        MultiTransformation<Bitmap> transform=new MultiTransformation<Bitmap>(new CenterCrop(),new RoundedCornersTransformation(dpRadius, 0,
                RoundedCornersTransformation.CornerType.ALL));
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(placeholder)
                .error(placeholder)
                .dontAnimate()
                .transform(transform);
        if(TextUtils.isEmpty(url)){
            Glide.with(imageView.getContext())
                    .load(placeholder)
                    .apply(options)
                    .into(imageView);
        }else {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(options)
                    .into(imageView);
        }
    }
    /**
     * 加载圆角图片
     * @param imageView
     * @param dataBytes
     * @param radius 半径单位dp
     */
    public static void loadImgRoundRadius(ImageView imageView, byte[] dataBytes, int radius) {
        int dpRadius=DpUtil.dp2px(radius);
        MultiTransformation<Bitmap> transform=new MultiTransformation<Bitmap>(new CenterCrop(),new RoundedCornersTransformation(dpRadius, 0,
                RoundedCornersTransformation.CornerType.ALL));
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .dontAnimate()
                .transform(new CenterCrop(),transform);
        Glide.with(imageView.getContext())
                .load(dataBytes)
                .apply(options)
                .into(imageView);
    }
    /**
     * 加载圆角图片
     * @param imageView
     * @param file
     * @param radius 半径单位dp
     * @param icon_default
     */
    public static void loadImgRoundRadius(ImageView imageView, File file, int radius,int icon_default) {
        int dpRadius=DpUtil.dp2px(radius);
        MultiTransformation<Bitmap> transform=new MultiTransformation<Bitmap>(new CenterCrop(),new RoundedCornersTransformation(dpRadius, 0,
                RoundedCornersTransformation.CornerType.ALL));
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(icon_default)
                .error(icon_default)
                .transform(new CenterCrop(),transform);
        Glide.with(imageView.getContext())
                .load(file)
                .apply(options)
                .into(imageView);
    }
    /**
     * 背景模糊遮罩
     * @param imageView
     * @param icon
     */
    public static void loadBlurTransformation(ImageView imageView,String url,int icon,int width, int height, CropTransformation.CropType cropType,int blurSampling) {
        // "14":模糊度；"3":图片缩放3倍后再进行模糊，缩放3-5倍个人感觉比较好。
        CropTransformation cropTransformation=new CropTransformation( width, height,cropType);
        BlurTransformation transform=new BlurTransformation( 14, blurSampling);
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(icon)
                .error(icon)
                .transform(cropTransformation,transform);
        if(TextUtils.isEmpty(url)){
            Glide.with(imageView.getContext())
                    .load(icon)
                    .apply(options)
                    .into(imageView);
        }else {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(options)
                    .into(imageView);
        }
    }
    /**
     * 加载半径为8dp的圆角图片
     * @param imageView
     * @param url
     */
    @BindingAdapter({"imgCRadius8Url"})
    public static void loadImgCRadius8(ImageView imageView,String url) {
        int dpRadius=DpUtil.dp2px(8);
        MultiTransformation<Bitmap> transform=new MultiTransformation<Bitmap>(new CenterCrop(),new RoundedCornersTransformation(dpRadius, 0,
                RoundedCornersTransformation.CornerType.ALL));
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(transform)
                .into(imageView);
    }
    /**
     * 加载圆图片
     * @param imageView
     * @param url
     */
    @BindingAdapter({"imgCircleUrl"})
    public static void loadImgCircle(ImageView imageView,String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .centerCrop()
                .circleCrop()
                .into(imageView);
    }
    /**
     * 加载圆图片
     * @param imageView
     * @param url
     * @param icon_default
     */
    public static void loadImgCircle(ImageView imageView,String url,int icon_default) {
        Glide.with(imageView.getContext())
                .load(TextUtils.isEmpty(url)?icon_default:url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(icon_default)
                .error(icon_default)
                .centerCrop()
                .circleCrop()
                .into(imageView);
    }
    /**
     * 加载圆角图片
     * @param imageView
     * @param radiusDp 圆角半径 单位dp
     * @param url 图片url
     */
    public static void loadImgRadius(ImageView imageView,int radiusDp,String url) {
        loadImgRoundRadius(imageView,url,radiusDp);
    }

    public static void loadImgRadius(ImageView imageView,int radiusDp,int url) {
        int radius=DpUtil.dp2px(radiusDp);
        MultiTransformation<Bitmap> transform=new MultiTransformation<Bitmap>(new CenterCrop(),new RoundedCornersTransformation(radius, 0,
                RoundedCornersTransformation.CornerType.ALL));
        Glide.with(imageView.getContext())
                .load(url).dontAnimate()
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(transform)
                .into(imageView);
    }
    public static void loadImgRadius(ImageView imageView,int radiusDp,String url,int icon_default) {
        int radius=DpUtil.dp2px(radiusDp);
        MultiTransformation<Bitmap> transform=new MultiTransformation<Bitmap>(new CenterCrop(),new RoundedCornersTransformation(radius, 0,
                RoundedCornersTransformation.CornerType.ALL));
        Glide.with(imageView.getContext())
                .load(TextUtils.isEmpty(url)?icon_default:url).dontAnimate()
                .placeholder(icon_default)
                .error(icon_default)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(transform)
                .into(imageView);
    }
    /**
     * 加载圆角图片
     * @param imageView
     * @param radiusDp 圆角半径 单位dp
     * @param url 图片url
     */
    public static void loadImgRadius(ImageView imageView,String url,int radiusDp,int w,int h) {
        int radius=DpUtil.dp2px(radiusDp);
        MultiTransformation<Bitmap> transform=new MultiTransformation<Bitmap>(new CenterCrop(),new RoundedCornersTransformation(radius, 0,
                RoundedCornersTransformation.CornerType.ALL));
        Glide.with(imageView.getContext())
                .load(url).dontAnimate()
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(transform)
                .override(w,h)
                .into(imageView);
    }
    /**
     * 带圆角的图片
     *
     * @param avator
     * @param main_user_iv
     */
    public static void setImageCircle(String avator, ImageView main_user_iv, int errorPic) {
        if (avator != null) {
            if (avator.length() > 0) {
                //圆角处理
                Glide.with(main_user_iv.getContext())
                        .load(avator)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(errorPic)
                        .error(errorPic)
                        .centerCrop()
                        .circleCrop()
                        .into(main_user_iv);
            } else {
                main_user_iv.setImageResource(errorPic);
            }
        } else {
            main_user_iv.setImageResource(errorPic);
        }
    }
    /**
     * 带圆角的图片
     *
     * @param avator
     * @param main_user_iv
     */
    public static void setImageCircle(Context context,String avator, ImageView main_user_iv, int errorPic) {
        if (avator != null) {
            if (avator.length() > 0) {
                //圆角处理
                Glide.with(context)
                        .load(avator)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(errorPic)
                        .error(errorPic)
                        .centerCrop()
                        .circleCrop()
                        .into(main_user_iv);
            } else {
                main_user_iv.setImageResource(errorPic);
            }
        } else {
            main_user_iv.setImageResource(errorPic);
        }
    }


    /**
     * 停止下载
     */
    public static void pauseRequests() {
        Glide.with(AppUtils.getAppContext()).pauseRequests();
    }

    /**
     * 恢复下载
     */
    public static void resumeRequests() {
        Glide.with(AppUtils.getAppContext()).resumeRequests();
    }
}
