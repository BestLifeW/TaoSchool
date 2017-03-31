package com.wtc.xmut.taoschool.ui.activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.ShopExt;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;

import com.wtc.xmut.taoschool.utils.ToastUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;


import java.util.HashMap;

import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class ShopDetailActivity extends AppCompatActivity implements View.OnClickListener {


    private SimpleDraweeView mSld_usericon;
    private TextView mTv_money;
    private TextView mTv_user_name;
    private TextView mTv_fromwhere;
    private TextView mTv_shop_summary;
    private SimpleDraweeView mSdv_shop_pic;
    private Button mBtn_buy;
    private boolean isLove = false;
    private ShopExt shopExt;
    private static final String TAG = "ShopDetailActivity";
    private ImageView mBtnLove;
    private String username;
    private XutilsUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopdetail);
        ButterKnife.bind(this);
        username = PrefUtils.getString(getApplicationContext(), PrefUtils.USER_NUMBER,"");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        utils = XutilsUtils.getInstance();
        final int ShopId = (int) getIntent().getExtras().get("ShopId");
        init();

        utils.getCache(ServerApi.GETSHOPBYID + ShopId, null, true,60*1000*6, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                ParseJson(result);
                isLoveShow(ShopId);

            }
        });

    }

    private void isLoveShow(int shopId) {
        //进来判断是否有赞
        utils.get(ServerApi.ISLOVESHOW + username + "/" + shopId, null, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                if (result.equals("1")) {
                    mBtnLove.setImageResource(R.drawable.love_red);
                } else if (result.equals("0")) {
                    mBtnLove.setImageResource(R.drawable.love_gray);
                }
            }
        });
    }
//



    private void ParseJson(String json) {
        Log.i(TAG, "ParseJson: "+json);
        Gson gson = new Gson();
        shopExt = gson.fromJson(json, ShopExt.class);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initDate();
            }
        });
    }

    private void init() {
        initView();
    }

    private void initDate() {
        if (shopExt != null) {
            mTv_user_name.setText(shopExt.getName());
            mTv_money.setText("￥"+ shopExt.getPrice() + "");
            mTv_shop_summary.setText(shopExt.getDescription());
            Uri shopuri = Uri.parse(ServerApi.SHOWPIC + shopExt.getPicture());
            Uri UserIconUri = Uri.parse(ServerApi.SHOWPIC + shopExt.getIconpath());
            mSld_usericon.setImageURI(UserIconUri);
            mSdv_shop_pic.setImageURI(shopuri);
        }
    }

    private void initView() {
        mSld_usericon = (SimpleDraweeView) findViewById(R.id.sld_usericon);
        mTv_money = (TextView) findViewById(R.id.tv_money);
        mTv_user_name = (TextView) findViewById(R.id.tv_user_name);
        mTv_fromwhere = (TextView) findViewById(R.id.tv_fromwhere);
        mTv_shop_summary = (TextView) findViewById(R.id.tv_shop_summary);
        mSdv_shop_pic = (SimpleDraweeView) findViewById(R.id.sdv_shop_pic);
        mBtn_buy = (Button) findViewById(R.id.btn_buy);
        mBtn_buy.setOnClickListener(this);

        mBtnLove = (ImageView) findViewById(R.id.iv_love);
        mBtnLove.setOnClickListener(this);

    }


    private void addLove(HashMap<String, String> map) throws Exception {

        utils.post(ServerApi.ISLOVE, map, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                if (result.equals("插入成功")) {

                    mBtnLove.setImageResource(R.drawable.love_red);
                    Toasty.custom(getApplicationContext(), "+1", R.drawable.love_red, Color.GRAY, Color.alpha(200), Toast.LENGTH_SHORT, true, true).show();

                }else {
                 mBtnLove.setImageResource(R.drawable.love_gray);
                 Toasty.custom(getApplicationContext(), "-1", R.drawable.love_gray, Color.GRAY, Color.alpha(200), Toast.LENGTH_SHORT, true, true).show();
                }

            }
        });


/*

        OkHttpUtils.doPostAsync(ServerApi.ISLOVE, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toasty.error(getApplicationContext(), "服务器连接失败啦", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


            }
        });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_love:
                ToastUtils.showToast(getApplicationContext(),"颠倒了");
                addLike();
                break;
            case R.id.btn_buy:
                enterOrder();
        }

    }

    private void enterOrder() {
        if (username==null){
            SnackbarUtils.ShowSnackbar(getCurrentFocus(),"您还未登录");
        }else {
            Intent  intent = new Intent(getApplicationContext(),OrderActivity.class);
            intent.putExtra("username",username);
            intent.putExtra("shopid", shopExt.getId());
            startActivity(intent);
        }

    }

    private void addLike() {
        //判断点赞情况
        HashMap<String, String> map = new HashMap<>();
        map.put("shopid", shopExt.getId() + "");
        map.put("username", username);
        try {
            addLove(map);
        } catch (Exception e) {
            e.printStackTrace();
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
}
