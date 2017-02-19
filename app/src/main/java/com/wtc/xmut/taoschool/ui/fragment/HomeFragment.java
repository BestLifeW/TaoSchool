package com.wtc.xmut.taoschool.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.adpater.MyViewPagerAdapter;
import com.wtc.xmut.taoschool.ui.fragment.homeFragment_src.InquiryFragment;
import com.wtc.xmut.taoschool.ui.fragment.homeFragment_src.PublishFragment;

import static android.content.ContentValues.TAG;


/***
 *
 */
public class HomeFragment extends Fragment {

    private Context mContext;
    private View view;
    private TabLayout mtablayout;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public HomeFragment() {
        Log.d(TAG,"HomeFragment");
        this.mContext = getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return view;
    }

    private void initView() {

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(PublishFragment.newInstance(), "发布");
        viewPagerAdapter.addFragment(InquiryFragment.newInstance(),"求购");
        mViewPager.setAdapter(viewPagerAdapter);//设置适配器

        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("发布"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("求购"));//给TabLayout添加Tab
        mTabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }
}
