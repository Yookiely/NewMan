package com.example.asus.newman;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    boolean loading = false;
    int a = 0;
    int z = 1;

    MyFragment(int type) {
        this.a = type;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.page, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view1);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_main1);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "loading...", Toast.LENGTH_SHORT).show();
                adapter = new NewsAdapter(getActivity(), news);
                recyclerView.setAdapter(adapter);
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(getActivity(), "finish", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                if (msg.what == 1) {
//                    adapter = new NewsAdapter(getActivity(), news);
//                    recyclerView.setAdapter(adapter);
//                }
//                super.handleMessage(msg);
//            }
//        };

        news = new News();
//        news.setData(new ArrayList<News.DataBean>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://open.twtstudio.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Api api = retrofit.create(Api.class);

//        new Thread(new Runnable() {

//             @Override
//            public void run() {
//                try {
//                    retrofit2.Response<News> response = api.getNewsList(a, 1).execute();
//                    news = response.body();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Message message = new Message();
//                message.what = 1;
//                handler.sendMessage(message);
//            }
//        }
        Call<News> call = api.getNewsList(a,1);
// 用法和OkHttp的call如出一辙,
// 不同的是如果是Android系统回调方法执行在主线程
        call.enqueue(new Callback<News>() {


            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
                news=response.body();
                adapter = new NewsAdapter(getActivity(), news);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                t.printStackTrace();
            }
        });//Retrofit自带方法，call.enqueue  （onResponse和onFailure）

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int totalItemCount;
            int visibleItemCount;
            int firstVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                visibleItemCount = recyclerView.getChildCount();

                if (((totalItemCount - visibleItemCount) <= firstVisibleItem) && !loading) {
                    loading = true;
                    z++;
                    onLoadMore();

                }

            }

            @SuppressLint("HandlerLeak")
            public void onLoadMore() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (a == 4 && z == 3) {

                            } else {
                                retrofit2.Response<News> response = api.getNewsList(a, z).execute();

                                if (response.body().getData() != null) {
                                    news.getData().addAll(response.body().getData());
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }).start();
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == 1) {
                            adapter.notifyDataSetChanged();
                            loading = false;
                        }
                        super.handleMessage(msg);
                    }
                };


            }
        });
        return view;

    }


}
