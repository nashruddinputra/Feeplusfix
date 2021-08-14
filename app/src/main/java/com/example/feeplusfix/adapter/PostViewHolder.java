package com.example.feeplusfix.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feeplusfix.R;

public class PostViewHolder extends RecyclerView.ViewHolder {
    ConstraintLayout constraint;
    TextView tvNamaBarang, tvDeskripsiBarang, tvHargaBarang;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        tvNamaBarang = itemView.findViewById(R.id.tv_nama_barang);
        tvDeskripsiBarang = itemView.findViewById(R.id.tv_deskripsi_barang);
        tvHargaBarang = itemView.findViewById(R.id.tv_harga_barang);
        constraint = itemView.findViewById(R.id.constraint);
    }

}
