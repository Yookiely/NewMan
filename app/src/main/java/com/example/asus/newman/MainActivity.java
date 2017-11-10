package com.example.asus.newman;

import android.app.DownloadManager;
import android.content.ClipData;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private News news;
    Handler handler;
    NewsAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.srl_main);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,  android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(),"loading...",Toast.LENGTH_SHORT).show();
                adapter = new NewsAdapter(getApplicationContext(),news);
                recyclerView.setAdapter(adapter);
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(),"finish",Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    adapter = new NewsAdapter(getApplicationContext(),news);
                    recyclerView.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        news = new News();
        news.setData(new ArrayList<News.DataBean>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://open.twtstudio.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Api api = retrofit.create(Api.class);

            new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    retrofit2.Response<News> response = api.getNewsList(1,1).execute();
                    news = response.body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }).start();

        }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_LONG).
                        show();
                break;
            default:
        }
        return true;
    }

}
