package com.currentaffairs;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Api {

    @GET("get/currentaffairs")
    retrofit2.Call<CaResponse> getCurrentAffairs(
            @HeaderMap Map<String, String> headers,
            @Query("start") String start
    );

}
