package com.wtc.xmut.taoschool.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wtc.xmut.taoschool.R;



public class PersonFragment extends Fragment {

    private Context mContext;

    public PersonFragment() {
        this.mContext =getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();
        return inflater.inflate(R.layout.fragment_person, container, false);
    }


    public static PersonFragment newInstance() {
        Bundle args = new Bundle();
        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
