package com.example.fact_o;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.fact_o.adapter.RecyclerAdapter;
import com.example.fact_o.api.ApiCalls;
import com.example.fact_o.client.FactClient;
import com.example.fact_o.model.Fact;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    ViewPager2 viewPager;
    RecyclerAdapter adapter;
    List<Fact> factList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.view_pager);

        factList = new ArrayList<>();
        adapter = new RecyclerAdapter(factList,this);
        viewPager.setAdapter(adapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d("TAG", "onPageSelected: "+position);
                if(position%30==28){
                    loadNewData();
                }

            }
        });
        loadNewData();


    }
    private void generateList(List<Fact> list){
        Log.d("TAG", "generateList: content loaded");
        factList.addAll(list);
        adapter.notifyDataSetChanged();

    }
    private void loadNewData(){
        ApiCalls api = FactClient.getInstance().create(ApiCalls.class);
        Call<List<Fact>> call = api.getFacts();
        call.enqueue(new Callback<List<Fact>>() {
            @Override
            public void onResponse(Call<List<Fact>> call, Response<List<Fact>> response) {
                generateList(response.body());
            }

            @Override
            public void onFailure(Call<List<Fact>> call, Throwable t) {

            }
        });
    }

}