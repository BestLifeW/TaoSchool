package com.wtc.xmut.taoschool.ui.fragment.messageFragment_src;

import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.adpater.OrderSallerAdapter;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.OrdersExt;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;
import com.wtc.xmut.taoschool.view.RecyclerViewEmptySupport;

import java.util.ArrayList;
import java.util.List;

import static com.marshalchen.ultimaterecyclerview.expanx.Util.DataUtil.TAG;


public class messageSaller extends Fragment {

    private List<OrdersExt> shoplist;
    private OrderSallerAdapter adapter;
    private View view;
    private RecyclerViewEmptySupport recycler_view;
    private XutilsUtils utils;
    private String username;
    private SwipeRefreshLayout mSwipeRefreshWidget;


    public messageSaller() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message_publish, container, false);
        utils = XutilsUtils.getInstance();
        username = PrefUtils.getString(getActivity(), PrefUtils.USER_NUMBER, "");
        recycler_view = (RecyclerViewEmptySupport)view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        initData();
        refresh();
        return view;
    }
    /**
     * 初始化数据
     */
    private void initData() {

       utils.get(ServerApi.GETORDERBYUSERNAME + username, null, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.i(TAG, "parseResult: "+result);
                parseResult(result);
            }
            @Override
            public void onResponseFail() {
                SnackbarUtils.ShowSnackbar(getView(),"连接失败");
            }
        });

    }

    private void parseResult(String result) {
        Gson gson = new Gson();
        shoplist = gson.fromJson(result, new TypeToken<ArrayList<OrdersExt>>() {
        }.getType());
        Log.i(TAG, "parseResult: "+ shoplist.size());

        if (shoplist ==null|| shoplist.size()==0){
           // isMessage.setVisibility(View.VISIBLE);
        }
        adapter = new OrderSallerAdapter(getActivity(), shoplist);
        recycler_view.setEmptyView(view.findViewById(R.id.fail));
        recycler_view.setAdapter(adapter);


    }
    public static messageSaller newInstance() {
        
        Bundle args = new Bundle();
        
        messageSaller fragment = new messageSaller();
        fragment.setArguments(args);
        return fragment;
    }

    class MyItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //设定底部边距为1px
            outRect.set(0, 0, 0, 1);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
    private void refresh(){
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();

                        mSwipeRefreshWidget.setRefreshing(false);

                    }
                }, 3000);
            }
        });
    }
}
