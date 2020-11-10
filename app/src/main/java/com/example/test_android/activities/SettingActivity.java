package com.example.test_android.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.test_android.Interface.AppService;
import com.example.test_android.Interface.UserAddressApiService;
import com.example.test_android.Interface.UserImageService;
import com.example.test_android.R;
import com.example.test_android.model.ApiResult;
import com.example.test_android.model.User;
import com.example.test_android.model.UserAddress;
import com.example.test_android.utilities.RetrofitUtility;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    TabLayout tabLayout;
    private UserAddress userAddress;

    private Retrofit retrofit;
//    ImageView imgAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);;

//        TabLayout tabLayout = findViewById(R.id.tabLayout);
//        TabItem tabImage = findViewById(R.id.tab_image);
//        TabItem tabAddress = findViewById(R.id.tab_address);
//        ViewPager viewPager = findViewById(R.id.viewPager);
//
//        PagerAdapter pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(pagerAdapter);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

//                initView();
//        initRetrofit();
//        getImage();


//
        getUserAvatar();
        openUserImageFragment();

        }

    private void getUserAvatar() {
        Long user_id = AppService.getUser().getId();
        tabLayout = findViewById(R.id.tabLayout);

        retrofit = RetrofitUtility.initializeRetrofit();

        UserImageService userImageService = retrofit.create(UserImageService.class);  //instansiasi interfacenya ke retrofit
        Call<ApiResult> result = userImageService.getUserImage(AppService.getToken(), user_id);   // call method interfacenya


        result.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                Log.e(TAG, "User setting response : " + response);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure user setting: " + t);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.e(TAG, "onTabSelected: " + position);
                if (position == 0) {
                    Log.e(TAG, "onTabSelected: user image");
                    openUserImageFragment();
                } else {
                    Log.e(TAG, "onTabSelected: user address");
//                    openUserAddressFragment();
                    getUserAddressData();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.e(TAG, "onTabUnselected: " + tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.e(TAG, "onTabReselected: " + tab);
            }
        });

    }

    public void openUserImageFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserImageFragment strCode = new UserImageFragment();
        fragmentTransaction.replace(R.id.content, strCode, "UserImageFragment");
        fragmentTransaction.commit();
    }

    private void getUserAddressData() {

        String user_id = String.valueOf(AppService.getUser().getId());

        UserAddressApiService userAddressApiService = retrofit.create(UserAddressApiService.class);
        Call<ApiResult> resultCall = userAddressApiService.getByUserAddressId(AppService.getToken(), user_id);

        resultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult result = response.body();

                if (result.isSuccess()) {
                    Gson gson = new Gson();
                    userAddress = gson.fromJson(gson.toJson(result.getData()), UserAddress.class);
                    openUserAddressDataFragment();
                } else {
                    Log.e(TAG, "onResponse failed : ");
                    openUserAddressFragment();
                }

            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
//                Toasty.error(getBaseContext(), t.getMessage(), Toasty.LENGTH_LONG).show();
                Toast.makeText(SettingActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    public void openUserAddressFragment() {
        Log.e(TAG, "openUserAddressFragment: ");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserAddressFragment strCode = new UserAddressFragment();
        fragmentTransaction.replace(R.id.content, strCode, "userAddressFragment");
        fragmentTransaction.commit();
    }

    public void openUserAddressDataFragment() {
        Log.e(TAG, "openUserAddressDataFragment: ");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserAddressF strCode = new UserAddressF();
        fragmentTransaction.replace(R.id.content, strCode, "userAddressF");
        fragmentTransaction.commit();
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }



}