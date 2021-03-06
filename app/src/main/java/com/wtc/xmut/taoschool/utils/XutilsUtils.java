package com.wtc.xmut.taoschool.utils;


import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.util.Map;

/**
 * Created by 王田朝 on 2017/3/31.
 */
public class XutilsUtils {

    private volatile static XutilsUtils instance;
    private Handler handler;
    private ImageOptions options;
    private static final String TAG = "XutilsUtils";
    private XutilsUtils() {
        handler = new Handler(Looper.getMainLooper());
    }
    public static XutilsUtils getInstance() {
        if (instance == null) {
            synchronized (XutilsUtils.class) {
                if (instance == null) {
                    instance = new XutilsUtils();
                }
            }
        }
        return instance;
    }
    /**
     * 异步get请求
     *
     * @param url
     * @param maps
     * @param callBack
     */
    public void get(String url, Map<String, String> maps, final XCallBack callBack) {
        RequestParams params = new RequestParams(url);
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        }
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                onSuccessResponse(result, callBack);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                onFaliResponse(callBack);
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 异步post请求
     *
     * @param url
     * @param maps
     * @param callback
     */
    public void post(String url, Map<String, String> maps, final XCallBack callback) {
        RequestParams params = new RequestParams(url);
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                onSuccessResponse(result, callback);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                onFaliResponse(callback);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }



    /**
     * 带缓存数据的异步get请求
     * @param url
     * @param maps
     * @param ifCache      是否缓存
     * @param cacheTime   缓存存活时间
     * @param callback
     */
    public void getCache(String url,Map<String,String> maps,final boolean ifCache,long cacheTime,final XCallBack callback){
        RequestParams params = new RequestParams(url);
        params.setCacheMaxAge(cacheTime);
        if (null != maps && maps.isEmpty()){
            for (Map.Entry<String,String> entry : maps.entrySet()){
                params.addQueryStringParameter(entry.getKey(),entry.getValue());
            }
        }
        x.http().get(params, new Callback.CacheCallback<String>() {
            private boolean hasError = false;
            private String result = null;
            @Override
            public boolean onCache(String result) {
                if (ifCache && null != result){
                    this.result = result;
                }
                // true: 信任缓存数据, 不在发起网络请求; false不信任缓存数据.
                return ifCache;
            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    this.result = result;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                hasError = true;
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                if (!hasError && result != null) {
                    onSuccessResponse(result,callback);
                }
            }
        });
    }

    /**
     * 带缓存数据的异步 post请求
     *
     * @param url
     * @param maps
     * @param pnewCache
     * @param callback
     */
    public void postCache(String url, Map<String, String> maps, final boolean pnewCache, final XCallBack callback) {
        RequestParams params = new RequestParams(url);
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }

        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                onSuccessResponse(result, callback);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }


            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                boolean newCache = pnewCache;
                if (newCache) {
                    newCache = !newCache;
                }
                if (!newCache) {
                    newCache = !newCache;
                    onSuccessResponse(result, callback);
                }
                return newCache;
            }
        });
    }

/*
        *//**
     * 正常图片显示
     *
     * @param iv
     * @param url
     * @param option
     *//*
        public void bindCommonImage(ImageView iv, String url, boolean option) {
            if (option) {
                options = new ImageOptions.Builder().setLoadingDrawableId(R.mipmap.icon_stub).setFailureDrawableId(R.mipmap.icon_error).build();
                x.image().bind(iv, url, options);
            } else {
                x.image().bind(iv, url);
            }
        }*/
/*

        */
/**
 * 圆形图片显示
 *
 * @param iv
 * @param url
 * @param option
 *//*

        public void bindCircularImage(ImageView iv, String url, boolean option) {
            if (option) {
                options = new ImageOptions.Builder().setLoadingDrawableId(R.mipmap.icon_stub).setFailureDrawableId(R.mipmap.icon_error).setCircular(true).build();
                x.image().bind(iv, url, options);
            } else {
                x.image().bind(iv, url);
            }
        }
*/


    /**
     * 文件上传
     *
     * @param url
     * @param maps
     * @param file
     * @param callback
     */
    public void upLoadFile(String url, Map<String, String> maps, Map<String, File> file, final XCallBack callback) {
        RequestParams params = new RequestParams(url);
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        if (file != null) {
            for (Map.Entry<String, File> entry : file.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue().getAbsoluteFile());
            }
        }
        // 有上传文件时使用multipart表单, 否则上传原始文件流.
        params.setMultipart(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                onSuccessResponse(result, callback);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                    onFaliResponse(callback);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }


       /* *//**
     * 文件下载
     *
     * @param url
     * @param maps
     * @param callBack
     *//*
        public void downLoadFile(String url, Map<String, String> maps, final XDownLoadCallBack callBack) {

            RequestParams params = new RequestParams(url);
            if (maps != null && !maps.isEmpty()) {
                for (Map.Entry<String, String> entry : maps.entrySet()) {
                    params.addBodyParameter(entry.getKey(), entry.getValue());
                }
            }
            params.setAutoRename(true);// 断点续传
            params.setSaveFilePath(PublicParams.SAVE_FILE_PATH);
            x.http().post(params, new Callback.ProgressCallback<File>() {
                @Override
                public void onSuccess(final File result) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null) {
                                callBack.onResponse(result);
                            }
                        }
                    });
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null) {
                                callBack.onFinished();
                            }
                        }
                    });
                }

                @Override
                public void onWaiting() {

                }

                @Override
                public void onStarted() {

                }

                @Override
                public void onLoading(final long total, final long current, final boolean isDownloading) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null) {
                                callBack.onLoading(total, current, isDownloading);
                            }
                        }
                    });
                }
            });

        }*/


    /**
     * 异步get请求返回结果,json字符串
     *
     * @param result
     * @param callBack
     */
    private void onSuccessResponse(final String result, final XCallBack callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(result);

                }
            }
        });
    }
    private void onFaliResponse(final XCallBack callBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack!=null){
                    callBack.onResponseFail();
                }
            }
        });
    }


    public interface XCallBack {
        void onResponse(String result);
        void onResponseFail();
    }


    public interface XDownLoadCallBack extends XCallBack {
        void onResponse(File result);

        void onLoading(long total, long current, boolean isDownloading);

        void onFinished();
    }


    /**
     * 同步的POST请求
     */

}

