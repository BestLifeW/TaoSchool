package com.wtc.xmut.taoschool.ui.activity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShopDetailActivity extends AppCompatActivity {

    @BindView(R.id.sld_usericon)
    SimpleDraweeView sldUsericon;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_fromwhere)
    TextView tvFromwhere;
    @BindView(R.id.tv_shop_summary)
    TextView tvShopSummary;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.sdv_shop_pic)
    SimpleDraweeView sdvShopPic;
    @BindView(R.id.lv_comment)
    ListView lvComment;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.btn_buy)
    Button btnBuy;
    @BindView(R.id.iv_love)
    ImageView ivLove;
    @BindView(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    private SimpleDraweeView mSld_usericon;
    private TextView mTv_money;
    private TextView mTv_user_name;
    private TextView mTv_fromwhere;
    private TextView mTv_shop_summary;
    private SimpleDraweeView mSdv_shop_pic;
    private Button mBtn_buy;

    private ShopExt shop;
    private static final String TAG = "ShopDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopdetail);
        ButterKnife.bind(this);

        init();
        int ShopId = (int) getIntent().getExtras().get("ShopId");
        Log.i(TAG, "onCreate: ");
        ToastUtils.showToast(getApplicationContext(), ShopId + "");
        OkHttpUtils.getDateAsync(ServerApi.GETSHOPBYID + ShopId, new Callback() {
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @OnClick(R.id.iv_love)
    public void onClick() throws Exception {
        //判断点赞情况
        HashMap<String,String> map = new HashMap<>();
        map.put("shopid",shop.getId()+"");
        map.put("username",shop.getUsername());
        OkHttpUtils.doPostAsync(ServerApi.ADDLIKE, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toasty.error(getApplicationContext(),"服务器连接失败啦",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Toasty.custom(getApplicationContext(),"+1",R.drawable.love_red, Color.GRAY,Color.alpha(200),Toast.LENGTH_SHORT,true,true).show();
            }
        });
    }
}
