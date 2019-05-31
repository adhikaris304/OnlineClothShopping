package com.example.onlineclothshopping;

import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

import static com.example.onlineclothshopping.R.layout.activity_details;

public class DetailsActivity extends AppCompatActivity {
    TextView itemname,itemprice,itemdesc;
    ImageView itemimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_details);
        itemimg=findViewById(R.id.ivDetailsImage_View);
        itemname=findViewById(R.id.itemName_View);
        itemprice=findViewById(R.id.itemPrice_View);
        itemdesc=findViewById(R.id.itemDesc_view);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            itemname.setText(bundle.getString("itemname"));
            itemprice.setText(bundle.getString("itemprice"));
            itemdesc.setText(bundle.getString("itemdesc"));

            String image=bundle.getString("itemimage");
            StrictMode();

            try {
                URL url=new URL(image);
                itemimg.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}