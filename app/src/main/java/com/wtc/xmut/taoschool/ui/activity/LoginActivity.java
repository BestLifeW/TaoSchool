package com.wtc.xmut.taoschool.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.Service.UserService;
import com.wtc.xmut.taoschool.Service.impl.UserServiceImol;
import com.wtc.xmut.taoschool.domain.User;
import com.wtc.xmut.taoschool.utils.PrefUtils;

import java.io.IOException;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEt_usernumber;
    private EditText mEt_userpassword;
    private Button mBtn_signup;
    private TextView mTv_register;
    private String usernumber;
    private String password;
    private UserService userService;
    private static final String TAG = "LoginActivity";
    Gson gson = null;
    private ProgressBar progressBar2;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userService = new UserServiceImol();
       /* toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
       // getActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        initView();
        initEvent();
    }

    private void initEvent() {
        mTv_register.setOnClickListener(this);
        mBtn_signup.setOnClickListener(this);
    }

    //初始化布局
    private void initView() {
        mEt_usernumber = (EditText) findViewById(R.id.et_usernumber);
        mEt_userpassword = (EditText) findViewById(R.id.et_userpassword);
        mBtn_signup = (Button) findViewById(R.id.btn_signup);
        mTv_register = (TextView) findViewById(R.id.tv_register);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
    }

    private void Login() {



        progressBar2.setVisibility(View.VISIBLE);
        usernumber = mEt_usernumber.getText().toString().trim();
        password = mEt_userpassword.getText().toString().trim();
        if (TextUtils.isEmpty(usernumber) || TextUtils.isEmpty(password)) {
            Snackbar.make(getCurrentFocus(), "请输入内容", Snackbar.LENGTH_LONG).show();
            progressBar2.setVisibility(View.INVISIBLE);
        } else {
            getUser();
        }
    }

    private void getUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = null;
                try {
                    user = userService.login(usernumber, password);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                Bundle data = new Bundle();
                if (user != null) {
                    if (user.getUsername() != null) {
                        data.putString("state", "登录成功");
                        PrefUtils.setString(getApplicationContext(), PrefUtils.USER_NUMBER,
                                usernumber);
                        ToMainActivity();
                        finish();
                        msg.setData(data);
                        handler.sendMessage(msg);
                    } else {
                        data.putString("state", "用户名或者密码错误");
                        msg.setData(data);
                        handler.sendMessage(msg);
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar2.setVisibility(View.INVISIBLE);
                        }
                    });

                    data.putString("state", "服务器连接失败");
                    msg.setData(data);
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("state");
            //ToastUtils.showToast(getApplicationContext(),val);
            Snackbar.make(getCurrentFocus(), val, Snackbar.LENGTH_LONG).show();
            progressBar2.setVisibility(View.INVISIBLE);
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                ToRegister();
                break;
            case R.id.btn_signup:
                Login();
                break;
        }
    }

    private void ToRegister() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();
    }

    private void ToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();
    }
}
