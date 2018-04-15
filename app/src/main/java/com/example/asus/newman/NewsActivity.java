package com.example.asus.newman;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.mediaRouteButtonStyle;
import static android.R.attr.x;
import static android.R.attr.y;

/**
 * Created by asus on 2017/10/27.
 */

public class NewsActivity extends AppCompatActivity {
    private NewsSecond contentBean = new NewsSecond();
    WebView webView;
    Handler handler;
    TextView textView;

    @SuppressLint({"HandlerLeak", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        webView = (WebView) findViewById(R.id.webView);
        textView = (TextView) findViewById(R.id.text_subject);
        webView.getSettings().setJavaScriptEnabled(true);
        sendRequestWithOkhttp();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                webView.loadData(contentBean.title, "text/html;charset=utf-8", null);
                Bundle b = msg.getData();
                textView.setText(b.getString("name"));
                super.handleMessage(msg);
            }
        };
    }

    public void sendRequestWithOkhttp() {
        Intent intent = getIntent();
        final String b = intent.getStringExtra("1");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://open.twtstudio.com/api/v1/news/" + b)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseDate = response.body().string();
                    parseJSONWithJSONObject(responseDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                msg.obj = contentBean.title;
                Bundle b = new Bundle();
                b.putString("name", contentBean.subject);
                msg.setData(b);
                handler.sendMessage(msg);
            }
        }).start();
    }

    public void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);

            jsonObject = jsonObject.getJSONObject("data");
            contentBean.title = jsonObject.getString("content");
            contentBean.subject = jsonObject.getString("subject");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}


