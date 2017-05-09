package com.wtc.xmut.taoschool.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wtc.xmut.taoschool.R.id.et_summary;
import static com.wtc.xmut.taoschool.R.id.et_title;

public class InquiryActivity extends AppCompatActivity {

    @BindView(et_title)
    EditText etTitle;
    @BindView(et_summary)
    EditText etSummary;

    @BindView(R.id.iv_mall3)
    ImageView ivMall3;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.rl_money)
    RelativeLayout rlSelect;
    @BindView(R.id.btn_publish)
    Button btnPublish;
    private String newmoney;
    private String oldmoney;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);
        ButterKnife.bind(this);
        username = PrefUtils.getString(getApplicationContext(), PrefUtils.USER_NUMBER, "");
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
                    tvMoney.setText("￥：" + newmoney + "元");
                } else {
                    SnackbarUtils.ShowSnackbar(getCurrentFocus(), "未输入任何数据");
                }
            }
        }).show();
    }


    @OnClick(R.id.btn_publish)
    public void OkPublish() {
        //获取系统时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        HashMap<String, String> Shop = new HashMap<>();
        String title = etTitle.getText().toString().trim();
        String description = etSummary.getText().toString().trim();

        if (!(title.trim().equals("") || description.trim().equals("") || username.trim().equals("") || newmoney.trim().equals("") || oldmoney.trim().equals(""))) {
            Shop.put("shopname", title);
            Shop.put("description", description);
            Shop.put("username", username);
            Shop.put("price", newmoney);
            Shop.put("shoptime", str);
        } else {
            SnackbarUtils.ShowSnackbar(getCurrentFocus(), "请输入完整信息");
            return;
        }

        final Dialog show = DialogUIUtils.showLoading(getApplicationContext(), "发布中...", false, false, false, true).show();

    }

}
