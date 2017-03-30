package com.wtc.xmut.taoschool.ui.fragment.homeFragment_src;


import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.Service.ShopService;
import com.wtc.xmut.taoschool.adpater.PublishAdapter;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.ShopExt;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PublishFragment extends Fragment {

    private View view;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    private ShopService shopService;
    private List<ShopExt> shopList;
    private static final String TAG = "PublishFragment";
    private SwipeRefreshLayout mSwipeRefreshWidget;



    public PublishFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_publish, container, false);
        ButterKnife.bind(this, view);
        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void init() throws IOException {
        initDate();
        initEvent();
    }

    private void initDate() throws IOException {


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Request request = new Request.Builder()
                .url(ServerApi.ALLSHOPANDUSER)
                .build();
        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                SnackbarUtils.ShowSnackbar(getView(), "服务器连接失败");
                Log.i(TAG, "onFailure: 失败了");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = null;
                str = response.body().string();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                shopList = gson.fromJson(str, new TypeToken<ArrayList<ShopExt>>() {
                }.getType());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mRecyclerView.setAdapter(new PublishAdapter(getActivity(),
                                R.layout.item_main, shopList));
                        SpacesItemDecoration decoration = new SpacesItemDecoration(20);
                        mRecyclerView.addItemDecoration(decoration);
                    }
                });
            }
        });
    }

    private void initEvent() {
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
    }

    public static PublishFragment newInstance() {
        Bundle args = new Bundle();
        PublishFragment fragment = new PublishFragment();
        fragment.setArguments(args);
        return fragment;
    }

    class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }


}
