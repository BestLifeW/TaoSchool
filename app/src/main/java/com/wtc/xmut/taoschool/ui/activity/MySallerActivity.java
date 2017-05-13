package com.wtc.xmut.taoschool.ui.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.ShopExt;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

import java.util.ArrayList;

public class MySallerActivity extends AppCompatActivity {

    private XutilsUtils xutilsUtils;
    private String username;
    private Object shopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_saller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        xutilsUtils = XutilsUtils.getInstance();
        username = PrefUtils.getString(getApplicationContext(), PrefUtils.USER_NUMBER, "");
        init();

    }
    private void init(){
        initView();
        initData();
    }

    private void initData() {
        xutilsUtils.getCache(ServerApi.GETSHOPBYUSERNAME + username, null, false, 60 * 6000, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                parseJson(result);
            }

            @Override
            public void onResponseFail() {
                SnackbarUtils.ShowSnackbar(getCurrentFocus(),"网络连接失败了~");
            }
        });
    }
    //解析数据
    private void parseJson(String result) {
        Gson gson = new Gson();
        shopList = gson.fromJson(result, new TypeToken<ArrayList<ShopExt>>() {
        }.getType());
  /*      Log.i(TAG, "parseJson: "+result);

    adapter = new MyPublishAdapter(getApplicationContext(), shopList,this);
        sm_rlv.setItemViewSwipeEnabled(true);
    SpacesItemDecoration decoration = new SpacesItemDecoration(20);
        sm_rlv.addItemDecoration(decoration);
        sm_rlv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        sm_rlv.setAdapter(adapter);*/



    }


    private void initView() {
        //sm_rlv = (SwipeMenuRecyclerView) findViewById(sm_rlv);
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
}
