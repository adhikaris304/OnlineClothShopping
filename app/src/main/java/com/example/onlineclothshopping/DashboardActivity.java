package com.example.onlineclothshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.example.onlineclothshopping.adapter.ItemsAdapter;
import com.example.onlineclothshopping.api.ClothesApi;
import com.example.onlineclothshopping.model.Clothes;
import com.example.onlineclothshopping.url.Url;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Clothes> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView=findViewById(R.id.recyclerView);

        getItems();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(intent);
            }
        });

    }
    private void getItems(){
        ClothesApi clothesApi= Url.getInstance().create(ClothesApi.class);
        Call<List<Clothes>> listCall=clothesApi.getClothes();

        listCall.enqueue(new Callback<List<Clothes>>() {
            @Override
            public void onResponse(Call<List<Clothes>> call, Response<List<Clothes>> response) {
                if(response.body()!=null){
                    List<Clothes> clothes=response.body();
                    ItemsAdapter itemsAdapter=new ItemsAdapter(DashboardActivity.this,clothes);
                    recyclerView.setAdapter(itemsAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this));


                }
            }

            @Override
            public void onFailure(Call<List<Clothes>> call, Throwable t) {

            }
        });

    }

}
