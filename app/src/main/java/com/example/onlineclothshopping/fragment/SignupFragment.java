package com.example.onlineclothshopping.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlineclothshopping.R;
import com.example.onlineclothshopping.api.ClothesApi;
import com.example.onlineclothshopping.model.Users;
import com.example.onlineclothshopping.url.Url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupFragment extends Fragment implements View.OnClickListener {
    private Button btnSignup;
    private EditText etFname_Signup,etPassword_Signup,etConfPass, etLname_Signup, etUsername_Signup, etConfirmPassword_Signup;


    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.activity_signup_fragment, container, false);
        etFname_Signup=view.findViewById(R.id.etFname_Signup);
        etLname_Signup=view.findViewById(R.id.etLname_Signup);
        etUsername_Signup=view.findViewById(R.id.etUsername_Signup);
        etPassword_Signup=view.findViewById(R.id.etPassword_Signup);
        etConfPass=view.findViewById(R.id.etConfirmPassword_Signup);
        btnSignup=view.findViewById(R.id.btn_signup);

        btnSignup.setOnClickListener(this);
        return view;

    }
    public void onClick(View v){
        if(!validate()){
            return;
        }
        addUsers();

    }
    private void Register() {
        String pass1 = etPassword_Signup.getText().toString();
        String pass2 = etConfPass.getText().toString();



        if (pass1.equals(pass2)) {

            Toast.makeText(getActivity(), "Sucessfully Registered", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Password Doesn't Match", Toast.LENGTH_SHORT).show();

        }
    }
    private void addUsers(){
        ClothesApi clothesApi= Url.getInstance().create(ClothesApi.class);
        String etFnameSignup=etFname_Signup.getText().toString();
        String etLnameSignup=etLname_Signup.getText().toString();
        String etUsernameSignup=etUsername_Signup.getText().toString();
        String etPasswordSignup=etPassword_Signup.getText().toString();

        Users users=new Users(etFnameSignup,etLnameSignup,etPasswordSignup, etUsernameSignup);

        Call<Void> usersCall=clothesApi.addUsers(users);
        usersCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getActivity(), "User Added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Error"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public Boolean validate(){
        boolean isValid=true;
        if(TextUtils.isEmpty(etUsername_Signup.getText().toString())){
            etUsername_Signup.setError("Please Enter Username");
            etUsername_Signup.requestFocus();
            isValid=false;
        }else if (TextUtils.isEmpty(etPassword_Signup.getText().toString())){
            etPassword_Signup.setError("Please Enter Password");
            etPassword_Signup.requestFocus();
            isValid=false;

        }else if (TextUtils.isEmpty(etConfPass.getText().toString())) {
            etConfPass.setError("Please Renter Password ");
            etConfPass.requestFocus();
            isValid = false;
        }
        return isValid;
    }

}