package com.example.project_harayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import api.UserApi;
import model.Register;
import model.Response;
import retrofit2.Call;
import retrofit2.Callback;
import url.Url;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName, etRUsername, etAddress, etPhoneNo, etEmail, etRPassword, etRConfPassword;
    private Button btnRegister;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // will hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        etFullName = findViewById(R.id.etFullName);
        etRUsername = findViewById(R.id.etRUsername);
        etAddress = findViewById(R.id.etAddress);
        etPhoneNo = findViewById(R.id.etPhoneNo);
        etEmail = findViewById(R.id.etEmail);
        etRPassword = findViewById(R.id.etRPassword);
        etRConfPassword = findViewById(R.id.etRConfPassword);
        btnRegister = findViewById(R.id.btnRegister);

        login = findViewById(R.id.tvLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        String fullname = etFullName.getText().toString();
        String username = etRUsername.getText().toString();
        String address = etAddress.getText().toString();
        String phoneno = etPhoneNo.getText().toString();
        String email = etEmail.getText().toString();
        String password = etRPassword.getText().toString();
        String confpassword = etRConfPassword.getText().toString();

        if (fullname.isEmpty()){
            etFullName.setError("Please Enter Your FullName");
        }else if (username.isEmpty()){
            etRUsername.setError("Please Enter Username");
        }else if (address.isEmpty()){
            etAddress.setError("Please Enter Your Address");
        }else if (phoneno.isEmpty()){
            etPhoneNo.setError("Please Enter Your Phone Number");
        }else if (email.isEmpty()){
            etEmail.setError("Please Enter Your Email");
        }else if (password.isEmpty()){
            etRPassword.setError("Please Enter Password");
        }else if (confpassword.isEmpty()){
            etRConfPassword.setError("Please Enter Confirm Password");
        }
        else if(!etRPassword.getText().toString().equals(etRConfPassword.getText().toString())){
            etRConfPassword.setError("Please Enter Same Password");
        }
        else {

            Register register = new Register(fullname,username,address,phoneno,email,password);

            UserApi userApi = Url.getInstance().create(UserApi.class);
            Call<Response> responseCall = userApi.register(register);

            responseCall.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registered Successful",Toast.LENGTH_LONG).show();

                        etFullName.setText("");
                        etRUsername.setText("");
                        etAddress.setText("");
                        etPhoneNo.setText("");
                        etEmail.setText("");
                        etRPassword.setText("");
                        etRConfPassword.setText("");
                    }else{
                        Toast.makeText(RegisterActivity.this, "Registration Failed",Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {

                    Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}
