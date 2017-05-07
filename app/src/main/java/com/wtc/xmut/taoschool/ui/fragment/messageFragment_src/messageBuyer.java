package com.wtc.xmut.taoschool.ui.fragment.messageFragment_src;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wtc.xmut.taoschool.R;


public class messageBuyer extends Fragment {


    private View view;

    public messageBuyer() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message_inquiry, container, false);

        return view;
    }

    public static messageBuyer newInstance() {
        
        Bundle args = new Bundle();
        
        messageBuyer fragment = new messageBuyer();
        fragment.setArguments(args);
        return fragment;
    }

}
