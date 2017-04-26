package com.wtc.xmut.taoschool.ui.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tapadoo.alerter.Alerter;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.adpater.OrdersAdapter;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.OrdersExt;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

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
    private List<OrdersExt> shoplist;
    private static final String TAG = "MessageFragment";
    private RecyclerView mRecyclerView;
    private OrdersAdapter adapter;
    private Dialog dialog;
    private TextView isMessage;

    public MessageFragment() {
        this.mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message,null);
        username = PrefUtils.getString(getActivity(), PrefUtils.USER_NUMBER, "");
        dialog = DialogUIUtils.showMdLoading(getActivity(),"刷新中",true,true,true,true).show();
        utils = XutilsUtils.getInstance();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.smrlview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

    /**
     * 解析返回的数据
     * @param result
     */
    private void parseResult(String result) {
        Gson gson = new Gson();
        shoplist = gson.fromJson(result, new TypeToken<ArrayList<OrdersExt>>() {
        }.getType());
        Log.i(TAG, "parseResult: "+shoplist.size());
        /*Alerter.create(getActivity())
                .setTitle("提示")
                .setText("有新消息啦～")
                .show();*/
        if (shoplist==null||shoplist.size()==0){
            isMessage.setVisibility(View.VISIBLE);
        }
        adapter = new OrdersAdapter(getActivity(), R.layout.item_message, shoplist);
        dialog.dismiss();
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 初始化View
     */
    private void initView() {
        isMessage = (TextView) view.findViewById(R.id.isMessage);

    }

    public static MessageFragment newInstance() {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
