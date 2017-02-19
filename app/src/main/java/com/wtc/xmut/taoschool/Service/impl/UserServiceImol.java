package com.wtc.xmut.taoschool.Service.impl;

import com.google.gson.Gson;
import com.wtc.xmut.taoschool.Service.UserService;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.User;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 作者 By lovec on 2017/2/6 0006.07:28
 * 邮箱 lovecanon0823@gmail.com
 */

public class UserServiceImol implements UserService {
    private static OkHttpClient client;

    static {
        client = new OkHttpClient();
        gson = new Gson();
    }

    private static Gson gson;
    private User user;

    @Override
    public String insertUser(User user) {
        String str = null;
        RequestBody body = new FormBody.Builder()
                .add("username", user.getUsername())
                .add("password",user.getPassword())
                .add("name",user.getName())
                .build();
        Request request = new Request.Builder()
                .url(ServerApi.USERADD)
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                str = response.body().string();
                return str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; //返回空 说明连接失败
    }

    @Override
    public void deleteUserById(Integer id) {

    }

    @Override
    public void updateUserById(User user) {

    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findUserById(Integer id) {
        return null;
    }


    @Override   //根据用户名称查找
    public User findUserByName(String username) throws IOException {
        User user = null;
        RequestBody body = new FormBody.Builder()
                .add("username", username)//添加键值对
                .build();
        Request request = new Request.Builder()
                .url(ServerApi.FINDUSER)
                .post(body)
                .build();
        Response response = null;
        response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String str = null;
            str = response.body().string();
            user = gson.fromJson(str, User.class);
            System.out.println("服务器响应为: " + user.toString());
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User login(String username, String password) throws IOException {

        User user = null;
        RequestBody body = new FormBody.Builder()
                .add("username", username)//添加键值对
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(ServerApi.USERLOGIN)
                .post(body)
                .build();
        Response response = null;
        response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String str = null;
            str = response.body().string();
            user = gson.fromJson(str, User.class);
            System.out.println("服务器响应为: " + user.toString());
            return user;
        } else {
            return null;
        }


    }

}
