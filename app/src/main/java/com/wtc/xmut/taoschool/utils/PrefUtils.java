package com.wtc.xmut.taoschool.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created By lovec on 2017/1/27 0027.
 */

public class PrefUtils {


    public static  final  String USER_NUMBER="USERNUMBER";

    /**
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(Context context,String key,boolean defValue ){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }

    /**
     *
     * @param context
     * @param key
     * @param Value
     */
    public static void setBoolean(Context context, String key, boolean Value ){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,Value).apply();
    }

    /**
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */

    public static String getString(Context context,String key,String defValue ){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }

    /**
     *
     * @param context
     * @param key
     * @param Value
     */
    public static void setString(Context context,String key,String Value ){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key,Value).apply();
    }

    public static int getInt(Context context,String key,int defValue ){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }
    public static void setInt(Context context,String key,int Value ){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt(key,Value).apply();
    }
}
