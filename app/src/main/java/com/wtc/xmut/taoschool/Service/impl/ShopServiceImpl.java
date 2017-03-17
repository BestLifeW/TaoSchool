package com.wtc.xmut.taoschool.Service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wtc.xmut.taoschool.Service.ShopService;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.Shop;
import com.wtc.xmut.taoschool.domain.User;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者 By lovec on 2017/2/23 0023.11:05
 * 邮箱 lovecanon0823@gmail.com
 */

public class ShopServiceImpl implements ShopService {

    private static Gson gson;
    private static OkHttpClient client;

    static {
        client = new OkHttpClient();
        gson = new Gson();
    }

    private Shop shop;
    private List<Shop> foods;

    @Override
    public void insertShop(Shop shop) {

    }

    @Override
    public void deleteShopById(Integer id) {

    }

    @Override
    public void updateShopById(Shop shop) {

    }

    @Override
    public List<Shop> findAll() throws IOException {
        Request request = new Request.Builder()
                .url(ServerApi.ALLSHOP)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = null;
                str = response.body().string();
                foods =  gson.fromJson(str, new TypeToken<List<Shop>>(){}.getType());
            }
        });
        if (foods != null) {
            return foods;
        } else return null;
    }

    @Override
    public User findShopById(Integer id) {
        return null;
    }


}
