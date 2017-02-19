package com.wtc.xmut.taoschool.ui.fragment.homeFragment_src;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.Action;


public class PublishFragment extends Fragment {

    private View view;
    @BindView(R.id.recycler_view)
    RefreshRecyclerView mRecyclerView;

    public PublishFragment() {
        // Required empty public constructor
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
        init();
        return view;
    }

    private void init() {
        initEvent();
    }

    private void initEvent() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置刷新颜色s
        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction() {
                ToastUtils.showToast(getActivity(), "我刷新啦");
            }
        });

    }

    public static PublishFragment newInstance() {
        Bundle args = new Bundle();
        PublishFragment fragment = new PublishFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
