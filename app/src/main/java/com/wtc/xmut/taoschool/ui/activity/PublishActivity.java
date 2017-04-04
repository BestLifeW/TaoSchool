package com.wtc.xmut.taoschool.ui.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishActivity extends AppCompatActivity implements View.OnClickListener {



    @BindView(R.id.et_title)
    EditText et_title;

    @BindView(R.id.et_summary)
    EditText et_summary;

    @BindView(R.id.tv_money)
    TextView tv_money;

    @BindView(R.id.tv_oldmoney)
    TextView tv_oldmoney;

    @BindView(R.id.btn_publish)
    Button btn_publish;

    @BindView(R.id.rl_money)
    RelativeLayout rl_money;

    @BindView(R.id.iv_addpic)
    ImageView iv_addpic;

    private String newmoney;
    private String oldmoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        intView();
    }

    private void intView() {
        iv_addpic = (ImageView) findViewById(R.id.iv_addpic);
        iv_addpic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_addpic:
              /*  new PhotoPostDialog(this);*/
                break;
        }
    }

    @OnClick(R.id.rl_money)
    public void inputMoney() {
        DialogUIUtils.showAlert(this, null, "请输入出售的价格", "价格", "原先的价格", "确定", "取消", false, false, false, new DialogUIListener() {
            @Override
            public void onPositive() {
            }
            @Override
            public void onNegative() {
            }
            @Override
            public void onGetInput(CharSequence input1, CharSequence input2) {
                newmoney = (String) input1;
                oldmoney = (String) input2;
                if (!(TextUtils.isEmpty(newmoney) || TextUtils.isEmpty(oldmoney))) {
                    tv_money.setText("￥：" + newmoney + "元");
                    tv_oldmoney.setText("原价：￥" + oldmoney + "元");
                    tv_oldmoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }else {
                    SnackbarUtils.ShowSnackbar(getCurrentFocus(),"未输入任何数据");
                }
            }
        }).show();
    }

    @OnClick(R.id.btn_publish)
    public void OkPublish(){
        DialogUIUtils.showLoading(getApplicationContext(),"提交中...",false,false,false,true).show();

    }


    @OnClick(R.id.iv_addpic)
    public  void  getPic(){}

}
