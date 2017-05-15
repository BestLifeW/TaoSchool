package com.wtc.xmut.taoschool.ui.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.adpater.MySallerAdapter;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.OrdersExt;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;
import com.wtc.xmut.taoschool.view.RecyclerViewEmptySupport;

import java.util.ArrayList;
import java.util.List;

public class MySallerActivity extends AppCompatActivity {

    private XutilsUtils xutilsUtils;
    private String username;
    private List<OrdersExt> ordersExtList;
    private MySallerAdapter adapter;
    private RecyclerViewEmptySupport recycler_view;
    private static final String TAG = "MySallerActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_saller);
         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        xutilsUtils = XutilsUtils.getInstance();
        username = PrefUtils.getString(getApplicationContext(), PrefUtils.USER_NUMBER, "");
        recycler_view = (RecyclerViewEmptySupport) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        SpacesItemDecoration decoration = new SpacesItemDecoration(20);
        recycler_view.addItemDecoration(decoration);
        init();

    }
    private void init(){
        initData();


    }

    private void initData() {
        xutilsUtils.get(ServerApi.GETSHOPBYSALLORBUYER + username+"/2", null, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.i(TAG, "parseResult: "+result);
                parseJson(result);
            }
            @Override
            public void onResponseFail() {
                SnackbarUtils.ShowSnackbar(getCurrentFocus(),"连接失败");
            }
        });
    }
    //解析数据
    private void parseJson(String result) {
        Gson gson = new Gson();
        ordersExtList = gson.fromJson(result, new TypeToken<ArrayList<OrdersExt>>() {
        }.getType());
        adapter = new MySallerAdapter(getApplicationContext(), ordersExtList);
        recycler_view.setEmptyView(findViewById(R.id.fail));

        recycler_view.setAdapter(adapter);



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
