package com.example.test_android.Interface;

import com.example.test_android.model.ApiResult;
import com.example.test_android.model.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {

    @POST("login")
    Call<ApiResult> userLogin(@Body Login loginBody);
}
