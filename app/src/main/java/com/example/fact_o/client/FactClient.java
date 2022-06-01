package com.example.fact_o.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FactClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.api-ninjas.com";

    public static Retrofit getInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}




