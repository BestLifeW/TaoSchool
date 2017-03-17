package com.wtc.xmut.taoschool.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;
import com.wtc.xmut.taoschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonActivity extends AppCompatActivity {


    @BindView(R.id.rl_account_password)
    RelativeLayout mrl_account_password;
    private String oldPassword;
    private String newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.rl_account_password)
    void setPassWord() {
        DialogUIUtils.showAlert(this, null, "修改密码", "旧密码", "新密码", "确定", "取消", false, false, false, new DialogUIListener() {
            @Override
            public void onPositive() {

            }

            @Override
            public void onNegative() {
            }

            @Override
            public void onGetInput(CharSequence input1, CharSequence input2) {
                oldPassword = (String) input1;
                newPassword = (String) input2;

            }
        }).show();
    }
}
