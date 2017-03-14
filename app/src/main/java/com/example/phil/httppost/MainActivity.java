package com.example.phil.httppost;

import android.content.Intent;
import android.support.v4.os.UserManagerCompatApi24;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.phil.httppost.data.model.Login;
import com.example.phil.httppost.data.model.User;
import com.example.phil.httppost.data.remote.ApiUtils;
import com.example.phil.httppost.data.remote.GoodJobService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private GoodJobService goodJobService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.login);
        goodJobService = ApiUtils.getAPIService();

        Button register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, User_Profile.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText emailText = (EditText) findViewById(R.id.emailText);
                String email = emailText.getText().toString();
                EditText passText = (EditText) findViewById(R.id.passwordText);
                String password = passText.getText().toString();
                doLogin(email, password );
                startActivity(new Intent(MainActivity.this, Tinder_Look.class));
            }
        });
    }

    public void doLogin(String email, String password){
        goodJobService.login(new Login(email, password)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    System.out.println("okay\n" + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("FAIL" + t.toString());
            }
        });
    }
}
