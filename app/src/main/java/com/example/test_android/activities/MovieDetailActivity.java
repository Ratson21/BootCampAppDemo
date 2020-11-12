package com.example.test_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test_android.Interface.AppService;
import com.example.test_android.Interface.MovieApiService;
import com.example.test_android.R;
import com.example.test_android.model.ApiResult;
import com.example.test_android.model.Movie;
import com.example.test_android.model.Province;
import com.example.test_android.utilities.RetrofitUtility;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetailActivity extends AppCompatActivity {
    private String TAG = "MovieDetailActivity";
    ImageView image_poster;
    private View view;
    TextView txt_judul, txt_rating, txt_sutradara, txt_pemain, txt_sinopsis;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        long movieId = intent.getLongExtra("movieId", 0);
        initView();
        getMoviewById(movieId);
    }

    private void initView(){
        image_poster = findViewById(R.id.image_poster2);
        txt_judul = findViewById(R.id.txt_judul2);
        txt_rating = findViewById(R.id.txt_rating2);
        txt_sutradara = findViewById(R.id.txt_sutradara);
        txt_pemain = findViewById(R.id.txt_pemain);
        txt_sinopsis = findViewById(R.id.txt_sinopsis2);
    }

    private void getMoviewById( Long id){
        retrofit = RetrofitUtility.initializeRetrofit();

        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<ApiResult> resultCall = movieApiService.getMovieById(AppService.getToken(), id);

        resultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                Gson gson = new Gson();

                ApiResult result = response.body();

                Movie movie = gson.fromJson(gson.toJson(result.getData()), Movie.class);
                setMoiveDetail(movie);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
            }
        });
    }

    private void setMoiveDetail(Movie movieBody){
       txt_judul.setText(movieBody.getJudul());
       txt_pemain.setText(movieBody.getCast());
       txt_sutradara.setText(movieBody.getSutradara());
       txt_rating.setText(movieBody.getRating());
       image_poster.setImageBitmap(convertToBitmap(movieBody.getImage()));
       txt_sinopsis.setText(movieBody.getSinopsis());

    }

    private Bitmap convertToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

}