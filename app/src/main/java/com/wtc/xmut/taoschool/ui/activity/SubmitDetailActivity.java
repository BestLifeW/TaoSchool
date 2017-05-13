package com.wtc.xmut.taoschool.ui.activity;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;
import com.google.gson.Gson;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.SubmitDetail;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;
import com.wtc.xmut.taoschool.utils.ToastUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

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
    @BindView(R.id.tv_buytime)
    TextView tvbuytime;



    private int shopId;
    private String username;
    private XutilsUtils utils;
    private SubmitDetail submitDetail;
    private static final String TAG = "SubmitDetailActivity";
    private Button btn_ok;
    private LinearLayout ll_buytime;
    private TimePickerView pvTime;
    private Dialog show;
    private Dialog submitDialog;
    private String time;
    private LinearLayout ll_address;

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

        ll_buytime = (LinearLayout) findViewById(R.id.ll_buytime);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        ll_buytime.setOnClickListener(this);
        ll_address.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initDate() {
        //初始化网络数据
        Log.i(TAG, "initDate: " + ServerApi.GETSUBMITDETAIL + "/" + shopId + "/" + username);
        utils.post(ServerApi.GETSUBMITDETAIL + "/" + shopId + "/" + username, null, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.i(TAG, "onResponse: " + result);
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
        Log.i(TAG, "parseResult: " + submitDetail.toString());
        fillView();
        btn_ok.setOnClickListener(this);
    }

    private void fillView() {
        if (submitDetail != null) {
            tvShopname.setText(submitDetail.getShopname() + "");
            tvBuymoney.setText("￥" + submitDetail.getPrice() + "");
            tvShopmenoy.setText("￥" + submitDetail.getPrice() + "");
            tvUsername.setText(submitDetail.getName()+"|"+submitDetail.getTelephone());
            tvAddress.setText("学院:"+submitDetail.getCollege()+"、楼号:"+submitDetail.getFloor()+"、寝号:"+submitDetail.getDormitory());
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
        switch (v.getId()) {
            case R.id.btn_ok:
                AddOrder();
                break;
            case R.id.ll_buytime:
                choseTime();
                break;
            case R.id.ll_address:
                choseAddress();
                break;
            default:
                break;
        }

    }

    private void choseAddress() {
        DialogUIUtils.showMdAlert(SubmitDetailActivity.this, "是否切换选择新地址？", null, true, false, new DialogUIListener() {
            @Override
            public void onPositive() {
                Intent intent = new Intent(getApplicationContext(),PersonActivity.class);
                startActivity(intent);
            }

            @Override
            public void onNegative() {

            }
        }).show();
    }

    private void choseTime() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        ToastUtils.showToast(getApplication(), "选择");
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 4, 16);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 11, 28);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                tvbuytime.setText(getTime(date));
            }
        })
                .setType(TimePickerView.Type.MONTH_DAY_HOUR_MIN)
                .setLabel("年", "月", "日", "时", "分", "秒") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .build();
        pvTime.show();
    }

    private void AddOrder() {
        show = DialogUIUtils.showMdAlert(this, "确认信息", "确认提交订单？建议在提交前，先电话联系卖家！", false, false, new DialogUIListener() {
            @Override
            public void onPositive() {
                //提交订单操作

                time = tvbuytime.getText().toString();
                if (!time.contains("请选择")){
                    addOrderinWeb();
                    submitDialog = DialogUIUtils.showLoading(getApplicationContext(), "提交中", false, false, false, true).show();
                }else {
                   Toasty.info(getApplicationContext(),"请选择时间",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onNegative() {
            }
        }).show();
    }


    private void addOrderinWeb() {
        Log.i(TAG, "addOrderinWeb: "+time);
        String newid = shopId+"";
            HashMap<String, String> map = new HashMap<>();
            map.put("shopid", newid);
            map.put("buyerusername", username);
            map.put("sellerusername", submitDetail.getUsername());
            map.put("time", time);
            map.put("state","拍下");
            utils.post(ServerApi.ADDORDER, map, new XutilsUtils.XCallBack() {
                @Override
                public void onResponse(String result) {
                    Log.i(TAG, "onResponse: " + result);
                    if (result.contains("成功")) {
                        Toasty.success(getApplicationContext(), "提交成功", Toast.LENGTH_LONG).show();
                        submitDialog.dismiss();
                        finish();
                    } else {
                        Toasty.error(getApplicationContext(), "提交失败", Toast.LENGTH_LONG).show();
                        submitDialog.dismiss();
                    }
                }
                @Override
                public void onResponseFail() {
                    Toasty.error(getApplicationContext(), "提交失败", Toast.LENGTH_LONG).show();
                    submitDialog.dismiss();
                }
            });

    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initDate();
    }
}
