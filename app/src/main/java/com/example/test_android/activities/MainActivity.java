package com.example.test_android.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.test_android.Interface.AppService;
import com.example.test_android.Interface.MovieApiService;
import com.example.test_android.R;
import com.example.test_android.model.ApiResult;
import com.example.test_android.model.Movie;
import com.example.test_android.utilities.MovieAdapter;
import com.example.test_android.utilities.RetrofitUtility;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> moviesArrayList = new ArrayList<>();
    private String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton setting = (ImageButton) findViewById(R.id.imageButtonSetting);

        getMovieData();

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(i);
            }

        });


    }

    //call login activity
    private void toSettingActivity() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
        finish();
    }

    private void getMovieData() {
        retrofit = RetrofitUtility.initializeRetrofit();
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<ApiResult> resultCall = movieApiService.getAllMovie(AppService.getToken());

        resultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult result = response.body();

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Movie>>() {
                }.getType();

                List<Movie> movies = gson.fromJson(gson.toJson(result.getData()), listType);
                moviesArrayList.addAll(movies);

                setRecyclerView();
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);

            }
        });
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recycler_main);
        movieAdapter = new MovieAdapter(moviesArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(movieAdapter);


    }

    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity. this);
        builder.setMessage("Yakin untuk keluar?");
        builder.setPositiveButton("Ya",(dialog, which) -> {finish();});
        builder.setCancelable(true);
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
}