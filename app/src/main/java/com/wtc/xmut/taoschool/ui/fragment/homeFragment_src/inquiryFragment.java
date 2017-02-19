package com.wtc.xmut.taoschool.ui.fragment.homeFragment_src;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wtc.xmut.taoschool.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InquiryFragment extends Fragment {


    public InquiryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inquiry, container, false);
    }

    public static InquiryFragment newInstance() {

        Bundle args = new Bundle();

        InquiryFragment fragment = new InquiryFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
