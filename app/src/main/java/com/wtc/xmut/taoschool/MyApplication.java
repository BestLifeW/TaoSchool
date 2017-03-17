package com.wtc.xmut.taoschool;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 作者 By lovec on 2017/2/22 0022.21:05
 * 邮箱 lovecanon0823@gmail.com
 */

public class MyApplication extends Application {

    public static Context mContext;
    @Override
    public void onCreate() {
        mContext = getApplicationContext();
        super.onCreate();
        Fresco.initialize(this);

    }
}
