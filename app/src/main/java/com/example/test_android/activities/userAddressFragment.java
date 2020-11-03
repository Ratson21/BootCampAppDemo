package com.example.test_android.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.test_android.R;
import com.example.test_android.utilities.Cons;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    private Spinner spinnerProvince, spinnerRegencie, spinnerDistrict;
    private Retrofit retrofit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_address, container, false);
        initView();
        initRetrofit();

        return view;
    }

    private void initView() {

        spinnerProvince = view.findViewById(R.id.spin_provinces);
        spinnerRegencie = view.findViewById(R.id.spin_regencie);
        spinnerDistrict = view.findViewById(R.id.spin_district);

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


}