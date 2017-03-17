package com.wtc.xmut.taoschool.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.Shop;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShopDetailActivity extends AppCompatActivity {

    private SimpleDraweeView mSld_usericon;
    private TextView mTv_money;
    private TextView mTv_user_name;
    private TextView mTv_fromwhere;
    private TextView mTv_shop_summary;
    private SimpleDraweeView mSdv_shop_pic;
    private Button mBtn_buy;

    private Shop shop;
    private static final String TAG = "ShopDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopdetail);
        init();
        int ShopId = (int) getIntent().getExtras().get("ShopId");
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ServerApi.GETSHOPBYID + ShopId)
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                SnackbarUtils.ShowSnackbar(getCurrentFocus(), "网络连接失败，请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ParseJson(response.body().string());
            }
        });

    }

    private void ParseJson(String json) {
        Gson gson = new Gson();
        shop = gson.fromJson(json, Shop.class);
        // Log.i(TAG, "ParseJson: "+shop.toString());
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
        if (shop != null) {
            mTv_user_name.setText(shop.getUsername());
            mTv_money.setText(shop.getPrice() + "");
            mTv_shop_summary.setText(shop.getDescription());
            Uri shopuri = Uri.parse(ServerApi.SHOWPIC + shop.getPicture());
            Log.i(TAG, "initView: " + shopuri);
                mSdv_shop_pic.setImageURI(shopuri);
        }
    }

    private void initView() {
        mSld_usericon = (com.facebook.drawee.view.SimpleDraweeView) findViewById(R.id.sld_usericon);
        mTv_money = (TextView) findViewById(R.id.tv_money);
        mTv_user_name = (TextView) findViewById(R.id.tv_user_name);
        mTv_fromwhere = (TextView) findViewById(R.id.tv_fromwhere);
        mTv_shop_summary = (TextView) findViewById(R.id.tv_shop_summary);
        mSdv_shop_pic = (com.facebook.drawee.view.SimpleDraweeView) findViewById(R.id.sdv_shop_pic);
        mBtn_buy = (Button) findViewById(R.id.btn_buy);


    }
}
