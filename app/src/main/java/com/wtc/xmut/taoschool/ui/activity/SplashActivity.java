package com.wtc.xmut.taoschool.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.utils.PrefUtils;

public class SplashActivity extends AppCompatActivity {

    private String mUsernumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mUsernumber = PrefUtils.getString(getApplicationContext(), PrefUtils.USER_NUMBER, "");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mUsernumber.equals("")){
                ToHome();}else {
                    ToLogin();
                }
                finish();
            }
        }, 3000);
    }

    private void ToLogin() {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }


    //进入主页
    private void ToHome() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
