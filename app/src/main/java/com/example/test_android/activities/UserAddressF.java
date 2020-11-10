package com.example.test_android.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.test_android.Interface.AppService;
import com.example.test_android.Interface.DistrictApiService;
import com.example.test_android.Interface.ProvincesApiService;
import com.example.test_android.Interface.RegenciesApiService;
import com.example.test_android.Interface.UserAddressApiService;
import com.example.test_android.Interface.VillageApiService;
import com.example.test_android.R;
import com.example.test_android.model.ApiResult;
import com.example.test_android.model.District;
import com.example.test_android.model.Province;
import com.example.test_android.model.Regencie;
import com.example.test_android.model.UserAddress;
import com.example.test_android.model.Village;
import com.example.test_android.utilities.RetrofitUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UserAddressF extends Fragment {

    private String TAG = "userAddressF";
    private View view;
    private String user_id, address, pos_code, province_id, regencie_id, district_id, village_id;
    private EditText spinnerProvince, spinnerRegencie, spinnerDistrict,spinnerVillage;
    private EditText txtUserAddress, txtPostalCode;
    private Retrofit retrofit;
    int provinceId, regencyId, districtId, villageId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_address2, container, false);
        initView();


        return view;
    }

    private void initView() {

        retrofit = RetrofitUtility.initializeRetrofit();

        spinnerProvince = view.findViewById(R.id.spin_provinces2);
        spinnerRegencie = view.findViewById(R.id.spin_regencie2);
        spinnerDistrict = view.findViewById(R.id.spin_district2);
        spinnerVillage = view.findViewById(R.id.spin_villages2);
        txtUserAddress = view.findViewById(R.id.txtAddress2);
        txtPostalCode = view.findViewById(R.id.txtPostalCode2);
//getUserAddressData();
getData();
    }

    private void getUserAddressData() {

        String user_id = String.valueOf(AppService.getUser().getId());


        UserAddressApiService userAddressApiService = retrofit.create(UserAddressApiService.class);
        Call<ApiResult> resultCall = userAddressApiService.getByUserAddressId(AppService.getToken(), user_id);

        resultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult result = response.body();


                Log.e(TAG, "onResponse: " + result.getData());

                if (result.isSuccess()) {
                    Log.e(TAG, "onResponse ada");


                } else {
                    Log.e(TAG, "onResponse: tidak ada");
                }

            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void getData() {
        SettingActivity activity = (SettingActivity) getActivity();
        UserAddress userAddress = activity.getUserAddress();



        spinnerProvince = view.findViewById(R.id.spin_provinces2);
        spinnerRegencie = view.findViewById(R.id.spin_regencie2);
        spinnerDistrict = view.findViewById(R.id.spin_district2);
        spinnerVillage = view.findViewById(R.id.spin_villages2);
        txtUserAddress = view.findViewById(R.id.txtAddress2);
        txtPostalCode = view.findViewById(R.id.txtPostalCode2);

        txtUserAddress.setText(userAddress.getAddress());
        txtPostalCode.setText(userAddress.getPos_code());

        txtUserAddress.setEnabled(false);
        txtPostalCode.setEnabled(false);
        spinnerProvince.setEnabled(false);
        spinnerRegencie.setEnabled(false);
        spinnerDistrict.setEnabled(false);
        spinnerVillage.setEnabled(false);

        getProvince(userAddress.getProvince_id());
        getRegencie(userAddress.getRegencie_id());
        getDistrict(userAddress.getDistrict_id());
        getVillage(userAddress.getVillage_id());



    }

    private void getProvince(Integer id) {
        ProvincesApiService provincesApiService = retrofit.create(ProvincesApiService.class);
        Call<ApiResult> resultCall = provincesApiService.getByProvinceId(AppService.getToken(), id);

        resultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                Gson gson = new Gson();

                ApiResult result = response.body();

                Province province = gson.fromJson(gson.toJson(result.getData()), Province.class);
                spinnerProvince.setText(province.getName());
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
//                Toasty.error(getBaseContext(), t.getMessage(), Toasty.LENGTH_LONG).show();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getRegencie(Integer id) {
        RegenciesApiService regenciesApiService = retrofit.create(RegenciesApiService.class);
        Call<ApiResult> resultCall = regenciesApiService.getByRegenciesId(AppService.getToken(), id);

        resultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult result = response.body();

                Gson gson = new Gson();
                Regencie regencie = gson.fromJson(gson.toJson(result.getData()), Regencie.class);
                spinnerRegencie.setText(regencie.getName());
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
//                Toasty.error(getBaseContext(), t.getMessage(), Toasty.LENGTH_LONG).show();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    //
    private void getDistrict(Integer id) {
        DistrictApiService districtApiService = retrofit.create(DistrictApiService.class);
        Call<ApiResult> resultCall = districtApiService.getByDistrictsId(AppService.getToken(), id);

        resultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult result = response.body();

                Gson gson = new Gson();
                District district = gson.fromJson(gson.toJson(result.getData()), District.class);
                spinnerDistrict.setText(district.getName());
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
//                Toasty.error(getBaseContext(), t.getMessage(), Toasty.LENGTH_LONG).show();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getVillage(Integer id) {
        VillageApiService villageApiService = retrofit.create(VillageApiService.class);
        Call<ApiResult> resultCall = villageApiService.getByVillageId(AppService.getToken(), id);

        resultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult result = response.body();

                Gson gson = new Gson();
                Village village = gson.fromJson(gson.toJson(result.getData()), Village.class);
                spinnerVillage.setText(village.getName());
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
//                Toasty.error(getBaseContext(), t.getMessage(), Toasty.LENGTH_LONG).show();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();


            }
        });
    }
}