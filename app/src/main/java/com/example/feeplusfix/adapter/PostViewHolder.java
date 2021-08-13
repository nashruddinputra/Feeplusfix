package com.example.feeplusfix.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feeplusfix.R;

public class PostViewHolder extends RecyclerView.ViewHolder {

    TextView tvNamaBarang, tvDeskripsiBarang, tvHargaBarang;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        tvNamaBarang = itemView.findViewById(R.id.tv_nama_barang);
        tvDeskripsiBarang = itemView.findViewById(R.id.tv_deskripsi_barang);
        tvHargaBarang = itemView.findViewById(R.id.tv_harga_barang);
    }

}
