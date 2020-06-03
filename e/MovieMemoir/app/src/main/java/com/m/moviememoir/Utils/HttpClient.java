package com.m.moviememoir.Utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClient {
    private static HttpClient httpClient;
    private OkHttpClient okHttpClient;
    private Handler handler;

    private HttpClient() {
        okHttpClient = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
    }

    public static HttpClient getInstance() {
        if (httpClient == null) {
            synchronized (HttpClient.class) {
                if (httpClient == null) {
                    httpClient = new HttpClient();
                }
            }
        }
        return httpClient;
    }

    public void asyncGet(String url, HttpCallBack httpCallBack) {
        Log.d("xbw12138", url);
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new StringCallBack(request, httpCallBack));
    }

    public void asyncGet(String url, Headers header, HttpCallBack httpCallBack) {
        Request request = new Request.Builder().url(url).headers(header).build();
        okHttpClient.newCall(request).enqueue(new StringCallBack(request, httpCallBack));
    }

    public void asyncPost(String url, RequestBody requestBody, HttpCallBack httpCallBack) {
        Request request = new Request.Builder().url(url).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new StringCallBack(request, httpCallBack));
    }

    public interface HttpCallBack {
        void onError(Request request, IOException e);

        void onSuccess(Request request, String result, Headers headers);
    }

    class StringCallBack implements Callback {
        private HttpCallBack httpCallBack;
        private Request request;

        public StringCallBack(Request request, HttpCallBack httpCallBack) {
            this.request = request;
            this.httpCallBack = httpCallBack;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            final IOException fe = e;
            if (httpCallBack != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpCallBack.onError(request, fe);
                    }
                });
            }
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String result = response.body().string();
            final Headers headers = response.headers();
            if (httpCallBack != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpCallBack.onSuccess(request, result, headers);
                    }
                });
            }
        }
    }
}
