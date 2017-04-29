package com.wtc.xmut.taoschool.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

import butterknife.internal.Utils;

public class MyPublishActivity extends AppCompatActivity {

    private String username;
    private XutilsUtils xutilsUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publish);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        xutilsUtils = XutilsUtils.getInstance();
        username = (String)getIntent().getExtras().get("username");
        init();
    }
    private void init(){
        initView();
        initData();
    }

    private void initData() {

    }


    private void initView() {
    }
}
