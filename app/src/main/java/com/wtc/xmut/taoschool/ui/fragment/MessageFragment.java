package com.wtc.xmut.taoschool.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.wtc.xmut.taoschool.R;


/**
 * User:Shine
 * Date:2015-10-20
 * Description:
 */
public class MessageFragment extends PreferenceFragment {
    private Context mContext;

    public MessageFragment() {
        this.mContext = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.demo);
    }


    public static MessageFragment newInstance() {

        Bundle args = new Bundle();

        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
