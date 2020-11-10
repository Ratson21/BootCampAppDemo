package com.example.test_android.utilities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.test_android.R;
import com.example.test_android.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private String TAG = "MovieAdapter";
    private ArrayList<Movie> dataList;

    public MovieAdapter(ArrayList<Movie> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new MovieAdapter.MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.txtJudul.setText(dataList.get(position).getJudul());
        holder.txtRating.setText(dataList.get(position).getRating());
        holder.txtSinopsis.setText(dataList.get(position).getSinopsis());
        holder.imagePoster.setImageBitmap(convertToBitmap(dataList.get(position).getImage()));
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView txtJudul, txtRating, txtSinopsis;
        private ImageView imagePoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            txtJudul = itemView.findViewById(R.id.txt_judul);
            txtRating = itemView.findViewById(R.id.txt_rating);
            txtSinopsis = itemView.findViewById(R.id.txt_sinopsis);
            imagePoster = itemView.findViewById(R.id.image_poster);
        }
    }

    private Bitmap convertToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }


}

