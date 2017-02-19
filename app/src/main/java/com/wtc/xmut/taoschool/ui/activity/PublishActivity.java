package com.wtc.xmut.taoschool.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.wtc.xmut.taoschool.R;

public class PublishActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_addpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        init();
    }

    private void init() {
        intView();
    }

    private void intView() {
        iv_addpic = (ImageView) findViewById(R.id.iv_addpic);
        iv_addpic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_addpic:
                break;
        }
    }
}
