package com.example.test_android.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.test_android.Interface.AppService;
import com.example.test_android.Interface.UserImageService;
import com.example.test_android.R;
import com.example.test_android.model.ApiResult;
import com.example.test_android.model.User;
import com.example.test_android.model.UserImage;
import com.example.test_android.utilities.Cons;
import com.example.test_android.utilities.RetrofitUtility;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
public class UserImageFragment extends Fragment {

    public static final int PICK_IMAGE = 1;
    private String TAG = "UserImageFragment";
    private View view;
    private MaterialButton fileButton, saveButton, updateButton;
    private ImageView image;
    private String base64Image;
    MainActivity mainActivity;
   User user;
    UserImage userImage;
    ImageView mImageView;
    Button mChooseBtn;
    Button btnSave, btnUpdate, btnSetting;
    TabLayout tab_image, tab_address, tabLayout;
    private String avatar;
    private Long id;
    //    private String TAG = "User Setting";
    private static final int PERMISSION_CODE = 1001;
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_image_freagment, container, false);
//
        initView();
        initRetrofit();

        getAvatar();

        return view;
    }

    private void initView() {

//        retrofit = RetrofitUtility.initializeRetrofit();

        fileButton = view.findViewById(R.id.btnChooseImage);
        saveButton = view.findViewById(R.id.btnSave);
        updateButton = view.findViewById(R.id.btnUpdate);
        image = view.findViewById(R.id.imgAvatar);

        saveButton.setEnabled(false);

        fileButton.setOnClickListener(v -> fileChooser());
        saveButton.setOnClickListener(v -> {
            sendImage(userImage);
        });

        updateButton.setOnClickListener(v -> {
            updateAvatar();
            Log.e(TAG, "update data: ");
        });
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

    //function pemilihan file
    private void fileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    //event yang terjadi saat file image di pilih
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            Uri uri = data.getData();
            image.setImageURI(uri);
            InputStream imageStream;
            String encodedImage = "";

            image.getLayoutParams().height = 500;
            image.getLayoutParams().width = 400;

            try {
                imageStream = getContext().getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                encodedImage = encodedImage + encodeImage(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            base64Image = encodedImage; // hasilnya sudah dalam bentuk base64 text encoded
            Log.e(TAG, "hasil encoded image : " + encodedImage);
            saveButton.setEnabled(true);
        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }


    private void sendImage(UserImage userImageBody) {
        UserImageService userImageService = retrofit.create(UserImageService.class);  //instansiasi interfacenya ke retrofit
        Call<ApiResult> result = userImageService.insertUserImage(AppService.getToken(), userImageBody);   // call method interfacenya

        result.enqueue(new Callback<ApiResult>() {

            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult apiResponse = response.body();
                boolean success = apiResponse.isSuccess();
                if (success) {
                    Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getAvatar(){
        Long user_id = AppService.getUser().getId();
        UserImageService userImageService = retrofit.create(UserImageService.class);
        Call<ApiResult> result = userImageService.getUserImage(AppService.getToken(), user_id);

        result.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                Gson gson = new Gson();
                ApiResult apiResult = response.body();
                boolean success = apiResult.isSuccess();
                if (success) {
                    UserImage userImageResult = gson.fromJson(gson.toJson(apiResult.getData()), UserImage.class);
                    setImageThumb(userImageResult.getAvatar());
                    setButtonVisibility(View.GONE, View.VISIBLE);
                } else {
                    setButtonVisibility(View.VISIBLE, View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateAvatar(){
//        Long person_id = AppService.getPerson().getId();
        userImage = new UserImage();
        userImage.setUser_id(AppService.getUser().getId());
        userImage.setAvatar(base64Image);

        UserImageService userImageService = retrofit.create(UserImageService.class);
        Call<ApiResult> result = userImageService.updateUserImage(AppService.getToken(), userImage);

        result.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult apiResult = response.body();
                if (apiResult.isSuccess()){
                    Toast.makeText(getContext(), apiResult.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), apiResult.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setImageThumb(String base64String) {
        Log.e(TAG, "data image dari server api: "+ base64String );
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        image.setImageBitmap(decodedByte);
    }

    private void setButtonVisibility(int saveState, int updateState) {
        saveButton.setVisibility(saveState);
        updateButton.setVisibility(updateState);
    }


}