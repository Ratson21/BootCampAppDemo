package com.example.test_android.Interface;

import com.example.test_android.model.ApiResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RegenciesApiService {
    @GET("api/regencies/getByProvinceId")
    Call<ApiResult> getRegencieByProvinceId(@Header("Authorization") String token, @Query("provinceId") int provinceId);

    @GET("api/regencies/getByRegenciesId")
    Call<ApiResult> getByRegenciesId(@Header("Authorization") String token, @Query("Id") Integer id);

}
