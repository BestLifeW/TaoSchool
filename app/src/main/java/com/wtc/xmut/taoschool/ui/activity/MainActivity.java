package com.wtc.xmut.taoschool.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.zhouwei.library.CustomPopWindow;
import com.startsmake.mainnavigatetabbar.widget.MainNavigateTabBar;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.ui.fragment.CityFragment;
import com.wtc.xmut.taoschool.ui.fragment.HomeFragment;
import com.wtc.xmut.taoschool.ui.fragment.MessageFragment;
import com.wtc.xmut.taoschool.ui.fragment.PersonFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG_PAGE_HOME = "首页";
    private static final String TAG_PAGE_CITY = "分类";
    private static final String TAG_PAGE_PUBLISH = "发布";
    private static final String TAG_PAGE_MESSAGE = "消息";
    private static final String TAG_PAGE_PERSON = "我的";


    private MainNavigateTabBar mNavigateTabBar;
    private SearchView searchView;
    private ImageView Iv_publish;
    private CustomPopWindow.PopupWindowBuilder mCustomPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        Iv_publish = (ImageView) findViewById(R.id.tab_post_icon);
        Iv_publish.setOnClickListener(this);
        mNavigateTabBar = (MainNavigateTabBar) findViewById(R.id.mainTabBar);

        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);

        mNavigateTabBar.addTab(HomeFragment.class, new MainNavigateTabBar.TabParam(R.mipmap.comui_tab_home, R.mipmap.comui_tab_home_selected, TAG_PAGE_HOME));
        mNavigateTabBar.addTab(CityFragment.class, new MainNavigateTabBar.TabParam(R.mipmap.comui_tab_city, R.mipmap.comui_tab_city_selected, TAG_PAGE_CITY));
        mNavigateTabBar.addTab(null, new MainNavigateTabBar.TabParam(0, 0, TAG_PAGE_PUBLISH));
        mNavigateTabBar.addTab(MessageFragment.class, new MainNavigateTabBar.TabParam(R.mipmap.comui_tab_message, R.mipmap.comui_tab_message_selected, TAG_PAGE_MESSAGE));
        mNavigateTabBar.addTab(PersonFragment.class, new MainNavigateTabBar.TabParam(R.mipmap.comui_tab_person, R.mipmap.comui_tab_person_selected, TAG_PAGE_PERSON));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigateTabBar.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

      /*  searchView = (SearchView) MenuItemCompat.getActionView(menuItem);//加载searchview
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search: {
                Intent intent = new Intent(getApplicationContext(), SearchResultsActivity.class);
                startActivity(intent);
                return super.onOptionsItemSelected(item);

            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {

        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //默认为0，为-1时一直循环动画
        rotate.setRepeatCount(0);
        //添加匀速加速器
        rotate.setDuration(1000);
        rotate.setFillAfter(true);
        Iv_publish.startAnimation(rotate);


        View contentView = LayoutInflater.from(this).inflate(R.layout.item_popup, null);
        //处理popWindow 显示内容
        //handleLogic(contentView);
        //创建并显示popWindow

        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this);
        mCustomPopWindow
                .setView(contentView)
                .setClippingEnable(true)
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0.5f)
                .create()
                .showAsDropDown(Iv_publish, -100, 60);
        handleLogic(contentView);
    }

    private void handleLogic(View contentView){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.iv_publish:
                        enterPublishActivity();
                        break;
                    case R.id.iv_inquiry:
                        enterInquiryActivity();
                        break;
                }
            }
        };
        contentView.findViewById(R.id.iv_publish).setOnClickListener(listener);
        contentView.findViewById(R.id.iv_inquiry).setOnClickListener(listener);
    }

    private void enterInquiryActivity() {
        Intent intent = new Intent(getApplicationContext(),InquiryActivity.class);
        startActivity(intent);

    }

    private void enterPublishActivity() {
        Intent intent = new Intent(getApplicationContext(),PublishActivity.class);
        startActivity(intent);
    }
}

