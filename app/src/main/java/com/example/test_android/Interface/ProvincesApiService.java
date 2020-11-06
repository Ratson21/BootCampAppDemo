package com.example.test_android.Interface;

import com.example.test_android.model.ApiResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProvincesApiService {
    @GET("api/provinces/getAll")
    Call<ApiResult> getAllProvinces(@Header("Authorization") String token);
}
