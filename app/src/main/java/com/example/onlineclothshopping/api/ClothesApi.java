package com.example.onlineclothshopping.api;

import com.example.onlineclothshopping.ImageResponse;
import com.example.onlineclothshopping.model.Clothes;
import com.example.onlineclothshopping.model.Users;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ClothesApi  {

    @POST("users")
    Call<Void> addUsers(@Body Users users);



    @GET("items")
    Call<List<Clothes>> getClothes();

    @POST("items")
    Call<Void> addItems(@Body Clothes clothes);

    @FormUrlEncoded
    @POST("items")
    Call<Void> addFieldItems (@Field("itemname") String itemName, @Field("itemprice") String itemPrice, @Field("itemimageName") String itemImageName, @Field("itemdesc") String itemDescription);

    @Multipart
    @POST("upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    @GET("users")
    Call<List<Users>> getUsers();

    @POST("users")
    Call<Void> addUser(@Body Users users);
}