package com.wtc.xmut.taoschool;

import org.junit.Test;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        OkHttpClient client = new OkHttpClient();

        //3, 发起新的请求,获取返回信息
        RequestBody body = new FormBody.Builder()
                .add("user_name", "abc")//添加键值对
                .add("user_password", "321")
                .build();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/LoginDemo/AddUser")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful())
        {
            String str = response.body().string();
            System.out.println("服务器响应为: " + str);
        }
    }
}