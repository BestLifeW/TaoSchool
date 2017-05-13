package com.wtc.xmut.taoschool.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.adpater.InquiryCommentAdapter;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.InquiryComment;
import com.wtc.xmut.taoschool.domain.InquiryExt;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;
import com.wtc.xmut.taoschool.utils.ToastUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;
import com.wtc.xmut.taoschool.view.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InquiryDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private XutilsUtils utils;
    private int inquiryid;
    private InquiryExt inquiryExt;
    private static final String TAG = "InquiryDetailActivity";
    private CircleImageView mSld_usericon;
    private TextView mTv_money;
    private TextView mTv_user_name;
    private TextView mTv_fromwhere;
    private TextView mTv_shop_summary;
    private ImageView mIv_addComment;
    private Button mBtn_buy;
    private RelativeLayout rl_shopdetail;
    private ListView mLv_comment;
    private TextView mTv_CommentCount;
    private InquiryCommentAdapter adapter;
    private String username;
    private List<InquiryComment> comments;
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquirydetail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        utils = XutilsUtils.getInstance();
        username = PrefUtils.getString(getApplicationContext(), PrefUtils.USER_NUMBER, "");
        inquiryid = (int) getIntent().getExtras().get("inquiryid");
        init();


        getDateAndFillView(inquiryid);
    }

    private void getDateAndFillView(final int inquiryid) {
        Log.i(TAG, "getDateAndFillView: " + ServerApi.GETINQUIRYBYID + inquiryid);
        utils.getCache(ServerApi.GETINQUIRYBYID + inquiryid, null, true, 60 * 1000 * 6, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                ParseJson(result);
                getComment(inquiryid);

            }

            @Override
            public void onResponseFail() {
            }
        });
    }

    private void init() {
        initView();
    }

    private void ParseJson(String result) {
        Gson gson = new Gson();
        inquiryExt = gson.fromJson(result, InquiryExt.class);
        Log.i(TAG, "ParseJson: " + inquiryExt.toString());

    }

    private void getComment(int shopId) {
        Log.i(TAG, "getComment: " + ServerApi.GETCOMMENTBYINQUIRYID + shopId);
        utils.getCache(ServerApi.GETCOMMENTBYINQUIRYID + shopId, null, true, 60 * 100, new XutilsUtils.XCallBack() {

            @Override
            public void onResponse(String result) {
                ParseJsonComment(result);
            }

            @Override
            public void onResponseFail() {
                ToastUtils.showToast(getApplicationContext(), "失败了");
            }
        });
    }

    private void ParseJsonComment(String result) {
        Gson gson = new Gson();
        comments = gson.fromJson(result, new TypeToken<ArrayList<InquiryComment>>() {
        }.getType());
        adapter = new InquiryCommentAdapter(getApplicationContext(), comments, inquiryExt.getUsername());
        mLv_comment.setAdapter(adapter);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initDate();
            }
        });
    }

    private void initDate() {
        if (inquiryExt != null) {
            mTv_user_name.setText(inquiryExt.getName());
            mTv_money.setText("￥" + inquiryExt.getIprice() + "");
            mTv_shop_summary.setText(inquiryExt.getIdescription());
            Uri UserIconUri = Uri.parse(ServerApi.SHOWPIC + inquiryExt.getIconpath());
            Glide.with(getApplication()).load(UserIconUri).placeholder(R.drawable.usericon).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource,
                                            GlideAnimation<? super GlideDrawable> glideAnimation) {
                    mSld_usericon.setImageDrawable(resource);
                }
            });
            mTv_CommentCount.setText(comments.size() + "");
            if (username.equalsIgnoreCase(inquiryExt.getUsername())) {
                mBtn_buy.setText("已经收到?");  //判断是否用户已经收到
            } else {
                mBtn_buy.setText("联系卖家?");  //判断是否用户已经收到
            }


            avi.hide();
        }
    }

    private void initView() {
        mSld_usericon = (CircleImageView) findViewById(R.id.sld_usericon);
        mTv_money = (TextView) findViewById(R.id.tv_money);
        mTv_user_name = (TextView) findViewById(R.id.tv_user_name);
        mTv_fromwhere = (TextView) findViewById(R.id.tv_fromwhere);
        mTv_shop_summary = (TextView) findViewById(R.id.tv_shop_summary);
        mIv_addComment = (ImageView) findViewById(R.id.iv_addcomment);
        mIv_addComment.setOnClickListener(this);
        mLv_comment = (ListView) findViewById(R.id.lv_comment);
        mTv_CommentCount = (TextView) findViewById(R.id.tv_comment);
        mBtn_buy = (Button) findViewById(R.id.btn_buy);
        mBtn_buy.setOnClickListener(this);

        rl_shopdetail = (RelativeLayout) findViewById(R.id.rl_shopdetail);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_addcomment:
                addComment();
                break;
            case R.id.btn_buy:
                checkState();
                break;
        }
    }

    private void checkState() {
        if (mBtn_buy.getText().toString().contains("已经收到")) {
            DialogUIUtils.showMdAlert(this, "是否确认？","您已经成功求购到商品了么？",true, true, new DialogUIListener() {
                @Override
                public void onPositive() {

                }

                @Override
                public void onNegative() {
                    return;
                }
            }).show();


        }
        if (mBtn_buy.getText().toString().contains("联系卖家")) {
            DialogUIUtils.showMdAlert(this, "联系买家", "是否拨打买家电话:" + inquiryExt.getTelephone()+"？", true, true, new DialogUIListener() {
                @Override
                public void onPositive() {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + inquiryExt.getTelephone()));
                    startActivity(intent);
                }

                @Override
                public void onNegative() {
                    return;
                }
            }).show();


        }
    }


    private void addComment() {

        DialogUIUtils.showAlert(this, null, "请输入留言内容", "内容", null, "确定", "取消", false, false, false, new DialogUIListener() {
            @Override
            public void onPositive() {
            }

            @Override
            public void onNegative() {
            }

            @Override
            public void onGetInput(CharSequence input1, CharSequence input2) {
                String content = (String) input1;
                if (!TextUtils.isEmpty(content.trim())) {
                    addCommentContent(content);

                } else {
                    SnackbarUtils.ShowSnackbar(getCurrentFocus(), "请输入内容");
                }

            }
        }).show();
    }

    //将内容推送至服务器
    private void addCommentContent(String input1) {
        String content = input1;
        final HashMap<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("content", content);
        map.put("inquiryid", inquiryid + "");
        final InquiryComment comment = new InquiryComment();
        comment.setInquiryid(inquiryExt.getId());
        comment.setUsername(username);
        comment.setContent(content);
        utils.post(ServerApi.ADDINQUIRYCONMENT, map, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                //插入成功
                adapter.addComment(comment);
                onRestart();
            }

            @Override
            public void onResponseFail() {
                SnackbarUtils.ShowSnackbar(getCurrentFocus(), "网络连接失败");
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getDateAndFillView(inquiryid);
        Log.i(TAG, "onRestart: ");
    }

}
