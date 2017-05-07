package com.wtc.xmut.taoschool.ui.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dou361.dialogui.DialogUIUtils;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

/**
 * 作者 By lovec on 2017/3/9 0009.21:09
 * 邮箱 lovecanon0823@gmail.com
 */

public class FailFragment extends Fragment  {

    private SwipeRefreshLayout swipe_refresh_layout;
    private Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fail, null);
        swipe_refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        //判断网络连接
        XutilsUtils xUtils = XutilsUtils.getInstance();
        dialog = DialogUIUtils.showMdLoading(getActivity(),"刷新中",true,true,true,true).show();
        xUtils.get(ServerApi.ISCONNECT, null, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                if (result.equals("true")) {
                    dialog.dismiss();
                    
                }
            }
            @Override
            public void onResponseFail() {
                dialog.dismiss();

            }
        });
        return view;

    }

    public static FailFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FailFragment fragment = new FailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
