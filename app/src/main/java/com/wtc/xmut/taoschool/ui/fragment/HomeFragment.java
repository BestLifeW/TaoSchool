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
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.ui.fragment.homeFragment_src.inquiryFragment;
import com.wtc.xmut.taoschool.ui.fragment.homeFragment_src.PublishFragment;
import com.wtc.xmut.taoschool.utils.NetWorkUtils;
import com.wtc.xmut.taoschool.utils.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;


/***
 *
 */
public class HomeFragment extends Fragment {

    private Context mContext;
    private View view;
    private TabLayout mtablayout;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private final OkHttpClient okHttpClient;
    private static final String TAG = "HomeFragment";

    private boolean isConnect;


    public HomeFragment() {
        Log.d(TAG, "HomeFragment");
        this.mContext = getActivity();
        okHttpClient = new OkHttpClient();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isConnect = NetWorkUtils.isNetworkAvailable(getActivity());
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        final MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("发布"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("求购"));//给TabLayout添加Tab
        OkHttpUtils.getDateAsync(ServerApi.ISCONNECT, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadDateFail(viewPagerAdapter);
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadDateSuccess(viewPagerAdapter);
                    }
                });
            }
        });

        return view;
    }

    private void loadDateSuccess(MyViewPagerAdapter viewPagerAdapter) {
        viewPagerAdapter.addFragment(PublishFragment.newInstance(), "发布");
        viewPagerAdapter.addFragment(inquiryFragment.newInstance(), "求购");
        mViewPager.setAdapter(viewPagerAdapter);//设置适配器
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void loadDateFail(MyViewPagerAdapter viewPagerAdapter) {
        viewPagerAdapter.addFragment(FailFragment.newInstance(), "发布");
        viewPagerAdapter.addFragment(FailFragment.newInstance(), "求购");
        mViewPager.setAdapter(viewPagerAdapter);//设置适配器
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initView() {



    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
