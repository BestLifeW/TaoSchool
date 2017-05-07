package com.wtc.xmut.taoschool.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.Service.UserService;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.User;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {



    @BindView(R.id.progressbar)
    ContentLoadingProgressBar mProgressbar;

    @BindView(R.id.et_usernumber)
    EditText etUsernumber;
    @BindView(R.id.et_userpassword)
    EditText etUserpassword;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_phone)
    EditText etphone;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.tv_returnLogin)
    TextView tvReturnLogin;
    @BindView(R.id.activity_login)
    NestedScrollView activityLogin;
    private String name;
    private String username;
    private String userpassword;
    private UserService userService;
    private static final String TAG = "RegisterActivity";
    private String string;
    private XutilsUtils utils;
    private String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        utils = XutilsUtils.getInstance();
        ButterKnife.bind(this);

    }
    @OnClick(R.id.tv_returnLogin)
    void enterLogin() {
        Intent intent  = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
    @OnClick(R.id.btn_register)
    void register(){
        mProgressbar.setVisibility(View.VISIBLE);
        User user = new User();
        username = etUsernumber.getText().toString();
        name = etUsername.getText().toString();
        userpassword = etUserpassword.getText().toString();
        phone = etphone.getText().toString();
        if (!(TextUtils.isEmpty(username)||TextUtils.isEmpty(name)||TextUtils.isEmpty(userpassword)||TextUtils.isEmpty(phone))){
            user.setUsername(username);
            user.setName(name);
            user.setPassword(userpassword);

            user.setTelephone(phone);
            insert(user);
        }else {
            Snackbar.make(getCurrentFocus(),"请输入完整信息",Snackbar.LENGTH_LONG).show();
            mProgressbar.setVisibility(View.INVISIBLE);
        }

    }

    //注册的方法
    private void insert(User user) {
        HashMap<String,String> map = new HashMap();
        map.put("username",user.getUsername());
        map.put("password",user.getPassword());
        map.put("name",user.getName());
        map.put("telephone",user.getTelephone());
        utils.post(ServerApi.USERADD, map, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                SnackbarUtils.ShowSnackbar(getCurrentFocus(),"注册成功");
                mProgressbar.setVisibility(View.INVISIBLE);
                SuccessToLogin();
            }

            @Override
            public void onResponseFail() {
                SnackbarUtils.ShowSnackbar(getCurrentFocus(),"连接失败");
                mProgressbar.setVisibility(View.INVISIBLE);
            }

        });
    }

    private void SuccessToLogin(){
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();
    }
}
