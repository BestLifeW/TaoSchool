package com.wtc.xmut.taoschool.ui.activity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.wtc.xmut.taoschool.utils.OkHttpUtils;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;
import com.wtc.xmut.taoschool.utils.ToastUtils;

import java.io.IOException;
import java.util.HashMap;

import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class ShopDetailActivity extends AppCompatActivity {


    private SimpleDraweeView mSld_usericon;
    private TextView mTv_money;
    private TextView mTv_user_name;
    private TextView mTv_fromwhere;
    private TextView mTv_shop_summary;
    private SimpleDraweeView mSdv_shop_pic;
    private Button mBtn_buy;
    private boolean isLove = false;
    private ShopExt shop;
    private static final String TAG = "ShopDetailActivity";
    private ImageView mBtnLove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopdetail);
        ButterKnife.bind(this);
        init();
        final int ShopId = (int) getIntent().getExtras().get("ShopId");
        //ToastUtils.showToast(getApplicationContext(), ShopId + "");


        OkHttpUtils.getDateAsync(ServerApi.GETSHOPBYID + ShopId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                SnackbarUtils.ShowSnackbar(getCurrentFocus(), "网络连接失败，请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ParseJson(response.body().string());
                isLoveShow(ShopId);
            }
        });



    }

    private void isLoveShow(int shopId) {
        //进来判断是否有赞
        OkHttpUtils.getDateAsync(ServerApi.ISLOVESHOW + shop.getUsername() + "/" + shopId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    if (response.body().string().contains("有赞")){
                        Log.i(TAG, "onResponse: 有赞");
                        mBtnLove.setImageResource(R.drawable.love_red);

                    }else {
                        Log.i(TAG, "onResponse: 没赞");
                        mBtnLove.setImageResource(R.drawable.love_gray);
                    }
            }
        });
    }

    private void ParseJson(String json) {
        Gson gson = new Gson();
        shop = gson.fromJson(json, ShopExt.class);
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
            mTv_user_name.setText(shop.getName());
            mTv_money.setText(shop.getPrice() + "");
            mTv_shop_summary.setText(shop.getDescription());
            Uri shopuri = Uri.parse(ServerApi.SHOWPIC + shop.getPicture());
            Uri UserIconUri = Uri.parse(ServerApi.SHOWPIC + shop.getIconpath());
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
        mBtnLove = (ImageView) findViewById(R.id.iv_love);
        mBtnLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断点赞情况
                HashMap<String, String> map = new HashMap<>();
                map.put("shopid", shop.getId() + "");
                map.put("username", shop.getUsername());
                try {
                    addLove(map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void addLove(HashMap<String, String> map) throws Exception {
        OkHttpUtils.doPostAsync(ServerApi.ISLOVE, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toasty.error(getApplicationContext(), "服务器连接失败啦", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body().string().contains("插入成功")) {
                    ShopDetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBtnLove.setImageResource(R.drawable.love_red);
                            Toasty.custom(getApplicationContext(), "+1", R.drawable.love_red, Color.GRAY, Color.alpha(200), Toast.LENGTH_SHORT, true, true).show();
                        }
                    });
                }else {
                    ShopDetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBtnLove.setImageResource(R.drawable.love_gray);
                            Toasty.custom(getApplicationContext(), "-1", R.drawable.love_gray, Color.GRAY, Color.alpha(200), Toast.LENGTH_SHORT, true, true).show();
                        }
                    });
                }


            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void onClick() throws Exception {

    }
}
