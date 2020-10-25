package com.example.test_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_android.Interface.UserApiService;
import com.example.test_android.R;
import com.example.test_android.model.ApiResult;
import com.example.test_android.model.Login;
import com.example.test_android.utilities.Cons;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    private EditText inputUserName, inputPassword;
    private String userName, password;
    private Button btnLogin, btnRegister;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initRetrofit();

        TextView daftar = (TextView) findViewById(R.id.btnDaftar);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("Tombol login ditekan");
                Intent i = new Intent(LoginActivity.this, DaftarActivity.class);
                startActivity(i);
            }

        });

    }


    private void initView() {

        inputUserName = findViewById(R.id.input_usernameLogin);
        inputPassword = findViewById(R.id.input_passwordLogin);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btnDaftar);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: button login ditekan ");
                userName = inputUserName.getText().toString();
                password = inputPassword.getText().toString();
                Login login = new Login(userName, password);

                if (userName.equals("")){
                    Toast.makeText(LoginActivity.this, "username tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else if(password.equals("")){
                    Toast.makeText(LoginActivity.this, "password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else{
                    sendLogin(login);
                }


                Log.i(TAG, "isi username : " + userName);
                Log.i(TAG, "isi password : " + password);
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
    }

    private void initRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(); //intercept semua log http
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Cons.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    private void sendLogin(Login loginBody) {
        UserApiService userApiService = retrofit.create(UserApiService.class);  //instansiasi interfacenya ke retrofit
        Call<ApiResult> result = userApiService.userLogin(loginBody);   // call method interfacenya

        result.enqueue(new Callback<ApiResult>() {

            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult apiResponse = response.body();
                boolean success = apiResponse.isSuccess();
                if (success) {
                    Toast.makeText(LoginActivity.this, "Selamat Datang", Toast.LENGTH_SHORT).show();
                  toMainActivity();

                } else {
                    Toast.makeText(LoginActivity.this , apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    ,apiResponse.getMessage() buat dapetin salahnya taruh di tengah
                }
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void toMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}