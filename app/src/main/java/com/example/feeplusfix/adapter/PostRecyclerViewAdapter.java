package com.example.feeplusfix.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.feeplusfix.DetailPost;
import com.example.feeplusfix.R;
import com.example.feeplusfix.model.Post;

import java.io.Serializable;
import java.util.List;

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostViewHolder> {

     List<Post> data;

    Activity activity;

    public PostRecyclerViewAdapter(List<Post> data, Activity activity){
        this.data = data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        holder.tvNamaBarang.setText(data.get(position).getNamaBarang());
        holder.tvDeskripsiBarang.setText(data.get(position).getDeskripsiBarang());
        holder.tvHargaBarang.setText(data.get(position).getHargaBarang());
        if(!TextUtils.isEmpty(data.get(position).getGambarBarang())){
            Glide.with(activity).load(data.get(position).getGambarBarang())
                    .centerCrop()
                    .into(holder.imGambarBarang);
            holder.imGambarBarang.setVisibility(View.VISIBLE);
        }
        holder.post = data.get(position);
    }

    @Override
    public int getItemCount() {
        return (data != null) ? data.size() : 0;
    }
}

