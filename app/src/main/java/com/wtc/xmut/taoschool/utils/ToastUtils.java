package com.wtc.xmut.taoschool.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created By lovec on 2017/1/8.
 */

public class ToastUtils {

    public static void showToast(Context context ,String content){
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }


}
