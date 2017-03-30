package com.wtc.xmut.taoschool.ui.fragment.homeFragment_src;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wtc.xmut.taoschool.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class inquiryFragment extends Fragment {


    private View view;
    private RecyclerView mRlv_inquiry;
    private SwipeRefreshLayout mSrf_inquiry;

    public inquiryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inquiry, container, false);
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

    }

    /**
     * 初始化View
     */
    private void initView() {
        mRlv_inquiry = (RecyclerView) view.findViewById(R.id.rlv_inquiry);
        mSrf_inquiry = (SwipeRefreshLayout) view.findViewById(R.id.srf_inquiry);
    }

    public static inquiryFragment newInstance() {
        Bundle args = new Bundle();
        inquiryFragment fragment = new inquiryFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
