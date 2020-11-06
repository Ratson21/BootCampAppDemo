package com.example.test_android.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.test_android.Interface.AppService;
import com.example.test_android.Interface.DistrictApiService;
import com.example.test_android.Interface.ProvincesApiService;
import com.example.test_android.Interface.RegenciesApiService;
import com.example.test_android.Interface.VillageApiService;
import com.example.test_android.R;
import com.example.test_android.model.ApiResult;
import com.example.test_android.model.District;
import com.example.test_android.model.Province;
import com.example.test_android.model.Regencie;
import com.example.test_android.model.Village;
import com.example.test_android.utilities.Cons;
import com.example.test_android.utilities.RetrofitUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class userAddressFragment extends Fragment {


    private String TAG = "userAddressFragment";
    private View view;
    private Spinner spinnerProvince, spinnerRegencie, spinnerDistrict,spinnerVillage;;
    private Retrofit retrofit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_address, container, false);
        initView();


        return view;
    }

    private void initView() {

        retrofit = RetrofitUtility.initializeRetrofit();

        spinnerProvince = view.findViewById(R.id.spin_provinces);
        spinnerRegencie = view.findViewById(R.id.spin_regencie);
        spinnerDistrict = view.findViewById(R.id.spin_district);
        spinnerVillage = view.findViewById(R.id.spin_villages);

        getProvinces();
    }

    private void getProvinces() {

        ProvincesApiService provincesApiService = retrofit.create(ProvincesApiService.class);  //instansiasi interfacenya ke retrofit
        Call<ApiResult> result = provincesApiService.getAllProvinces(AppService.getToken());   // call method interfacenya

        result.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                Log.e(TAG, "onResponse: " + response.body());
                Gson gson = new Gson();
                ApiResult apiResult = response.body();

                Type listType = new TypeToken<List<Province>>() {
                }.getType();

                List<Province> provinces = gson.fromJson(gson.toJson(apiResult.getData()), listType);
                setProvinceSpinner(provinces);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure user address setting: " + t);
            }
        });
    }

    private void getRegencie(int provinceId) {

        RegenciesApiService regenciesApiService = retrofit.create(RegenciesApiService.class);  //instansiasi interfacenya ke retrofit
        Call<ApiResult> result = regenciesApiService.getRegencieByProvinceId(AppService.getToken(), provinceId);   // call method interfacenya

        result.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                Gson gson = new Gson();
                ApiResult apiResult = response.body();

                Type listType = new TypeToken<List<Regencie>>() {
                }.getType();
                List<Regencie> regencies = gson.fromJson(gson.toJson(apiResult.getData()), listType);

                setRegencieSpinner(regencies);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);

            }
        });
    }
//
    private void getDistrict(int regencyId) {


        DistrictApiService districtApiService = retrofit.create(DistrictApiService.class);  //instansiasi interfacenya ke retrofit
        Call<ApiResult> result = districtApiService.getAllRegencyId(AppService.getToken(), regencyId);   // call method interfacenya

        result.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                Gson gson = new Gson();
                ApiResult apiResult = response.body();

                Type listType = new TypeToken<List<District>>() {
                }.getType();
                List<District> districts = gson.fromJson(gson.toJson(apiResult.getData()), listType);

                setDistrictSpinner(districts);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);

            }
        });
    }

    private void getVillages(int districtId) {


        VillageApiService villageApiService = retrofit.create(VillageApiService.class);  //instansiasi interfacenya ke retrofit
        Call<ApiResult> result = villageApiService.getAllVillageId(AppService.getToken(), districtId);   // call method interfacenya

        result.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                Gson gson = new Gson();
                ApiResult apiResult = response.body();

                Type listType = new TypeToken<List<Village>>() {
                }.getType();
                List<Village> villages = gson.fromJson(gson.toJson(apiResult.getData()), listType);

                Log.e(TAG, "onResponse: " + villages);
                setVillageSpinner(villages);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);

            }
        });
    }

    private void setProvinceSpinner(List<Province> provinceList) {
        List<String> provinces = new ArrayList<>();
        provinces.add("Choose Province");

        for (Province province : provinceList) {
            provinces.add(province.getName());
        }

        // Buat adapter untuk spinner province
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, provinces) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    return false;
                } else {
                    return true;
                }
            }
        };
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // set data adapter ke spinner
        spinnerProvince.setAdapter(dataAdapter);
        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemSelected: " + position);
                String selectedId = position == 0 ? null : String.valueOf(position - 1);

                if (selectedId == null) {
                    Log.e(TAG, "onItemSelected: " + null);
                } else {
                    Log.e(TAG, "pilihan selain itu ");
                    Log.e(TAG, "onItemSelected: " + provinceList.get(Integer.parseInt(selectedId)).getId());

                    int provinceId = provinceList.get(Integer.parseInt(selectedId)).getId();

                    getRegencie(provinceId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e(TAG, "onNothingSelected: " + parent);
            }

        });

    }

    private void setRegencieSpinner(List<Regencie> regencieList) {
        List<String> regencies = new ArrayList<>();
        regencies.add("Choose Regencie");

        for (Regencie regencie : regencieList) {
            regencies.add(regencie.getName());
        }

        // Buat adapter untuk spinner regencie
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, regencies) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    return false;
                } else {
                    return true;
                }
            }
        };
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // set data adapter ke spinner
        spinnerRegencie.setAdapter(dataAdapter);
        spinnerRegencie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemSelected: " + position);
                String selectedId = position == 0 ? null : String.valueOf(position - 1);

                if (selectedId == null) {
                    Log.e(TAG, "onItemSelected: " + null);
                } else {
                    Log.e(TAG, "pilihan selain itu ");

                    int regencyId = regencieList.get(Integer.parseInt(selectedId)).getId();

                    Log.e(TAG, "onItemSelected: " + regencyId);

                    getDistrict(regencyId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e(TAG, "onNothingSelected: " + parent);
            }

        });

    }

    private void setDistrictSpinner(List<District> districtList) {
        List<String> districts = new ArrayList<>();
        districts.add("Choose District");

        for (District district : districtList) {
            districts.add(district.getName());
        }

        // Buat adapter untuk spinner regencie
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, districts) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    return false;
                } else {
                    return true;
                }
            }
        };
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // set data adapter ke spinner
        spinnerDistrict.setAdapter(dataAdapter);
        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemSelected: " + position);
                String selectedId = position == 0 ? null : String.valueOf(position - 1);

                if (selectedId == null) {
                    Log.e(TAG, "onItemSelected: " + null);
                } else {
                    Log.e(TAG, "pilihan selain itu ");
                    int districtId = districtList.get(Integer.parseInt(selectedId)).getId();
                    Log.e(TAG, "onItemSelected: " + districtId);
                    getVillages(districtId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e(TAG, "onNothingSelected: " + parent);
            }
        });

    }

    private void setVillageSpinner(List<Village> villagesList) {
        List<String> villages = new ArrayList<>();
        villages.add("Choose Village");

        for (Village village : villagesList) {
            villages.add(village.getName());
        }

        // Buat adapter untuk spinner village
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, villages) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    return false;
                } else {
                    return true;
                }
            }
        };
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // set data adapter ke spinner
        spinnerVillage.setAdapter(dataAdapter);
        spinnerVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemSelected: " + position);
                String selectedId = position == 0 ? null : String.valueOf(position - 1);

                if (selectedId == null) {
                    Log.e(TAG, "onItemSelected: " + null);
                } else {
                    Log.e(TAG, "pilihan selain itu ");
                    int districtId = villagesList.get(Integer.parseInt(selectedId)).getId();
                    Log.e(TAG, "onItemSelected: " + districtId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e(TAG, "onNothingSelected: " + parent);
            }
        });

    }
}