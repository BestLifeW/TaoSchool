package com.wtc.xmut.taoschool.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.Shop;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

import java.util.ArrayList;
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
    private List<Shop> shoplist;
    private static final String TAG = "MessageFragment";
    public MessageFragment() {
        this.mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message,null);
        username = PrefUtils.getString(getActivity(), PrefUtils.USER_NUMBER, "");
        utils = XutilsUtils.getInstance();
        init();
        return view;
    }
    private void init(){
        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //先获取本人发送的所有商品id 返回集合类型
        utils.get(ServerApi.GETSHOPBYUSERNAME + username, null, new XutilsUtils.XCallBack() {
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

    /**
     * 解析返回的数据
     * @param result
     */
    private void parseResult(String result) {
        Gson gson = new Gson();
        shoplist = gson.fromJson(result, new TypeToken<ArrayList<Shop>>() {
        }.getType());

        //然后根据id查询是否在订单列表 遍历结合
        //解析 shoplist 将Id存放在list集合中
        List<String> list = new ArrayList<>();
        for (int i = 0;i<shoplist.size();i++){
            list.add(shoplist.get(i).getId()+"");
        }

    }

    /**
     * 初始化View
     */
    private void initView() {

    }

    public static MessageFragment newInstance() {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
