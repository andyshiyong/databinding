package com.andy.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String decimalFormat(double value) {
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(value);
        } catch (Exception e) {
            return "0.00";
        }
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    /**不区分大小写替代*/
    public static String replaceAll(String input, String regex, String replacement) {
        try {
            Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(input);
            String result = m.replaceAll(replacement);
            return result;
        } catch (Exception e) {
            return input;
        }
    }

    public static String getStarMobile(String mobile) {
        if (!TextUtils.isEmpty(mobile)) {
            if (mobile.length() >=11){
                return mobile.substring(0,3) +"****" + mobile.substring(7, mobile.length());
            }
        }else {
            return "";
        }
        return mobile;

    }

    public static String getBankCard(String bankcard) {
        if (!TextUtils.isEmpty(bankcard)) {
            if (bankcard.length() >=9){
                return bankcard.substring(0,3) +"**********" + bankcard.substring(bankcard.length()-4, bankcard.length());
            }
        }else {
            return "";
        }
        return bankcard;

    }

    public static String getIdCard(String idCard) {
        if (!TextUtils.isEmpty(idCard)) {
            if (idCard.length() >=7){
                return idCard.substring(0,3) +"**********" + idCard.substring(idCard.length()-3, idCard.length());
            }
        }else {
            return "";
        }
        return idCard;

    }

    public static String getName(String name) {
        if (!TextUtils.isEmpty(name)) {
            if (name.length() > 0){
                return "*" + name.substring(name.length()-1, name.length());
            }
        }
        return name;

    }

    public static String subStringCN(final String str, final int maxLength) {
        if (str == null) {
            return str;
        }
        String suffix = "...";
        int suffixLen = suffix.length();
        final StringBuffer sbuffer = new StringBuffer();
        final char[] chr = str.trim().toCharArray();
        int len = 0;
        for (int i = 0; i < chr.length; i++) {
            if (chr[i] >= 0xa1) {
                len += 2;
            } else {
                len++;
            }
        }

        if(len<=maxLength){
            return str;
        }
        len = 0;
        for (int i = 0; i < chr.length; i++) {
            if (chr[i] >= 0xa1) {
                len += 2;
                if (len + suffixLen > maxLength) {
                    break;
                }else {
                    sbuffer.append(chr[i]);
                }
            } else {
                len++;
                if (len + suffixLen > maxLength) {
                    break;
                }else {
                    sbuffer.append(chr[i]);
                }
            }
        }
        sbuffer.append(suffix);
        return sbuffer.toString();
    }

    public static String handleText(String str, int maxLen) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int count = 0;
        int endIndex=0;
        for (int i = 0; i < str.length(); i++) {
            char item = str.charAt(i);
            if (item < 128) {
                count = count + 1;
            } else {
                count = count + 2;
            }
            if(maxLen==count || (item>=128 && maxLen+1==count)){
                endIndex=i;
            }
        }
        if (count <= maxLen) {
            return str;
        } else {

            return str.substring(0, endIndex) + "...";
        }

    }
    public static String convertDoubleStr(String str) {
        if(str!=null){
            String replaceFirstStr=str.replaceFirst("\\.","@#");
            String replaceAllStr=replaceFirstStr.replaceAll("\\.","");
            String resultStr=replaceAllStr.replaceFirst("@#",".");
            return resultStr;
        }
        return null;
    }
}
