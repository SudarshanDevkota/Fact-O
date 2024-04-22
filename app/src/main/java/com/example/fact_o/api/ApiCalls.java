package com.example.fact_o.api;

import com.example.fact_o.model.Fact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiCalls {
    @Headers("X-Api-Key: cwYa40HzV+aO7BLiUJX9Mg==umPhIpiIPUmCrDUy")
    @GET("/v1/facts")
    Call<List<Fact>> getFacts();
}
