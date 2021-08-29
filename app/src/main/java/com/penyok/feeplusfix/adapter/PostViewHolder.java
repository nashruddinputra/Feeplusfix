package com.penyok.feeplusfix.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.penyok.feeplusfix.MainActivity;
import com.penyok.feeplusfix.R;
import com.penyok.feeplusfix.model.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {
    TextView tvNamaBarang, tvDeskripsiBarang, tvHargaBarang;
    android.widget.Button btnDetailPost;
    ImageView imGambarBarang;

    Post post;

    public PostViewHolder(@NonNull View itemView, Activity activity) {
        super(itemView);

        tvNamaBarang = itemView.findViewById(R.id.tv_nama_barang);
        tvDeskripsiBarang = itemView.findViewById(R.id.tv_deskripsi_barang);
        tvHargaBarang = itemView.findViewById(R.id.tv_harga_barang);
        btnDetailPost = itemView.findViewById(R.id.btn_detail_post);
        imGambarBarang = itemView.findViewById(R.id.im_image_post);


        btnDetailPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity) activity).openDetail(post);
            }
        });
    }

}
