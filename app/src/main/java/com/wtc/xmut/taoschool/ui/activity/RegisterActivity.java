package com.wtc.xmut.taoschool.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.Service.UserService;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.User;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {


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
    @BindView(R.id.sp_college)
    Spinner sp_college;
    @BindView(R.id.et_floor)
    EditText etfloor;

    @BindView(R.id.progressBar3)
    ProgressBar mProgressBar;


    @BindView(R.id.et_dormitory)
    EditText etdormitory;
    private String name;
    private String username;
    private String userpassword;
    private UserService userService;
    private static final String TAG = "RegisterActivity";
    private String college;

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    private XutilsUtils utils;
    private String phone;
    private List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        utils = XutilsUtils.getInstance();
        ButterKnife.bind(this);
        //第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        list.add("软件学院");
        list.add("机械工程学院");
        list.add("建筑工程学院");
        list.add("电子电气学院");
        list.add("计科学院");
        list.add("环境工程学院");
        list.add("数理学院");
        list.add("商学院");
        list.add("管理科学学院");
        list.add("文化传播学院");
        list.add("设计艺术学院");
        list.add("人文科学学院");
        list.add("空信科学学院");
        list.add("继续教育学院");
        list.add("其他学院");

        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        sp_college.setAdapter(adapter);
        sp_college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
             setCollege(list.get(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    @OnClick(R.id.tv_returnLogin)
    void enterLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @OnClick(R.id.btn_register)
    void register() {
        // mProgressbar.setVisibility(View.VISIBLE);
        User user = new User();
        String selectcollege = getCollege();
        String floor = etfloor.getText().toString();
        username = etUsernumber.getText().toString();
        name = etUsername.getText().toString();
        userpassword = etUserpassword.getText().toString();
        phone = etphone.getText().toString();
        String etdormi = etdormitory.getText().toString();
        if (!(TextUtils.isEmpty(username) || TextUtils.isEmpty(name) || TextUtils.isEmpty(userpassword) || TextUtils.isEmpty(phone)||TextUtils.isEmpty(selectcollege)||TextUtils.isEmpty(floor)||TextUtils.isEmpty(etdormi))) {
            user.setUsername(username);
            user.setName(name);
            user.setPassword(userpassword);
            user.setCollege(selectcollege);
            user.setTelephone(phone);
            user.setFloor(floor);
            user.setDormitory(etdormi);
            insert(user);
        } else {
            Snackbar.make(getCurrentFocus(), "请输入完整信息", Snackbar.LENGTH_LONG).show();
            // mProgressbar.setVisibility(View.INVISIBLE);
        }

    }

    //注册的方法
    private void insert(User user) {
        HashMap<String, String> map = new HashMap();
        map.put("username", user.getUsername());
        map.put("password", user.getPassword());
        map.put("name", user.getName());
        map.put("telephone", user.getTelephone());
        map.put("college",user.getCollege());
        map.put("floor",user.getFloor());
        map.put("dormitory",user.getDormitory());
        utils.post(ServerApi.USERADD, map, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                Toasty.success(getApplicationContext(),"注册成功", Toast.LENGTH_LONG).show();
                mProgressBar.setVisibility(View.INVISIBLE);
                SuccessToLogin();
            }

            @Override
            public void onResponseFail() {
                SnackbarUtils.ShowSnackbar(getCurrentFocus(), "连接失败");
                mProgressBar.setVisibility(View.INVISIBLE);
            }

        });
    }

    private void SuccessToLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();
    }
}
