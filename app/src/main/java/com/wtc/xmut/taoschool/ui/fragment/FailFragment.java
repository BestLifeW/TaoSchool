package com.wtc.xmut.taoschool.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wtc.xmut.taoschool.R;

/**
 * 作者 By lovec on 2017/3/9 0009.21:09
 * 邮箱 lovecanon0823@gmail.com
 */

public class FailFragment extends Fragment  {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fail, null);
        return view;
    }

    public static FailFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FailFragment fragment = new FailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
