package com.example.test_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.test_android.R;

import retrofit2.Retrofit;

public class DaftarActivity extends AppCompatActivity {


    private String TAG = "DaftarActivity";

    private EditText inputUsername, inputPassword, inputEmail, inputFirstname, inputLastname ;
    private String userName, password;
    private Button btnLogin, btnRegister;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
    }
}