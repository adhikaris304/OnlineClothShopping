package com.example.onlineclothshopping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
            itemimg.setImageResource(Integer.parseInt(bundle.getString("itemimage")));
            itemdesc.setText(bundle.getString("itemdesc"));
        }
    }
}