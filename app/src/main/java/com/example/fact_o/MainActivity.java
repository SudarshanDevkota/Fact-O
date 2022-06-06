package com.example.fact_o;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.fact_o.adapter.RecyclerAdapter;
import com.example.fact_o.api.ApiCalls;
import com.example.fact_o.client.FactClient;
import com.example.fact_o.model.Fact;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    ViewPager2 viewPager;
    RecyclerAdapter adapter;
    List<Fact> factList;
    Button btnShare, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.view_pager);
        btnSave = findViewById(R.id.btn_save);
        btnShare = findViewById(R.id.btn_share);
        factList = new ArrayList<>();
        adapter = new RecyclerAdapter(factList, this);
        viewPager.setAdapter(adapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d("TAG", "onPageSelected: " + position);
                if (position % 30 == 28) {
                    loadNewData();
                }

            }
        });
        btnShare.setOnClickListener(shareListener);
        btnSave.setOnClickListener(saveListener);
        loadNewData();


    }

    View.OnClickListener shareListener = v -> {

    };
    View.OnClickListener saveListener = v -> {
        try {
            saveImage();
        } catch (IOException e) {
            showToast("file creation failed");
            e.printStackTrace();
        }

    };

    private void generateList(List<Fact> list) {
        Log.d("TAG", "generateList: content loaded");
        if (factList.size() > 120)
            factList.clear();
        factList.addAll(list);
        adapter.notifyDataSetChanged();

    }

    private void loadNewData() {
        ApiCalls api = FactClient.getInstance().create(ApiCalls.class);
        Call<List<Fact>> call = api.getFacts();
        call.enqueue(new Callback<List<Fact>>() {
            @Override
            public void onResponse(Call<List<Fact>> call, Response<List<Fact>> response) {
                generateList(response.body());
            }

            @Override
            public void onFailure(Call<List<Fact>> call, Throwable t) {
                showToast("Network Not Available");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void saveImage() throws IOException {
        viewPager.setDrawingCacheEnabled(true);
        viewPager.buildDrawingCache();
        viewPager.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = viewPager.getDrawingCache();
        saveImage(bitmap);
    }

    private void saveImage(Bitmap bitmap) throws IOException {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(root + "/Download");
        if (!file.exists())
            file.mkdirs();
        String name = System.currentTimeMillis()+".jpg";
        File img = new File(file, name);
        FileOutputStream fileOutputStream = new FileOutputStream(img);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        viewPager.setDrawingCacheEnabled(false);
        showToast("Image saved to Gallery");
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory()+"/Download/")));

    }

}