package com.wtc.xmut.taoschool.ui.fragment.homeFragment_src;


import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.Service.ShopService;
import com.wtc.xmut.taoschool.adpater.PublishAdapter;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.ShopExt;
import com.wtc.xmut.taoschool.utils.XutilsUtils;
import com.wtc.xmut.taoschool.view.RecyclerViewEmptySupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PublishFragment extends Fragment {

    private View view;
    @BindView(R.id.recycler_view)
    RecyclerViewEmptySupport mRecyclerView;


    private ShopService shopService;
    private List<ShopExt> shopList;
    private static final String TAG = "PublishFragment";
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private XutilsUtils utils;
    private PublishAdapter adapter;

    public PublishFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_publish, container, false);
        ButterKnife.bind(this, view);
        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        utils = XutilsUtils.getInstance();
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void init() throws IOException {
        initDate();
        initEvent();
    }

    private void initDate() throws IOException {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setEmptyView(view.findViewById(R.id.fail));
        SpacesItemDecoration decoration = new SpacesItemDecoration(20);
        mRecyclerView.addItemDecoration(decoration);
        getConnectData();
    }

    /**
     * 获取网络数据
     */
    private void getConnectData() {
        utils.getCache(ServerApi.ALLSHOPANDUSER, null, true, 60 * 10, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                parseDate(result);
            }
            @Override
            public void onResponseFail() {
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
        adapter = new PublishAdapter(getActivity(),
                R.layout.item_main, shopList);
        mRecyclerView.setAdapter(adapter);
    }

    private void initEvent() {
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mSwipeRefreshWidget.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        getConnectData();
                        adapter = new PublishAdapter(getActivity(),
                                R.layout.item_main, shopList);
                        mSwipeRefreshWidget.setRefreshing(false);
                        //Toasty.custom(getActivity(), "刷新成功", null, Color.WHITE, Color.alpha(200), Toast.LENGTH_SHORT, true, true).show();
                    }
                });
            }
        });
    }

    public static PublishFragment newInstance() {
        Bundle args = new Bundle();
        PublishFragment fragment = new PublishFragment();
        fragment.setArguments(args);
        return fragment;
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
    public void onResume() {
        super.onResume();
        getConnectData();
        adapter = new PublishAdapter(getActivity(),
                R.layout.item_main, shopList);

    }
}
