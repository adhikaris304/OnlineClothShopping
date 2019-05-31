package com.example.onlineclothshopping;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.onlineclothshopping.api.ClothesApi;
import com.example.onlineclothshopping.model.Clothes;
import com.example.onlineclothshopping.url.Url;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {
    private Button btnAddItem,btnback;
    private EditText etItemName,etPrice,etItemDesc;
    ImageView etImageName;
    String imagePath;
    String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        btnAddItem=findViewById(R.id.btnAddItem);
        etItemName=findViewById(R.id.etItemName);
        etPrice=findViewById(R.id.etItemPrice);
        etImageName=findViewById(R.id.imgItem);
        etItemDesc=findViewById(R.id.etItemDesc);
        btnback=findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddItemActivity.this,DashboardActivity.class);
                startActivity(intent);
            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();


            }
        });
        etImageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });
    }
    private  void addItem(){
        SaveImageOnly();
        ClothesApi clothsAPI= Url.getInstance().create(ClothesApi.class);
        String itemName=etItemName.getText().toString();
        String itemPrice=etPrice.getText().toString();
        String itemDesc=etItemDesc.getText().toString();


        Clothes items=new Clothes(itemName,itemPrice,itemDesc,imageName);

        Call<Void> call = clothsAPI.addItems(items);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(AddItemActivity.this, "Item Added Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddItemActivity.this, "Error"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void BrowseImage(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode==RESULT_OK){
            if(data==null){
                Toast.makeText(this, "Please Select Item Image", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri=data.getData();
        imagePath=getRealPathFromUri(uri);
        previewImage(imagePath);

    }
    private String getRealPathFromUri(Uri uri){
        String[] projection={MediaStore.Images.Media.DATA};
        CursorLoader loader=new CursorLoader(getApplicationContext(),uri, projection,null,null,null);
        Cursor cursor=loader.loadInBackground();
        int colIndex=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result=cursor.getString(colIndex);
        cursor.close();
        return result;
    }
    private void previewImage(String imagePath){
        File imgFile=new File(imagePath);
        if (imgFile.exists()){
            Bitmap bitmap= BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            etImageName.setImageBitmap(bitmap);
        }
    }
    private void StrictMode(){
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    private void SaveImageOnly(){
        File file =new File(imagePath);
        RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body=MultipartBody.Part.createFormData("imageFile",file.getName(),requestBody);

        ClothesApi clothsAPI= Url.getInstance().create(ClothesApi.class);
        Call<ImageResponse> responseBodyCall=clothsAPI.uploadImage(body);
        StrictMode();

        try{
            Response<ImageResponse> imageResponseResponse=responseBodyCall.execute();
            imageName=imageResponseResponse.body().getFilename();

        }catch (IOException e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
