package com.wtc.xmut.taoschool.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dd.CircularProgressButton;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;
import com.google.gson.Gson;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.OrdersExt;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static com.wtc.xmut.taoschool.myApplication.mContext;

public class ConfirmActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_buyername)
    TextView tvBuyername;
    @BindView(R.id.tv_buyerphone)
    TextView tvBuyerphone;
    @BindView(R.id.tv_buycollege)
    TextView tvBuycollege;
    @BindView(R.id.tv_buysushe)
    TextView tvBuysushe;
    @BindView(R.id.tv_buytime)
    TextView tvBuytime;
    @BindView(R.id.im_shopimg)
    ImageView imShopimg;
    @BindView(R.id.tv_shopname)
    TextView tvShopname;
    @BindView(R.id.textView12)
    TextView textView12;
    @BindView(R.id.tv_shopmoney)
    TextView tvShopmoney;
    @BindView(R.id.btn_refuse)
    CircularProgressButton btnRefuse;
    @BindView(R.id.btn_agree)
    CircularProgressButton btnAgree;
    private XutilsUtils utils;
    private String shopid;
    private OrdersExt result;
    private static final String TAG = "ConfirmActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shopid = String.valueOf(getIntent().getExtras().get("shopid"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        utils = XutilsUtils.getInstance();

        init();

    }

    private void init() {
        initData();

    }

    private void initData() {
        utils.getCache(ServerApi.GETORDERBYSHOPID + shopid, null, false, 60 * 6000, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {

                parseResult(result);

            }

            @Override
            public void onResponseFail() {

            }
        });
    }

    private void parseResult(String result) {
        Gson gson = new Gson();
        this.result = gson.fromJson(result, OrdersExt.class);
        Log.i(TAG, "parseResult: " + result);
        fillData();
    }

    private void fillData() {
        if (result != null) {
            Log.i(TAG, "fillData: " + result.getOrdersstate());
            if (result.getOrdersstate().equals("卖家确认")) {
                btnRefuse.setVisibility(View.GONE);
                btnAgree.setText("联系买家");
            }
            tvBuyername.setText(result.getBuyerusername());
            tvBuyerphone.setText(result.getTelephone());
            tvBuycollege.setText(result.getCollege());
            tvBuysushe.setText(result.getFloor());
            tvBuytime.setText(result.getTime());
            tvShopname.setText(result.getShopname());
            tvShopmoney.setText("￥" + result.getPrice());
            String shopimg = result.getPicture();

            Glide.with(getApplicationContext()).load(ServerApi.SHOWPIC + shopimg).placeholder(R.drawable.loadding).into(imShopimg);

        }
    }

    //同意按钮
    @OnClick(R.id.btn_agree)
    public void submit() {
        if (!(btnAgree.getText().toString().equalsIgnoreCase("联系买家"))) {
            btnRefuse.setVisibility(View.GONE);
            HashMap<String, String> map = new HashMap<>();
            map.put("id", result.getOrderid() + "");
            map.put("state", "卖家确认");
            utils.post(ServerApi.UPDATEORDERBYID, map, new XutilsUtils.XCallBack() {
                @Override
                public void onResponse(String result) {
                    if (result.contains("成功")) {
                        Toasty.success(getApplicationContext(), "确认成功", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i <= 100; i++) {
                                    btnAgree.setProgress(i);
                                    if (i == 100) {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                finish();
                                            }
                                        }, 2000);
                                    }
                                }
                            }
                        }, 200);

                    }
                }

                @Override
                public void onResponseFail() {
                    Toasty.error(mContext, "服务器链接失败", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            DialogUIUtils.showMdAlert(ConfirmActivity.this, "联系买家", "是否拨打买家电话:" + result.getTelephone(), true, true, new DialogUIListener() {
                @Override
                public void onPositive() {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    //url:统一资源定位符
                    //uri:统一资源标示符（更广）
                    intent.setData(Uri.parse("tel:" + result.getTelephone()));
                    //开启系统拨号器
                    startActivity(intent);
                }

                @Override
                public void onNegative() {
                    return;
                }
            }).show();


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
    //取消按钮
    @OnClick(R.id.btn_refuse)
    public void cancel() {


        //if (!(btnRefuse.getText().toString().equalsIgnoreCase("已拒绝"))) {
            btnAgree.setVisibility(View.GONE);
            HashMap<String, String> map = new HashMap<>();
            map.put("id", result.getOrderid() + "");
            map.put("state", "卖家拒绝");
            utils.post(ServerApi.UPDATEORDERBYID, map, new XutilsUtils.XCallBack() {
                @Override
                public void onResponse(String result) {
                    if (result.contains("成功")) {
                        Toasty.error(getApplicationContext(), "已取消", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i <= 100; i++) {
                                    btnRefuse.setProgress(i);
                                    if (i == 100) {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                finish();
                                            }
                                        }, 2000);
                                    }
                                }
                            }
                        }, 200);

                    }
                }

                @Override
                public void onResponseFail() {
                    Toasty.error(mContext, "服务器链接失败", Toast.LENGTH_LONG).show();
                }
            });
        }
   // }
}
