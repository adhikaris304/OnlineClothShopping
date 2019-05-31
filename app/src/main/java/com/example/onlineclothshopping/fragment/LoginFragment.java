package com.example.onlineclothshopping.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlineclothshopping.DashboardActivity;
import com.example.onlineclothshopping.R;
import com.example.onlineclothshopping.api.ClothesApi;
import com.example.onlineclothshopping.model.Users;
import com.example.onlineclothshopping.url.Url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment implements View.OnClickListener{
    private Button btnLogin;
    private EditText etUsername_Login, etPassword_Login;

    public LoginFragment(){

    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.activity_login_fragment, container, false);
        btnLogin=view.findViewById(R.id.btnLogin);
        etUsername_Login=view.findViewById(R.id.etUsername_Login);
        etPassword_Login=view.findViewById(R.id.etPassword_Login);

        btnLogin.setOnClickListener(this);
        return  view;

    }


    public void onClick(View v){
        if(!validate()){
            return;
        }
        checkLogin();

    }
    private void checkLogin(){
        ClothesApi clothesApi= Url.getInstance().create(ClothesApi.class);
        Call<List<Users>> ListCall = clothesApi.getUsers();
        ListCall.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                List<Users> usersList = response.body();
                for(Users users:usersList){
                    if(etUsername_Login.getText().toString().equals(users.getUsername()) && etPassword_Login.getText().toString().equals(users.getPassword())){
                        //Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(getActivity().getApplicationContext(), DashboardActivity.class);
                        startActivity(intent);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public Boolean validate(){
        boolean isValid=true;
        if(TextUtils.isEmpty(etUsername_Login.getText().toString())){
            etUsername_Login.setError("Please Enter Username");
            etUsername_Login.requestFocus();
            isValid=false;
        }else if (TextUtils.isEmpty(etPassword_Login.getText().toString())){
            etPassword_Login.setError("Please Enter Password");
            etPassword_Login.requestFocus();
            isValid=false;

        }
        return isValid;
    }

}
