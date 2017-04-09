package com.wtc.xmut.taoschool.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;
import com.google.gson.Gson;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.SubmitDetail;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wtc.xmut.taoschool.R.id.tv_shopmenoy;

public class SubmitDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.iv_shop)
    ImageView ivShop;
    @BindView(R.id.tv_shopname)
    TextView tvShopname;
    @BindView(tv_shopmenoy)
    TextView tvShopmenoy;
    @BindView(R.id.tv_jiaoyifangshi)
    TextView tvJiaoyifangshi;
    @BindView(R.id.relativeLayout3)
    RelativeLayout relativeLayout3;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_setaddress)
    LinearLayout llSetaddress;
    @BindView(R.id.tv_buymoney)
    TextView tvBuymoney;
    @BindView(R.id.btn_ok)
    Button btnOk;
    private int shopId;
    private String username;
    private XutilsUtils utils;
    private SubmitDetail submitDetail;
    private static final String TAG = "SubmitDetailActivity";
    private Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        utils = XutilsUtils.getInstance();

        //获取传过来的消息
        shopId = (int) getIntent().getExtras().get("shopid");
        username = PrefUtils.getString(getApplication(), PrefUtils.USER_NUMBER, "");
        init();
    }
    private void init() {
        initView();
        initDate();
    }

    private void initView() {
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initDate() {
        //初始化网络数据
        Log.i(TAG, "initDate: "+ServerApi.GETSUBMITDETAIL + "/" + shopId + "/" + username);
        utils.post(ServerApi.GETSUBMITDETAIL + "/" + shopId + "/" + username, null, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.i(TAG, "onResponse: "+result);
                parseResult(result);
            }
            @Override
            public void onResponseFail() {
                SnackbarUtils.ShowSnackbar(getCurrentFocus(), "网络连接失败！");
            }
        });
    }

    //解析方法
    private void parseResult(String result) {
        Gson gson = new Gson();
        submitDetail = gson.fromJson(result, SubmitDetail.class);
        Log.i(TAG, "parseResult: "+submitDetail.toString());
        fillView();
    }

    private void fillView() {
        if (submitDetail != null) {
            tvShopname.setText(submitDetail.getShopname()+"");
            tvBuymoney.setText("￥"+submitDetail.getPrice()+"");
            tvShopmenoy.setText("￥"+submitDetail.getPrice()+"");
            Uri UserIconUri = Uri.parse(ServerApi.SHOWPIC + submitDetail.getPicture());
            Glide.with(getApplication()).load(UserIconUri).placeholder(R.drawable.loadding).into(ivShop);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                AddOrder();
                break;
            default:
                break;
        }

    }

    private void AddOrder() {
        DialogUIUtils.showMdAlert(this, "确认信息", "确认提交订单？", false, false, new DialogUIListener() {
            @Override
            public void onPositive() {
                DialogUIUtils.showLoading(getApplicationContext(), "提交中", false, false, false, true).show();
            }

            @Override
            public void onNegative() {

            }
        }).show();

    }
}
