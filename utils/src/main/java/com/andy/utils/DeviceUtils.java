package com.andy.utils;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;


/**
 * android 设备工具类
 * 主要功能获取设备唯一标识
 */
public class DeviceUtils {
    /**
     * The IMEI: 仅仅只对Android手机有效
     * 采用此种方法，需要在AndroidManifest.xml中加入一个许可：android.permission.READ_PHONE_STATE，并且用
     * 户应当允许安装此应用。作为手机来讲，IMEI是唯一的，它应该类似于 359881030314356（除非你有一个没有量产的手
     * 机（水货）它可能有无效的IMEI，如：0000000000000）。
     *
     * @return imei
     */
    public static String getIMEI(Context appContext) {
        try{
            TelephonyManager TelephonyMgr = (TelephonyManager) appContext.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            String szImei = TelephonyMgr.getDeviceId();
        }catch (Exception e){}
        return "";
    }
    /**
     * The WLAN MAC Address string
     * 是另一个唯一ID。但是你需要为你的工程加入android.permission.ACCESS_WIFI_STATE 权限，否则这个地址会为
     * null。Returns: 00:11:22:33:44:55 (这不是一个真实的地址。而且这个地址能轻易地被伪造。).WLan不必打开，
     * 就可读取些值。
     *
     * @return m_szWLANMAC
     */
    public static String getWLANMACAddress(Context appContext) {
        try{
            WifiManager wm = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);
            String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
            return m_szWLANMAC;
        }catch (Exception e){}
        return "";
    }
    /**
     * The Android ID
     * 通常被认为不可信，因为它有时为null。开发文档中说明了：这个ID会改变如果进行了出厂设置。并且，如果某个
     * Andorid手机被Root过的话，这个ID也可以被任意改变。无需任何许可。
     *
     * @return AndroidID
     */
    public static String getAndroidID(Context appContext) {
        try{
            String m_szAndroidID = Settings.Secure.getString(appContext.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            return m_szAndroidID;
        }catch (Exception e){}
        return "";
    }
    /**
     *获取build设备序列id
     */
    public static String getBuildSerialID() {
        try{
            String device =Build.DEVICE;
            String serial =Build.SERIAL;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                serial =Build.getSerial();
            }
            String ID =Build.ID;
            return device+serial+ID;
        }catch (Exception e){}
        return "";
    }
    /**
     * Combined Device ID
     * 综上所述，我们一共有五种方式取得设备的唯一标识。它们中的一些可能会返回null，或者由于硬件缺失、权限问题等
     * 获取失败。但你总能获得至少一个能用。所以，最好的方法就是通过拼接，或者拼接后的计算出的MD5值来产生一个结果。
     * 通过算法，可产生32位的16进制数据:9DDDF85AFF0A87974CE4541BD94D5F55
     *
     * @return
     */
    public static String getUniqueDeviceID(Context appContext) {
        String imei=getIMEI(appContext);
        String wlanMacAddress=getWLANMACAddress(appContext);
        String androidID=getAndroidID(appContext);
        String buildSerialID=getBuildSerialID();
        String uniqueDeviceID=MD5Utils.getMd5Value(imei+buildSerialID+wlanMacAddress+androidID);
        return uniqueDeviceID;
    }

    public static String getDeviceInfo() {
        return "android;"+getDeviceBrand()+";"+getSystemModel()+";android"+getSystemVersion();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  品牌
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机厂商名
     *
     * @return  手机厂商名
     */
    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }
}
