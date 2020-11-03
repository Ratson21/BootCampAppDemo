package com.example.test_android.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test_android.Interface.UserApiService;
import com.example.test_android.R;
import com.example.test_android.model.ApiResult;
import com.example.test_android.model.Login;
import com.example.test_android.model.Register;
import com.example.test_android.utilities.Cons;

import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DaftarActivity extends AppCompatActivity {


    private String TAG = "DaftarActivity";

    private EditText inputUsername, inputPassword, inputEmail, inputfirstname, inputlastname, inputretypepassword ;
    private String userName, password, email, firstName, lastName, retypePassword ;
    private Button btnregister;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        initView();
        initRetrofit();

    }
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DaftarActivity. this);
        builder.setMessage("Yakin untuk keluar?");
        builder.setCancelable(true);
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("Ya",(dialog, which) -> {finish();});
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
    private void initView(){

        inputUsername = findViewById(R.id.input_username);
        inputPassword = findViewById(R.id.input_password);
        inputEmail = findViewById(R.id.input_email);
        inputfirstname = findViewById(R.id.input_firstname);
        inputlastname = findViewById(R.id.input_lastname);
        inputretypepassword = findViewById(R.id.input_retype_password);
        btnregister = findViewById(R.id.btn_register);

        btnregister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.i(TAG, "onClick: button register ditekan ");
                userName = inputUsername.getText().toString();
                password = inputPassword.getText().toString();
                firstName = inputfirstname.getText().toString();
                lastName = inputlastname.getText().toString();
                email = inputEmail.getText().toString();
                retypePassword = inputretypepassword.getText().toString();
                Register register = new Register(userName, password, lastName, firstName, email, retypePassword);



                    if (userName.equals("")){
                        Toast.makeText(DaftarActivity.this, "username tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }else if(userName.trim().length() < 4){
                        Toast.makeText(DaftarActivity.this, "username tidak boleh kurang dari 4 karakter", Toast.LENGTH_SHORT).show();
                    }else if(firstName.equals("")){
                        Toast.makeText(DaftarActivity.this, "first name tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }else if(lastName.equals("")){
                        Toast.makeText(DaftarActivity.this, "last name tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else if(email.equals("")){
                        Toast.makeText(DaftarActivity.this, "email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }else if(password.equals("")){
                        Toast.makeText(DaftarActivity.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }else if(password.trim().length() < 6){
                        Toast.makeText(DaftarActivity.this, "Password tidak boleh kurang dari 6 karakter", Toast.LENGTH_SHORT).show();
                    }else if(!retypePassword.equals(password)){
                        Toast.makeText(DaftarActivity.this, "Password tidak sama", Toast.LENGTH_SHORT).show();
                    }else if(!EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
                        Toast.makeText(DaftarActivity.this, "email tidak benar", Toast.LENGTH_SHORT).show();
                    }else{
                        sendRegister(register);
                    }

                Log.i(TAG, "isi username : " + userName);
                Log.i(TAG, "isi password : " + password);
                Log.i(TAG, "isi email : " + email);
                Log.i(TAG, "isi firstname : " + firstName);
                Log.i(TAG, "isi lastname : " + lastName);
                Log.i(TAG, "isi retyepe password : " + retypePassword);

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

    private void sendRegister(Register registerBody) {
        UserApiService userApiService = retrofit.create(UserApiService.class);  //instansiasi interfacenya ke retrofit
        Call<ApiResult> result = userApiService.userRegister(registerBody);   // call method interfacenya

        result.enqueue(new Callback<ApiResult>() {

            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult apiResponse = response.body();
                boolean success = apiResponse.isSuccess();
                if (success) {
                    Toast.makeText(DaftarActivity.this, "Berhasil Register", Toast.LENGTH_SHORT).show();
                    toLoginActivity();

                } else {
                    Toast.makeText(DaftarActivity.this , apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    ,apiResponse.getMessage() buat dapetin salahnya taruh di tengah
                }
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Toast.makeText(DaftarActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void toLoginActivity() {
        Intent intent = new Intent(DaftarActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}