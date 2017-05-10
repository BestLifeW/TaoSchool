package com.wtc.xmut.taoschool.ui.fragment.homeFragment_src;


import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.adpater.InquiryAdapter;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.InquiryExt;
import com.wtc.xmut.taoschool.utils.XutilsUtils;
import com.wtc.xmut.taoschool.view.RecyclerViewEmptySupport;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class inquiryFragment extends Fragment {


    private View view;
    private RecyclerViewEmptySupport mRlv_inquiry;
    private SwipeRefreshLayout mSrf_inquiry;
    private XutilsUtils utils;
    private List<InquiryExt> inquiryList;

    public inquiryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inquiry, container, false);
        utils = XutilsUtils.getInstance();
        init();
        return view;
    }

    /**
     * 初始化数据
     */
    private void init(){
        initDate();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initDate() {
        utils.get(ServerApi.GETINQUIRY, null, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                parseResult(result);
            }

            @Override
            public void onResponseFail() {
                Toasty.error(getActivity(),"网络连接失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void parseResult(String result) {
        Gson gson = new Gson();
        inquiryList = gson.fromJson(result, new TypeToken<ArrayList<InquiryExt>>() {
        }.getType());
        mRlv_inquiry.setAdapter(new InquiryAdapter(getActivity(),R.layout.item_inquiry,inquiryList));
    }

    /**
     * 初始化View
     */
    private void initView() {
        mRlv_inquiry = (RecyclerViewEmptySupport) view.findViewById(R.id.recycler_view);
        mSrf_inquiry = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mRlv_inquiry.setEmptyView(view.findViewById(R.id.fail));
        SpacesItemDecoration decoration = new SpacesItemDecoration(20);
        mRlv_inquiry.addItemDecoration(decoration);
        mRlv_inquiry.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public static inquiryFragment newInstance() {
        Bundle args = new Bundle();
        inquiryFragment fragment = new inquiryFragment();
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
}
