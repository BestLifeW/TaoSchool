package com.wtc.xmut.taoschool.ui.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.adpater.PublishAdapter;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.ShopExt;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetailActivity extends AppCompatActivity {

    private String category;
    private XutilsUtils utils;
    private RecyclerView mRecyclerView;
    private List<ShopExt> shopList;
    private PublishAdapter adapter;
    private static final String TAG = "CategoryDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        utils = XutilsUtils.getInstance();
        category = (String) getIntent().getExtras().get("category");
        init();
    }

    private void init(){
        initView();
        initData();

    }

     /*
     * 初始化数据
     */
    private void initData() {
        Log.i(TAG, "initData: "+ServerApi.GETSHOPBYCATEGORY + category);
        utils.get(ServerApi.GETSHOPBYCATEGORY + category,null,new XutilsUtils.XCallBack() {

            @Override
            public void onResponse(String result) {
                Log.i(TAG, "onResponse: "+result);
                parseDate(result);
            }

            @Override
            public void onResponseFail() {
                Log.i(TAG, "onResponseFail");
            }
        });

    }

    /**
     * 解析网络数据
     * @param result 网络数据
     */
    private void parseDate(String result) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        shopList = gson.fromJson(result, new TypeToken<ArrayList<ShopExt>>() {
        }.getType());
        setAdapter();
    }

    /***
     * 设置适配器
     */
    private void setAdapter() {
        adapter = new PublishAdapter(getApplicationContext(),
                R.layout.item_main, shopList);
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        SpacesItemDecoration decoration = new SpacesItemDecoration(20);
        mRecyclerView.addItemDecoration(decoration);
    }
    private class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;
        SpacesItemDecoration(int space) {
            this.space = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
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
