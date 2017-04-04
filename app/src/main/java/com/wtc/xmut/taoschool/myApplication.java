package com.wtc.xmut.taoschool;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.xutils.x;

/**
 * Created by tianchaowang on 17-4-4.
 */

public class myApplication extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        mContext = getApplicationContext();
        super.onCreate();
        x.Ext.init(this);
        Fresco.initialize(this);
    }
}
