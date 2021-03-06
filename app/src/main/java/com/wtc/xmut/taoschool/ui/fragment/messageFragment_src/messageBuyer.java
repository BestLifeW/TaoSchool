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
import com.wtc.xmut.taoschool.adpater.OrderBuyerAdapter;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.OrdersExt;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;
import com.wtc.xmut.taoschool.view.RecyclerViewEmptySupport;

import java.util.ArrayList;
import java.util.List;


public class messageBuyer extends Fragment {


    private final XutilsUtils utils;
    private  String buyername;
    private View view;
    private static final String TAG = "messageBuyer";
    private List<OrdersExt> shoplist;
    private RecyclerViewEmptySupport recycler_view;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private OrderBuyerAdapter adapter;

    public messageBuyer() {
        utils = XutilsUtils.getInstance();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message_inquiry, container, false);
        recycler_view = (RecyclerViewEmptySupport)view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        SpacesItemDecoration decoration = new SpacesItemDecoration(20);
        recycler_view.addItemDecoration(decoration);
        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        buyername = PrefUtils.getString(getActivity(), PrefUtils.USER_NUMBER, "");
        initData();
        refresh();
        return view;
    }
    /**
     * 初始化数据
     */
    private void initData() {

        utils.get(ServerApi.GETORDERBYBUYERNAME + buyername, null, new XutilsUtils.XCallBack() {
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

    public static messageBuyer newInstance() {
        Bundle args = new Bundle();
        messageBuyer fragment = new messageBuyer();
        fragment.setArguments(args);
        return fragment;
    }

    private void parseResult(String result) {
        Gson gson = new Gson();
        shoplist = gson.fromJson(result, new TypeToken<ArrayList<OrdersExt>>() {
        }.getType());
        Log.i(TAG, "parseResult: "+ shoplist.size());

        if (shoplist ==null|| shoplist.size()==0){

        }
        adapter = new OrderBuyerAdapter(getActivity(), shoplist,getActivity());
        recycler_view.setEmptyView(view.findViewById(R.id.fail));
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
                }, 1000);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
