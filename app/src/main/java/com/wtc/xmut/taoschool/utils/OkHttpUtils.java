package com.wtc.xmut.taoschool.utils;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 作者 By lovec on 2017/3/15 0015.22:25
 * 邮箱 lovecanon0823@gmail.com
 */

public class OkHttpUtils {
    private static  final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();


    /**
     * 返回request对象
     * @param urlString
     * @return
     */
    private static Request BuildRequest(String urlString){
        Request request = new Request.Builder()
                .url(urlString)
                .build();
        return  request;
    }

    /**
     * 返回Response对象
     * @param urlString
     * @return
     * @throws IOException
     */
    private static Response getResponseFromUrl(String urlString) throws IOException{
        Request request = BuildRequest(urlString);
        Response response = OK_HTTP_CLIENT.newCall(request).execute();
        return response;

    }

    private static ResponseBody BuildResponseBody(String urlstring) throws IOException{
        Response response = getResponseFromUrl(urlstring);
        if (response.isSuccessful()){
            return response.body();
        }
        return null;
    }

    /**
     * 通过网络请求获取字符串
     * @param urlString
     * @return
     * @throws IOException
     */
    public static String loadStringFromUrl(String urlString) throws IOException{
        ResponseBody responseBody = BuildResponseBody(urlString);
        if (responseBody!=null){
            return responseBody.string();
        }
        return null;
    }

    /**
     * 通过网络请求获取字节数据
     * @param urlString
     * @return
     * @throws IOException
     */
    public static byte[] loadByteFromUrl(String urlString) throws IOException{
        ResponseBody responseBody = BuildResponseBody(urlString);
        if (responseBody!=null){
            return responseBody.bytes();
        }
        return null;
    }

    /**
     * 通过网络请求获取字节数据
     * @param urlString
     * @return
     * @throws IOException
     */
    public static InputStream loadInputStreamFromUrl(String urlString) throws IOException{
        ResponseBody responseBody = BuildResponseBody(urlString);
        if (responseBody!=null){
            return responseBody.byteStream();
        }
        return null;
    }


    public static void getDateAsync(String urlString , Callback callback){
        Request request = BuildRequest(urlString);
        OK_HTTP_CLIENT.newCall(request).enqueue(callback);
    }




}
