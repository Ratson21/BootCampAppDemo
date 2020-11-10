package com.example.test_android.Interface;

import com.example.test_android.model.ApiResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface DistrictApiService {

    @GET("api/districts/getByRegenciesId")
    Call<ApiResult> getAllRegencyId(@Header("Authorization") String token, @Query("regenciesId") int regencyId);

    @GET("api/districts/getByDistrictsId")
    Call<ApiResult> getByDistrictsId(@Header("Authorization") String token, @Query("Id") Integer id);

}

