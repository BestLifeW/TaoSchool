package com.wtc.xmut.taoschool.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * 作者 By lovec on 2017/2/18 0018.13:23
 * 邮箱 lovecanon0823@gmail.com
 */

public class SnackbarUtils {

    public static void ShowSnackbar(View view,String msg){
        Snackbar.make(view,msg,Snackbar.LENGTH_LONG).show();
    }
}
