package com.example.asus.newman;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by asus on 2017/11/6.
 */

public interface Api {
    @GET("news/{type}/page/{page}")
    Call<News> getNewsList(@Path("type") int type, @Path("page") int page);

}
