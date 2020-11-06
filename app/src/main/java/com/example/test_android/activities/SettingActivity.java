package com.example.test_android.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.test_android.Interface.AppService;
import com.example.test_android.Interface.UserImageService;
import com.example.test_android.R;
import com.example.test_android.model.ApiResult;
import com.example.test_android.model.User;
import com.example.test_android.model.UserImage;
import com.example.test_android.utilities.Cons;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class SettingActivity extends AppCompatActivity {

    private String TAG = "SettingActivity";
    User user;
    Button btnChooseImage;
    Button btnSave;
    Button btnUpdate;
    ImageButton imageButtonSetting;
    private static final int PICK_IMAGE = 1000;
    private static final int PERMISSION_CODE = 1001;
    ImageView imgAvatar;
    private String base64Image;
    private Long user_id;
    private String avatar;

    private Retrofit retrofit;
//    ImageView imgAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);;

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabItem tabImage = findViewById(R.id.tab_image);
        TabItem tabAddress = findViewById(R.id.tab_address);
        ViewPager viewPager = findViewById(R.id.viewPager);

        PagerAdapter pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//                initView();
//        initRetrofit();
//        getImage();

        }



//    private void initView(){
//        btnSave = findViewById(R.id.btnSave);
//        btnChooseImage = findViewById(R.id.btnChooseImage);
//        imgAvatar = findViewById(R.id.imgAvatar);
//        btnUpdate = findViewById(R.id.btnUpdate);
//
//        btnChooseImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UserImage userImage = new UserImage(user_id, avatar);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                            ==  PackageManager.PERMISSION_DENIED){
//                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
//                        requestPermissions(permissions, PERMISSION_CODE);
//                    }
//                    else {
//                        pickImageFromGallery();
//                    }
//                }
//                else {
//                    pickImageFromGallery();
//                }
//            }
//        });
//
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                user_id = AppService.getUser().getId();
//                avatar = base64Image;
//                UserImage userImage = new UserImage(user_id, base64Image);
//                sendImage(userImage);
//                Log.e("info","value id : " +user_id );
//            }
//        });
//
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UserImage userImage = new UserImage();
//                updateImage(userImage);
//            }
//        });
//    }
//
//    private void initRetrofit() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(); //intercept semua log http
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient client = new OkHttpClient
//                .Builder()
//                .addInterceptor(interceptor)
//                .build();
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl(Cons.Base_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//    }
//
//    private void getImage() {
//        Long user_id = AppService.getUser().getId();
//        UserImageService userImageService = retrofit.create(UserImageService.class);  //instansiasi interfacenya ke retrofit
//        Call<ApiResult> result = userImageService.getUserImage(AppService.getToken(), user_id); // call method interfacenya
//        result.enqueue(new Callback<ApiResult>() {
//            @Override
//            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
////                Log.e(TAG, "onResponse: " + response);
//                Gson gson = new Gson();
//                ApiResult apiResponse = response.body();
//                boolean success = apiResponse.isSuccess();
////                Log.i(TAG, "onResponse: " +success);
//                if (success) {
//                    UserImage userImageResult = gson.fromJson(gson.toJson(apiResponse.getData()), UserImage.class);
//
//                    setImageThumb(userImageResult.getAvatar());
//                    setButtonVisibility(View.GONE, View.VISIBLE);
//                } else {
//                    setButtonVisibility(View.VISIBLE, View.GONE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResult> call, Throwable t) {
//               Toast.makeText(SettingActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.e(TAG, "onFailure user setting: " + t);
//            }
//        });
//    }
//
//    private void updateImage(UserImage userImageBody) {
//        UserImage userImage = new UserImage();
//        userImage.setUser_id(AppService.getUser().getId());
//        userImage.setAvatar(base64Image);
//        UserImageService userImageService = retrofit.create(UserImageService.class);  //instansiasi interfacenya ke retrofit
//        Call<ApiResult> result = userImageService.updateUserImage(AppService.getToken(), userImage); // call method interfacenya
//        result.enqueue(new Callback<ApiResult>() {
//            @Override
//            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
//                ApiResult apiResponse = response.body();
//                boolean success = apiResponse.isSuccess();
//                if (success) {
//                    Toast.makeText(SettingActivity.this, "Berhasil upload foto", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(SettingActivity.this , apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<ApiResult> call, Throwable t) {
//                Toast.makeText(SettingActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.e(TAG, "onFailure user setting: " + t);
//            }
//        });
//    }
//
//    private void sendImage(UserImage userImageBody) {
//
//        UserImageService userImageService = retrofit.create(UserImageService.class);  //instansiasi interfacenya ke retrofit
//        Call<ApiResult> result = userImageService.insertUserImage(AppService.getToken(), userImageBody);   // call method interfacenya
//
//        result.enqueue(new Callback<ApiResult>() {
//
//            @Override
//            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
//                ApiResult apiResponse = response.body();
//                boolean success = apiResponse.isSuccess();
////                Log.i(TAG, "onResponse: " +success);
//                if (success) {
//                    Toast.makeText(SettingActivity.this, "Berhasil upload foto", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(SettingActivity.this , apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
////                    ,apiResponse.getMessage() buat dapetin salahnya taruh di tengah
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResult> call, Throwable t) {
//                Toast.makeText(SettingActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
//            imgAvatar.setImageURI(data.getData());
//            Uri uri = data.getData();
//            InputStream imageStream;
//            String encodedImage = "";
//            try{
//                imageStream = getApplicationContext().getContentResolver().openInputStream(uri);
//                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                encodedImage = encodedImage + encodeImage(selectedImage);
//            } catch(FileNotFoundException e){
//                e.printStackTrace();
//            }
//            base64Image = encodedImage;
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == PICK_IMAGE) {
//            Uri uri = data.getData();
//            image.setImageURI((uri));
//            InputStream imageStream;
//            String encodedImage = "";
//
//            image.getLayoutParams().height = 500;
//            image.getLayoutParams().width = 400;
//
//            try{
//                imageStream = getApplicationContext().getContentResolver().openInputStream(uri);
//                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                encodedImage = encodedImage + encodeImage(selectedImage);
//            } catch(FileNotFoundException d){
//                d.printStackTrace();
//            }
//            avatar.setImageURI(data.getData());
//            base64Image = encodedImage;
//        }
//    }


//    private void pickImageFromGallery() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "select picture"), PICK_IMAGE);
//    }

//    private void pickImageFromGallery() {
//        Intent intent =  new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, PICK_IMAGE);
//    }
//
//    private String encodeImage(Bitmap bm) {
//        ByteArrayOutputStream baos =new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
//        byte[] b = baos.toByteArray();
//        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
//
//        return encImage;
//    }
//
//    private void setImageThumb(String base64String){
//        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        imgAvatar.setImageBitmap(decodedByte);
//    }
//
//    private void setButtonVisibility(int saveState, int updateState) {
//        btnSave.setVisibility(saveState);
//        btnUpdate.setVisibility(updateState);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case PERMISSION_CODE: {
//                if (grantResults.length > 0 && grantResults[0]
//                        == PackageManager.PERMISSION_GRANTED){
//                    pickImageFromGallery();
//                }
//                else {
//                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }


}