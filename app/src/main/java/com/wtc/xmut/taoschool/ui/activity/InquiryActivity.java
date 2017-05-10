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
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

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
    private XutilsUtils utils;
    private Dialog show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);
        ButterKnife.bind(this);
        utils = XutilsUtils.getInstance();
        username = PrefUtils.getString(getApplicationContext(), PrefUtils.USER_NUMBER, "");
    }

    @OnClick(R.id.rl_money)
    public void inputMoney() {
        DialogUIUtils.showAlert(this, null, "请输入出售的价格", "预估价格", null, "确定", "取消", false, false, false, new DialogUIListener() {
            @Override
            public void onPositive() {
            }

            @Override
            public void onNegative() {
            }

            @Override
            public void onGetInput(CharSequence input1, CharSequence input2) {
                newmoney = (String) input1;

                if (!(TextUtils.isEmpty(newmoney))) {
                    tvMoney.setText("￥:" + newmoney + "元");
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
        HashMap<String, String> inquiry = new HashMap<>();
        String title = etTitle.getText().toString().trim();
        String description = etSummary.getText().toString().trim();

        if (!(title.trim().equals("") || description.trim().equals("") || username.trim().equals("") || newmoney.trim().equals(""))) {
            inquiry.put("ishopname", title);
            inquiry.put("idescription", description);
            inquiry.put("iusername", username);
            inquiry.put("iprice", newmoney);
            inquiry.put("itime", str);

            update(inquiry);


        } else {
            SnackbarUtils.ShowSnackbar(getCurrentFocus(), "请输入完整信息");
            return;
        }
        show = DialogUIUtils.showLoading(getApplicationContext(), "发布中...", false, false, false, true).show();

    }

    /**
     * 提交数据
     *
     * @param inquiry
     */
    private void update(HashMap<String, String> inquiry) {
        utils.post(ServerApi.ADDINQUIRY, inquiry, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                if (result.contains("插入成功")) {
                    Toasty.success(getApplicationContext(), "提交成功", Toast.LENGTH_LONG).show();
                    show.dismiss();
                    finish();
                }
            }

            @Override
            public void onResponseFail() {
                Toasty.error(getApplicationContext(), "网络连接失败了", Toast.LENGTH_LONG).show();
                show.dismiss();
            }
        });
    }

}
