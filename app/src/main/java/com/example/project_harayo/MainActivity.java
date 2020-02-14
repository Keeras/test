package com.example.project_harayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import api.UserApi;
import model.Account;
import model.Response;
import retrofit2.Callback;
import url.Url;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private TextView signup;
    private Button Login;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //        remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // will hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        signup = findViewById(R.id.ibSignUp);
        Login = findViewById(R.id.btnLogin);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth();

            }
        });
    }

    private void auth() {
        //        retrieve values
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (username.isEmpty()){
            etUsername.setError("Please Enter Username");
        }else if (password.isEmpty()){
            etPassword.setError("Please Enter Password");
        }

        //        pass retrived values to model
        Account account = new Account(username,password);


//        API instanciate
        UserApi userApi = Url.getInstance().create(UserApi.class);
        Call<model.Response> responseCall = userApi.login(account);

        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, MainBoard.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Login Failed",Toast.LENGTH_LONG).show();
                    vibrator.vibrate(100);

                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
