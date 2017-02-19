package com.wtc.xmut.taoschool.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.ToastUtils;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }
    private void init(){
        initView();
        initEvent();
    }

    private void initEvent() {
        btn_logout.setOnClickListener(this);
    }

    private void initView() {
        btn_logout = (Button) findViewById(R.id.btn_logout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logout:
                Logout();
                break;
        }
    }
    //退出的方法
    private void Logout() {
        AlertDialog.Builder alertDialog = new  AlertDialog.Builder(this);
        alertDialog.setTitle("确认退出?");
        alertDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                ToastUtils.showToast(getApplicationContext(),"登出成功");
                PrefUtils.setString(getApplicationContext(),PrefUtils.USER_NUMBER,"");
                finish();
            }
        });
        alertDialog.setNegativeButton("取消",null);
        alertDialog.show();
    }
}
