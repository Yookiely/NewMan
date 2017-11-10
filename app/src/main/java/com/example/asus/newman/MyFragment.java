package com.example.asus.newman;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by asus on 2017/11/9.
 */

public class MyFragment extends Fragment {
    News news;
    Handler handler;
    NewsAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    MyFragment(final int a){

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    adapter = new NewsAdapter(getActivity(),news);
                    recyclerView.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };

        news = new News();
        news.setData(new ArrayList<News.DataBean>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
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
                    retrofit2.Response<News> response = api.getNewsList(a,1).execute();
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    { // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.page, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view1);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.srl_main);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,  android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(),"loading...",Toast.LENGTH_SHORT).show();
                adapter = new NewsAdapter(getActivity(),news);
                recyclerView.setAdapter(adapter);
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(getActivity(),"finish",Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        return view;

    }


}
