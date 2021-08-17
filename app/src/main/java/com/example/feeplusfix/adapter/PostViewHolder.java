package com.example.feeplusfix.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feeplusfix.DetailPost;
import com.example.feeplusfix.MainActivity;
import com.example.feeplusfix.R;
import com.example.feeplusfix.model.AddPosting;
import com.example.feeplusfix.model.Post;

import java.io.Serializable;

public class PostViewHolder extends RecyclerView.ViewHolder {
    TextView tvNamaBarang, tvDeskripsiBarang, tvHargaBarang;
    android.widget.Button btnDetailPost;

    Post post;

    public PostViewHolder(@NonNull View itemView, Activity activity) {
        super(itemView);

        tvNamaBarang = itemView.findViewById(R.id.tv_nama_barang);
        tvDeskripsiBarang = itemView.findViewById(R.id.tv_deskripsi_barang);
        tvHargaBarang = itemView.findViewById(R.id.tv_harga_barang);
        btnDetailPost = itemView.findViewById(R.id.btn_detail_post);

        btnDetailPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity) activity).openDetail(post);
            }
        });
    }

}
