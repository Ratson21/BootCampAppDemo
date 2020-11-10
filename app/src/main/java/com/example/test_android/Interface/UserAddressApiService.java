package com.example.test_android.Interface;

import com.example.test_android.model.ApiResult;
import com.example.test_android.model.UserAddress;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAddressApiService {

    @GET("user_address/getByUserAddressId")
    Call<ApiResult> getByUserAddressId(@Header("Authorization") String token, @Query("user_id") String user_id);

    @POST("user_address/insert")
    Call<ApiResult> insertUserAddress(@Header("Authorization") String token, @Body UserAddress userAddress);
}
