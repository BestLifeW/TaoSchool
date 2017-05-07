package com.wtc.xmut.taoschool.ui.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.adpater.MyViewPagerAdapter;
import com.wtc.xmut.taoschool.adpater.OrderSallerAdapter;
import com.wtc.xmut.taoschool.domain.OrdersExt;
import com.wtc.xmut.taoschool.ui.fragment.messageFragment_src.messageBuyer;
import com.wtc.xmut.taoschool.ui.fragment.messageFragment_src.messageSaller;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

import java.util.List;


/**
 * User:Shine
 * Date:2015-10-20
 * Description:
 */
public class MessageFragment extends Fragment {
    private Context mContext;
    private View view;
    private String username;
    private XutilsUtils utils;
    private List<OrdersExt> shoplist;
    private static final String TAG = "MessageFragment";
    private RecyclerView mRecyclerView;
    private OrderSallerAdapter adapter;
    private Dialog dialog;
    private TextView isMessage;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public MessageFragment() {
        this.mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message,null);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        final MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("卖家消息"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("求购"));//给TabLayout添加Tab
        viewPagerAdapter.addFragment(messageBuyer.newInstance(), "买到的");
        viewPagerAdapter.addFragment(messageSaller.newInstance(), "卖出的");
        mViewPager.setAdapter(viewPagerAdapter);//设置适配器
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }
}
