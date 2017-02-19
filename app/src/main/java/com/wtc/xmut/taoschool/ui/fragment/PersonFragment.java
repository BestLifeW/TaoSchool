package com.wtc.xmut.taoschool.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.Service.UserService;
import com.wtc.xmut.taoschool.Service.impl.UserServiceImol;
import com.wtc.xmut.taoschool.domain.User;
import com.wtc.xmut.taoschool.ui.activity.SettingActivity;
import com.wtc.xmut.taoschool.utils.PrefUtils;

import java.io.IOException;


public class PersonFragment extends Fragment implements View.OnClickListener {

    private com.wtc.xmut.taoschool.view.CircleImageView mCiv_userhead;
    private TextView mTv_user_name;
    private TextView mTv_earn_how;
    private TextView mTv_publish_count;
    private TextView mTv_sale_count;
    private TextView mTv_like_count;

    private static final String TAG = "PersonFragment";
    private Context mContext;
    private View inflate;
    private final UserService userService;
    private User user;
    private String usernumber;
    private RelativeLayout rl_setting_setting;

    public PersonFragment() {
        Log.i(TAG, "PersonFragment: 先执行无参的构造函数");
        userService = new UserServiceImol();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "PersonFragment: oncreateView");
        this.mContext = getActivity();
        usernumber = PrefUtils.getString(getActivity(), PrefUtils.USER_NUMBER, "");
        inflate = inflater.inflate(R.layout.fragment_person, container, false);
        init();
        return inflate;
    }

    private void initView() {
        //mCiv_userhead = (com.wtc.xmut.taoschool.view.CircleImageView) find''h ViewById(R.id.civ_userhead);
        mTv_user_name = (TextView) inflate.findViewById(R.id.tv_user_name);
        mTv_earn_how = (TextView) inflate.findViewById(R.id.tv_earn_how);
        mTv_publish_count = (TextView) inflate.findViewById(R.id.tv_publish_count);
        mTv_sale_count = (TextView) inflate.findViewById(R.id.tv_sale_count);
        mTv_like_count = (TextView) inflate.findViewById(R.id.tv_like_count);
        rl_setting_setting = (RelativeLayout) inflate.findViewById(R.id.rl_setting_setting);
    }

    private void init() {
        initView();
        //初始化数据
        initDate();
        //初始化布局
        initEvent();
    }

    private void initEvent() {
        rl_setting_setting.setOnClickListener(this);
    }

    private void initDate() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    user = userService.findUserByName(usernumber);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                Bundle data = new Bundle();
                if (user != null) {
                    if (user.getUsername() != null) {
                        data.putString("state", "LoginSuccess");
                        msg.setData(data);
                        handler.sendMessage(msg);
                    }
                }
            }
        }).start();
    }

    public static PersonFragment newInstance() {
        Bundle args = new Bundle();
        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("state");
            assert val != null;
            if (val.equals("LoginSuccess")){
                mTv_user_name.setText(user.getUsername());
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_setting_setting:
                enterSetting();
                break;
            default:
                break;
        }
    }

    //进入设置界面
    private void enterSetting() {
        Intent intent = new Intent(mContext,SettingActivity.class);
        startActivity(intent);
    }
}
